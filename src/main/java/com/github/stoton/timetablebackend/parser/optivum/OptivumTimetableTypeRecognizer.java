package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.TimetableTypeRecognizer;

public class OptivumTimetableTypeRecognizer implements TimetableTypeRecognizer {

    private static final char STUDENT_TYPE_SYMBOL = 'o';
    private static final char TEACHER_TYPE_SYMBOL = 'n';
    private static final char ROOM_TYPE_SYMBOL = 's';

    @Override
    public TimetableType recognizeTimetableType(String url) throws UnknownTimetableTypeException {

        char timetableTypeSymbol = getTimetableTypeSymbolFromUrl(url);

        switch (timetableTypeSymbol) {
            case STUDENT_TYPE_SYMBOL:
                return TimetableType.STUDENT;

            case TEACHER_TYPE_SYMBOL:
                return TimetableType.TEACHER;

            case ROOM_TYPE_SYMBOL:
                return TimetableType.ROOM;

            default:
                throw new UnknownTimetableTypeException("Unknown timetable type.");

        }
    }

    private char getTimetableTypeSymbolFromUrl(String url) {
        int indexOfTimetableSymbol = url.lastIndexOf('/') + 1;
        url = url.substring(indexOfTimetableSymbol);

        return url.charAt(0);
    }
}
