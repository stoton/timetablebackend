package com.github.stoton.timetablebackend.controller.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.TimetableHeader;
import com.github.stoton.timetablebackend.domain.timetable.TimetableHeaderJson;
import com.github.stoton.timetablebackend.domain.timetable.TimetableJson;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.ParserFactory;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import com.github.stoton.timetablebackend.repository.TimetableHeaderJsonRepository;
import com.github.stoton.timetablebackend.repository.TimetableJsonRepository;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.github.stoton.timetablebackend.service.TimetableProducerRegonizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TimetableController {

    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    private final TimetableJsonRepository timetableJsonRepository;
    private final TimetableHeaderJsonRepository timetableHeaderJsonRepository;
    private final SchoolRepository schoolRepository;
    private final TimetableProducerRegonizer timetableProducerRegonizer;

    @Autowired
    public TimetableController(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, TimetableJsonRepository timetableJsonRepository, TimetableHeaderJsonRepository timetableHeaderJsonRepository, SchoolRepository schoolRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableJsonRepository = timetableJsonRepository;
        this.timetableHeaderJsonRepository = timetableHeaderJsonRepository;
        this.schoolRepository = schoolRepository;
        timetableProducerRegonizer = new TimetableProducerRegonizer();
    }

    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TimetableHeader>>> getAllTimetables(@PathVariable Long schoolId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<TimetableHeaderJson> allBySchoolId = timetableHeaderJsonRepository.getAllBySchoolId(schoolId);

        if (allBySchoolId.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        updateSchoolsPopularity(schoolId);

        List<TimetableHeader> timetableHeaderList = new ArrayList<>();

        for (TimetableHeaderJson timetableHeaderJson : allBySchoolId) {
            TimetableHeader timetableHeader = mapper.readValue(timetableHeaderJson.getJson(), TimetableHeader.class);
            timetableHeaderList.add(timetableHeader);
        }
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetableHeaderList));
    }

    @GetMapping(value = "/schools/{schoolId}/timetables/{timetableId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Timetable>> getExampleTimetable(@PathVariable Long schoolId, @PathVariable Long timetableId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TimetableJson timetableJson = timetableJsonRepository.findBySchoolIdAndId(schoolId, timetableId);

        if (timetableJson == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        updateSchoolsPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(mapper.readValue(timetableJson.getJson(), Timetable.class)));
    }

    @PostMapping(value = "/schools/{schoolId}/scheduleParser")
    public void parseAllTimetablesBySchoolId(@PathVariable("schoolId") Long schoolId) throws IOException, UnknownTimetableTypeException {

        ParserFactory parserFactory = new ParserFactory();
        TimetableParser timetableParser = parserFactory.getTimetableParser(TimetableProducerType.OPTIVUM_TIMETABLE);

        List<OptivumTimetableIndexItem> optivumTimetableIndexItems = optivumTimetableIndexItemRepository.findAllBySchoolId(schoolId);

        for (OptivumTimetableIndexItem timetableIndexItem : optivumTimetableIndexItems) {

            Timetable timetable =
                    timetableParser.parseTimetable(timetableIndexItem.getLink(), new OptivumTimetableTypeRecognizer(), schoolId);

            timetable.setId(timetableIndexItem.getId());

            TimetableHeader timetableHeader = new TimetableHeader();
            timetableHeader.setId(timetable.getId());
            timetableHeader.setName(timetable.getName());
            timetableHeader.setType(timetable.getType());
            timetableHeader.setTimestamp(timetable.getTimestamp());

            Optional<School> schoolOptional = schoolRepository.findById(schoolId);

            if (schoolOptional.isPresent()) {
                School school = schoolOptional.get();
                school.setTimestamp(timetable.getTimestamp());
            }

            TimetableJson timetableJson = new TimetableJson();
            TimetableHeaderJson timetableHeaderJson = new TimetableHeaderJson();

            timetableJson.setSchoolId(schoolId);
            timetableHeaderJson.setSchoolId(schoolId);

            try {
                ObjectMapper mapper = new ObjectMapper();
                timetableJson.setJson(mapper.writeValueAsString(timetable));
                timetableHeaderJson.setJson(mapper.writeValueAsString(timetableHeader));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            timetableJsonRepository.save(timetableJson);
            timetableHeaderJsonRepository.save(timetableHeaderJson);
        }
    }

    private void updateSchoolsPopularity(@PathVariable Long schoolId) {
        List<School> schools = schoolRepository.findAll();

        for (School school : schools) {
            if (school.getId().equals(schoolId)) {
                school.setSchoolCallsNumber(school.getSchoolCallsNumber().add(BigInteger.ONE));
            }
        }
        schools.sort((o1, o2) -> o2.getSchoolCallsNumber().compareTo(o1.getSchoolCallsNumber()));

        Long popularity = 1L;

        for (School school : schools) {
            school.setPopularity(popularity);
            schoolRepository.save(school);
            popularity++;
        }
    }
}
