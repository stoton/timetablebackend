package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.nodes.Document;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableTeacherStrategy implements OptivumTimetableStrategy {

    private TimetableType timetableType;
    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    private Long schoolId;


    public OptivumTimetableTeacherStrategy(TimetableType timetableType, Long schoolId,
                                           OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableType = timetableType;
        this.schoolId = schoolId;
    }

    @Override
    public Timetable parseAllLessonsFromHtml(Document document) throws UnknownTimetableTypeException {

        Timetable timetable = new Timetable();

        String teacher = document.select(".tytulnapis")
                .first().text();

        int indexToCut = teacher.indexOf(' ');

        teacher = teacher.substring(0, indexToCut);

        teacher = teacher.substring(0, 2) + " " + teacher.substring(2);
        timetable.setType(timetableType.toString());

        timetable.setName(teacher);

        return getTimetable(document, timetable, teacher, timetableType, schoolId, optivumTimetableIndexItemRepository);
    }


    @Override
    public String fixHtml(String html) throws UnknownTimetableTypeException {
        return OptivumTimetableStrategyUtils.fixHtml(html, timetableType);
    }
}
