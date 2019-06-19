package com.github.stoton.timetablebackend.service;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.Schedule;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.UnsuccessfulParsedTimetable;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.ParserFactory;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableIndexItemsParser;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import com.github.stoton.timetablebackend.repository.*;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    private final SchoolRepository schoolRepository;
    private final TimetableRepository timetableRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;

    private final Logger logger;

    private List<UnsuccessfulParsedTimetable> unsuccessfulParsedTimetables = new ArrayList<>();

    @Autowired
    public TimetableServiceImpl(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, SchoolRepository schoolRepository, TimetableRepository timetableRepository, ScheduleRepository scheduleRepository, LessonRepository lessonRepository, GroupRepository groupRepository, Logger logger) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.schoolRepository = schoolRepository;
        this.timetableRepository = timetableRepository;
        this.scheduleRepository = scheduleRepository;
        this.lessonRepository = lessonRepository;
        this.groupRepository = groupRepository;
        this.logger = logger;
    }

    @Override
    public List<Timetable> getAllTimetableHeadersBySchoolId(Long schoolId) {
        return timetableRepository.findTimetableHeadersBySchoolId(schoolId);
    }

    @Override
    public Timetable getTimetableByTimetableIdAndSchoolId(Long timetableId, Long schoolId) {
        return timetableRepository.findByIdAndSchoolId(schoolId, timetableId);
    }

    @Async
    @Override
    public Future<Boolean> parseAllTimetablesBySchoolId(Long schoolId) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);

        if (!optionalSchool.isPresent()) {
            logger.error("School not found, schoolId: " + schoolId);
            return AsyncResult.forValue(false);
        }

        School school = optionalSchool.get();

        TimetableProducerType timetableProducerType;

        try {
            timetableProducerType = TimetableProducerRegonizer
                    .recognizeTimetable(school.getTimetableHref());
        } catch (UnknownTimetableProducerType unknownTimetableProducerType) {
            logger.error(unknownTimetableProducerType.getMessage());
            return AsyncResult.forValue(false);
        }

        unsuccessfulParsedTimetables.clear();

        if (TimetableProducerType.OPTIVUM_TIMETABLE.equals(timetableProducerType)) {

            String url = getTimetableIndexItemsListUrl(school.getTimetableHref());

            Document document;
            try {
                document = Jsoup.connect(url + "/lista.html").get();
            } catch (IOException e) {
                logger.error(e.getMessage());
                return AsyncResult.forValue(false);
            }

            OptivumTimetableIndexItemsParser optivumTimetableIndexItemsParser = new OptivumTimetableIndexItemsParser();

            List<OptivumTimetableIndexItem> optivumTimetableIndexItems;
            try {
                optivumTimetableIndexItems = optivumTimetableIndexItemsParser.parseIndexItems(document, school.getTimetableHref());
            } catch (UnknownTimetableTypeException e) {
                logger.error(e.getMessage());
                return AsyncResult.forValue(false);
            }

            optivumTimetableIndexItems
                    .forEach(optivumTimetableIndexItem -> saveOptivumTimetableIndexItem(optivumTimetableIndexItem, school));

            List<OptivumTimetableIndexItem> optivumTimetableIndexItemList = optivumTimetableIndexItemRepository.findAllBySchoolId(schoolId);
            optivumTimetableIndexItemList
                    .parallelStream()
                    .forEach(optivumTimetableIndexItem -> parseAndSaveTimetable(optivumTimetableIndexItem, school));
        }

        return AsyncResult.forValue(true);
    }

    @Override
    public void updateSchoolPopularity(Long schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);

        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setPopularity(school.getPopularity() + 1L);
        }
    }

    @Override
    public List<UnsuccessfulParsedTimetable> getListOfUnsuccessfulParsesOfLastParsing() {
        return this.unsuccessfulParsedTimetables;
    }

    private void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    private void parseAndSaveTimetable(OptivumTimetableIndexItem timetableIndexItem, School school) {
        ParserFactory parserFactory = new ParserFactory();
        TimetableParser timetableParser = parserFactory.getTimetableParser(TimetableProducerType.OPTIVUM_TIMETABLE);

        Timetable timetable;

        try {
            timetable = timetableParser.parseTimetable(timetableIndexItem.getLink(), new OptivumTimetableTypeRecognizer(), school.getId());
        } catch (UnknownTimetableTypeException | IOException e) {

            UnsuccessfulParsedTimetable unsuccessfulParsedTimetable = new UnsuccessfulParsedTimetable();
            unsuccessfulParsedTimetable.setId(timetableIndexItem.getId());
            unsuccessfulParsedTimetable.setSchoolId(timetableIndexItem.getSchool().getId());
            unsuccessfulParsedTimetable.setName(timetableIndexItem.getFullName());
            unsuccessfulParsedTimetable.setType(timetableIndexItem.getTimetableType().toString());

            unsuccessfulParsedTimetables.add(unsuccessfulParsedTimetable);

            e.printStackTrace();
            return;
        }

        Timetable currentTimetable = timetableRepository.
                findBySchoolIdAndNameAndType(school.getId(), timetable.getName(), timetable.getType());

        if (currentTimetable != null) {
            if (!currentTimetable.getSchedule().equals(timetable.getSchedule())) {
                currentTimetable.setSchedule(timetable.getSchedule());

                currentTimetable.setTimestamp(timetable.getTimestamp());
                saveSchedule(currentTimetable.getSchedule());
                saveLessons(currentTimetable.getSchedule());
                saveGroup(currentTimetable.getSchedule());

                timetableRepository.save(currentTimetable);

                school.setTimestamp(timetable.getTimestamp());
                schoolRepository.save(school);

            }
        } else {
            saveSchedule(timetable.getSchedule());
            saveLessons(timetable.getSchedule());
            saveGroup(timetable.getSchedule());

            timetable.setSchoolId(school.getId());
            timetableRepository.save(timetable);

            school.setTimestamp(timetable.getTimestamp());
            schoolRepository.save(school);
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

    private String getTimetableIndexItemsListUrl(String timetableHref) {
        Pattern pattern = Pattern.compile("(.*?)index.html");
        Matcher matcher = pattern.matcher(timetableHref);

        String url = "";

        if (matcher.find()) {
            url = matcher.group(1);
        }

        return url;
    }
}
