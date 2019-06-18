package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableParser;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;

public final class ParserFactory {

    public TimetableParser getTimetableParser(TimetableProducerType timetableProducerType) {

        switch (timetableProducerType) {
            case OPTIVUM_TIMETABLE:
                return new OptivumTimetableParser();
            default:
                return new OptivumTimetableParser();
        }
    }
}
