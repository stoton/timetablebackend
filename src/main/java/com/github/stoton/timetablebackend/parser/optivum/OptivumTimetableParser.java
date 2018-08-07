package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.domain.Timetable;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.TimetableTypeRecognizer;

public class OptivumTimetableParser implements TimetableParser {

    @Override
    public Timetable parseTimetable(String url, TimetableTypeRecognizer timetableTypeRecognizer) throws UnknownTimetableTypeException {
        timetableTypeRecognizer.recognizeTimetableType(url);

        return null;
    }
}
