package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

public interface TimetableTypeRecognizer {

    TimetableType recognizeTimetableType(String url) throws UnknownTimetableTypeException;
}
