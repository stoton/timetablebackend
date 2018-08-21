package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

public interface TimetableTypeRecognizer {

    TimetableType recognizeTimetableTypeByUrl(String url) throws UnknownTimetableTypeException;

    TimetableType recognizeTimetableTypeByContent(String content) throws UnknownTimetableTypeException;
}
