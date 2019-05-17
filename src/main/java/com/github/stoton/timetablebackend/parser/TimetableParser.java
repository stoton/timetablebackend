package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

import java.io.IOException;

public interface TimetableParser {
    Timetable parseTimetable(String url,
                             TimetableTypeRecognizer timetableTypeRecognizer, Long schoolId) throws UnknownTimetableTypeException, IOException;
}
