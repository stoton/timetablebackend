package com.github.stoton.timetablebackend.controller.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.TimetableHeader;
import com.github.stoton.timetablebackend.domain.timetable.TimetableHeaderJson;
import com.github.stoton.timetablebackend.domain.timetable.TimetableJson;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.ParserFactory;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import com.github.stoton.timetablebackend.properties.TimetableProvider;
import com.github.stoton.timetablebackend.repository.TimetableHeaderJsonRepository;
import com.github.stoton.timetablebackend.repository.TimetableJsonRepository;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TimetableController {

    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    private final TimetableJsonRepository timetableJsonRepository;
    private final TimetableHeaderJsonRepository timetableHeaderJsonRepository;

    @Autowired
    public TimetableController(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, TimetableJsonRepository timetableJsonRepository, TimetableHeaderJsonRepository timetableHeaderJsonRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableJsonRepository = timetableJsonRepository;
        this.timetableHeaderJsonRepository = timetableHeaderJsonRepository;
    }

    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TimetableHeader>>> getAllTimetables(@PathVariable Long schoolId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<TimetableHeaderJson> allBySchoolId = timetableHeaderJsonRepository.getAllBySchoolId(schoolId);

        if (allBySchoolId.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

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

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(mapper.readValue(timetableJson.getJson(), Timetable.class)));
    }

    @PostMapping(value = "/schools/{schoolId}/scheduleParser")
    public void parseAllTimetablesBySchoolId(@PathVariable("schoolId") Long schoolId) throws IOException, UnknownTimetableTypeException {
        ObjectMapper mapper = new ObjectMapper();

        ParserFactory parserFactory = new ParserFactory();
        TimetableParser timetableParser = parserFactory.getTimetableParser(TimetableProvider.OPTIVUM_TIMETABLE);

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


            TimetableJson timetableJson = new TimetableJson();
            TimetableHeaderJson timetableHeaderJson = new TimetableHeaderJson();

            timetableJson.setSchoolId(schoolId);
            timetableHeaderJson.setSchoolId(schoolId);

            try {
                timetableJson.setJson(mapper.writeValueAsString(timetable));
                timetableHeaderJson.setJson(mapper.writeValueAsString(timetableHeader));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            timetableJsonRepository.save(timetableJson);
            timetableHeaderJsonRepository.save(timetableHeaderJson);
        }
    }
}
