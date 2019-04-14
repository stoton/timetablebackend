package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableParser;
import com.github.stoton.timetablebackend.properties.TimetableProvider;

public final class ParserFactory {

    public TimetableParser getTimetableParser(TimetableProvider timetableProvider) {

        switch (timetableProvider) {
            case OPTIVUM_TIMETABLE:
                return new OptivumTimetableParser();
            default:
                return null;
        }
    }
}
