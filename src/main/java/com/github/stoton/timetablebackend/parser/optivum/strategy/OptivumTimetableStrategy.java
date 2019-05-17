package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;


public interface OptivumTimetableStrategy {

    Timetable parseAllLessonsFromHtml(Document document) throws UnknownTimetableTypeException;

    String fixHtml(String html) throws UnknownTimetableTypeException;
}
