package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.nodes.Document;


import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableStudentStrategy implements OptivumTimetableStrategy {

    private TimetableType timetableType;
    private Long schoolId;
    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;

    public OptivumTimetableStudentStrategy(TimetableType timetableType,
                                           Long schoolId, OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.timetableType = timetableType;
        this.schoolId = schoolId;
    }

    @Override
    public Timetable parseAllLessonsFromHtml(Document document) throws UnknownTimetableTypeException {

        Timetable timetable = new Timetable();

        String student = document.select(".tytulnapis")
                .first().text();

        student = student.replaceFirst("\\d+", "$0 ");

        timetable.setName(student);
        timetable.setType(timetableType.toString());

        return getTimetable(document, timetable, student, timetableType, schoolId, optivumTimetableIndexItemRepository);
    }

    @Override
    public String fixHtml(String html) throws UnknownTimetableTypeException {
        return OptivumTimetableStrategyUtils.fixHtml(html, timetableType);
    }
}
