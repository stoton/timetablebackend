package com.github.stoton.timetablebackend.controller.timetable;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.ParserFactory;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import com.github.stoton.timetablebackend.repository.*;
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
    private final SchoolRepository schoolRepository;
    private final TimetableRepository timetableRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final TimetableProducerRegonizer timetableProducerRegonizer;

    @Autowired
    public TimetableController(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, SchoolRepository schoolRepository, TimetableRepository timetableRepository, ScheduleRepository scheduleRepository, LessonRepository lessonRepository, GroupRepository groupRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableRepository = timetableRepository;
        this.schoolRepository = schoolRepository;
        this.scheduleRepository = scheduleRepository;
        this.lessonRepository = lessonRepository;
        this.groupRepository = groupRepository;
        timetableProducerRegonizer = new TimetableProducerRegonizer();
    }

    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TimetableHeader>>> getAllTimetables(@PathVariable Long schoolId) throws IOException {
        List<Timetable> allBySchoolId = timetableRepository.findAllBySchoolId(schoolId);

        if (allBySchoolId.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        updateSchoolsPopularity(schoolId);

        List<TimetableHeader> timetableHeaderList = new ArrayList<>();

        for (Timetable timetable : allBySchoolId) {
            TimetableHeader timetableHeader = new TimetableHeader(
                    timetable.getId(),
                    timetable.getName(),
                    timetable.getType(),
                    timetable.getTimestamp());
            timetableHeaderList.add(timetableHeader);
        }
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetableHeaderList));
    }

    @GetMapping(value = "/schools/{schoolId}/timetables/{timetableId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Timetable>> getExampleTimetable(@PathVariable Long schoolId, @PathVariable Long timetableId) throws IOException {
        Timetable timetable = timetableRepository.findBySchoolIdAndId(schoolId, timetableId);

        if (timetable == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        updateSchoolsPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetable));
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

            Optional<School> schoolOptional = schoolRepository.findById(schoolId);

            if (schoolOptional.isPresent()) {
                School school = schoolOptional.get();
                school.setTimestamp(timetable.getTimestamp());

                schoolRepository.save(school);
            }

            saveGroup(timetable.getSchedule());
            saveLessons(timetable.getSchedule());
            saveSchedule(timetable.getSchedule());

            timetableRepository.save(timetable);
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

    private void saveGroup(Schedule schedule) {
        schedule.getMon().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getTue().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getWed().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getThu().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getFri().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getSat().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
        schedule.getSun().forEach(lesson -> groupRepository.saveAll(lesson.getLessonGroups()));
    }

    private void saveLessons(Schedule schedule) {
        lessonRepository.saveAll(schedule.getMon());
        lessonRepository.saveAll(schedule.getTue());
        lessonRepository.saveAll(schedule.getWed());
        lessonRepository.saveAll(schedule.getThu());
        lessonRepository.saveAll(schedule.getFri());
        lessonRepository.saveAll(schedule.getSat());
        lessonRepository.saveAll(schedule.getSun());
    }

    private void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }


}
