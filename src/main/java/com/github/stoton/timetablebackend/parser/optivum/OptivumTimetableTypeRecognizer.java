package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.TimetableTypeRecognizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptivumTimetableTypeRecognizer implements TimetableTypeRecognizer {

    private static final char STUDENT_TYPE_SYMBOL = 'o';
    private static final char TEACHER_TYPE_SYMBOL = 'n';
    private static final char ROOM_TYPE_SYMBOL = 's';
    private static final String STUDENT_TITLE = "oddziału";
    private static final String TEACHER_TITLE = "nauczyciela";
    private static final String ROOM_TITLE = "sali";

    @Override
    public TimetableType recognizeTimetableTypeByUrl(String url) throws UnknownTimetableTypeException {

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

    @Override
    public TimetableType recognizeTimetableTypeByContent(String content) throws UnknownTimetableTypeException {

        String titlePattern = "(<title>)(.*?)(</title>)";
        Pattern pattern = Pattern.compile(titlePattern);
        Matcher matcher = pattern.matcher(content);

        String title = "";

        if(matcher.find()) {
            title = matcher.group();
        }

        if(title.contains(STUDENT_TITLE))
            return TimetableType.STUDENT;

        if(title.contains(TEACHER_TITLE))
            return  TimetableType.TEACHER;

        if(title.contains(ROOM_TITLE))
            return TimetableType.ROOM;

        throw new UnknownTimetableTypeException("Unknown timetable type.");

    }

    private char getTimetableTypeSymbolFromUrl(String url) {
        int indexOfTimetableSymbol = url.lastIndexOf('/') + 1;
        url = url.substring(indexOfTimetableSymbol);

        return url.charAt(0);
    }
}
