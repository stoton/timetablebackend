package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.domain.Timetable;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

public interface TimetableParser {
    Timetable parseTimetable(String url, TimetableTypeRecognizer timetableTypeRecognizer) throws UnknownTimetableTypeException;
}
