package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.domain.TimetableType;

public interface TimetableTypeRecognizer {

    TimetableType recognizeTimetableType(String url) throws UnknownTimetableTypeException;
}
