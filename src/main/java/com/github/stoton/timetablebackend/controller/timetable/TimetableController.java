package com.github.stoton.timetablebackend.controller.timetable;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.ParserFactory;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableIndexItemsParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import com.github.stoton.timetablebackend.repository.*;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.github.stoton.timetablebackend.service.TimetableProducerRegonizer;
import com.github.stoton.timetablebackend.service.UnknownTimetableProducerType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private OptivumTimetableIndexItemsParser optivumTimetableIndexItemsParser;


    @Autowired
    public TimetableController(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, SchoolRepository schoolRepository, TimetableRepository timetableRepository, ScheduleRepository scheduleRepository, LessonRepository lessonRepository, GroupRepository groupRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableRepository = timetableRepository;
        this.schoolRepository = schoolRepository;
        this.scheduleRepository = scheduleRepository;
        this.lessonRepository = lessonRepository;
        this.groupRepository = groupRepository;
        timetableProducerRegonizer = new TimetableProducerRegonizer();
        optivumTimetableIndexItemsParser = new OptivumTimetableIndexItemsParser();
    }

    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Timetable>>> getAllTimetables(@PathVariable Long schoolId) {
        List<Timetable> timetableHeadersBySchoolId = timetableRepository.findTimetableHeadersBySchoolId(schoolId);

        if (timetableHeadersBySchoolId.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        updateSchoolsPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetableHeadersBySchoolId));
    }

    @GetMapping(value = "/schools/{schoolId}/timetables/{timetableId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Timetable>> getExampleTimetable(@PathVariable Long schoolId, @PathVariable Long timetableId) {
        Timetable timetable = timetableRepository.findBySchoolIdAndId(schoolId, timetableId);

        if (timetable == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        updateSchoolsPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetable));
    }

    @PostMapping(value = "/schools/{schoolId}/scheduleParser")
    public Mono<ResponseEntity<Void>> parseAllTimetablesBySchoolId(@PathVariable("schoolId") Long schoolId) throws IOException, UnknownTimetableTypeException {

        Optional<School> optionalSchool = schoolRepository.findById(schoolId);

        if (!optionalSchool.isPresent()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        School school = optionalSchool.get();

        TimetableProducerType timetableProducerType = null;

        try {
            timetableProducerType = TimetableProducerRegonizer
                    .recognizeTimetable(school.getTimetableHref());
        } catch (UnknownTimetableProducerType unknownTimetableProducerType) {
            unknownTimetableProducerType.printStackTrace();
        }

        if (TimetableProducerType.OPTIVUM_TIMETABLE.equals(timetableProducerType)) {

            Pattern pattern = Pattern.compile("(.*?)index.html");
            Matcher matcher = pattern.matcher(school.getTimetableHref());


            String url = "";

            if (matcher.find()) {
                System.out.println(matcher.group(1));
                url = matcher.group(1);
            }

            Document document = Jsoup.connect(url + "/lista.html").get();

            List<OptivumTimetableIndexItem> optivumTimetableIndexItems =
                    optivumTimetableIndexItemsParser.parseIndexItems(document, school.getTimetableHref());

            optivumTimetableIndexItems
                    .forEach(optivumTimetableIndexItem -> saveOptivumTimetableIndexItem(optivumTimetableIndexItem, school));

            List<OptivumTimetableIndexItem> optivumTimetableIndexItemList = optivumTimetableIndexItemRepository.findAllBySchoolId(schoolId);
            optivumTimetableIndexItemList
                    .parallelStream()
                    .forEach(optivumTimetableIndexItem -> parseAndSaveTimetable(optivumTimetableIndexItem, schoolId));

            return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
        }

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }


    private void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    private void parseAndSaveTimetable(OptivumTimetableIndexItem timetableIndexItem, Long schoolId) {
        ParserFactory parserFactory = new ParserFactory();
        TimetableParser timetableParser = parserFactory.getTimetableParser(TimetableProducerType.OPTIVUM_TIMETABLE);

        Timetable timetable = null;

        try {
            timetable = timetableParser.parseTimetable(timetableIndexItem.getLink(), new OptivumTimetableTypeRecognizer(), schoolId);
        } catch (UnknownTimetableTypeException | IOException e) {
            e.printStackTrace();
        }

        Timetable currentTimetable = timetableRepository.
                findBySchoolIdAndNameAndType(schoolId, timetable.getName(), timetable.getType());

        if (currentTimetable != null) {

            if (!currentTimetable.getSchedule().equals(timetable.getSchedule())) {
                currentTimetable.setSchedule(timetable.getSchedule());

                Optional<School> schoolOptional = schoolRepository.findById(schoolId);

                currentTimetable.setTimestamp(timetable.getTimestamp());
                saveSchedule(currentTimetable.getSchedule());
                saveLessons(currentTimetable.getSchedule());
                saveGroup(currentTimetable.getSchedule());

                timetableRepository.save(currentTimetable);

                if (schoolOptional.isPresent()) {
                    School school = schoolOptional.get();
                    school.setTimestamp(timetable.getTimestamp());
                    schoolRepository.save(school);
                }

            }


        } else {
            saveSchedule(timetable.getSchedule());
            saveLessons(timetable.getSchedule());
            saveGroup(timetable.getSchedule());

            timetable.setSchoolId(schoolId);
            timetableRepository.save(timetable);
        }
    }

    private void saveOptivumTimetableIndexItem(OptivumTimetableIndexItem optivumTimetableIndexItem, School school) {
        OptivumTimetableIndexItem byFullNameAndShortNameAndSchool_id = optivumTimetableIndexItemRepository
                .findByFullNameAndShortNameAndSchool_Id(
                        optivumTimetableIndexItem.getFullName(),
                        optivumTimetableIndexItem.getShortName(),
                        school.getId());

        if (byFullNameAndShortNameAndSchool_id == null) {
            byFullNameAndShortNameAndSchool_id = new OptivumTimetableIndexItem();
        }

        byFullNameAndShortNameAndSchool_id.setSchool(school);
        byFullNameAndShortNameAndSchool_id.setFullName(optivumTimetableIndexItem.getFullName());
        byFullNameAndShortNameAndSchool_id.setShortName(optivumTimetableIndexItem.getShortName());
        byFullNameAndShortNameAndSchool_id.setLink(optivumTimetableIndexItem.getLink());
        byFullNameAndShortNameAndSchool_id.setTimetableType(optivumTimetableIndexItem.getTimetableType());

        optivumTimetableIndexItemRepository.save(byFullNameAndShortNameAndSchool_id);
    }

    private void updateSchoolsPopularity(@PathVariable Long schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);

        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setPopularity(school.getPopularity() + 1L);
        }
    }

    private void saveGroup(Schedule schedule) {
        schedule.getMon().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getTue().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getWed().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getThu().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getFri().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getSat().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
        schedule.getSun().forEach(lesson -> groupRepository.saveAll(lesson.getGroups()));
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
}
