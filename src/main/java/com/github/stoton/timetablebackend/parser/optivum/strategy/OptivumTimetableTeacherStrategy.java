package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;


public class OptivumTimetableTeacherStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        return null;
    }

    @Override
    public String fixHtml(String html) throws UnknownTimetableTypeException {
        return null;
    }

}
