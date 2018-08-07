package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.domain.Timetable;

public interface TimetableParser {
    Timetable parseTimetable(String url, TimetableTypeRecognizer timetableTypeRecognizer) throws UnknownTimetableTypeException;
}
