package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;

public interface OptivumTimetableStrategy {

    List<Lesson> parseAllLessonsFromHtml(Document document);

    String fixHtml(String html) throws UnknownTimetableTypeException;
}
