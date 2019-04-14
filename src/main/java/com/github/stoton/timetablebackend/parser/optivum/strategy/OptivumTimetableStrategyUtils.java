package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OptivumTimetableStrategyUtils {

    static final String OUTER_PART_OF_CLASS = ">-[0-9]/[0-9]";
    static final String SUBJECT_CLASS = "class=\"p\"";
    static final String STUDENT_CLASS = "class=\"o\"";
    static final String TEACHER_CLASS = "class=\"n\"";
    static final String ROOM_CLASS = "class=\"s\"";
    static final int NO_OCCURRENCE = -1;


    static String appendHtmlUntilPartToMerge(String html, int end) {
        return html.substring(0, end);
    }

    static String appendPartOfClassToMerge(String html, int start, int end) {
        return html.substring(start, end);
    }

    static String appendEndOfSpan(String html, int start, int end) {
        return html.substring(start, end);
    }

    static String appendEndOfHtml(String html, int start) {
        return html.substring(start);
    }

    static boolean hasClassPartOutOfTag(String html) {
        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        return matcher.find();
    }

    static boolean isHtmlValid(String html, TimetableType timetableType) throws UnknownTimetableTypeException {

        int studentTags = html
                .split(STUDENT_CLASS, -1).length - 1;

        int subjectTagsCount = html
                .split(SUBJECT_CLASS, -1).length - 1;

        int teacherTagsCount = html
                .split(TEACHER_CLASS, -1).length - 1;

        int roomTagsCount = html
                .split(ROOM_CLASS, -1).length - 1;


        switch (timetableType) {
            case ROOM:
                return studentTags == subjectTagsCount && studentTags == teacherTagsCount &&
                        !hasClassPartOutOfTag(html);
            case STUDENT:
                return subjectTagsCount == teacherTagsCount && subjectTagsCount == roomTagsCount &&
                        !hasClassPartOutOfTag(html);
            case TEACHER:
                return !hasClassPartOutOfTag(html);

            default:
                throw new UnknownTimetableTypeException("TimetableType cannot be recognized.");
        }
    }

    static int getIndexOfElementToMerge(String html) {
        int indexOfElementToMerge = NO_OCCURRENCE;

        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            indexOfElementToMerge = html.indexOf(matcher.group()) + 1;
        }

        return indexOfElementToMerge;
    }
}
