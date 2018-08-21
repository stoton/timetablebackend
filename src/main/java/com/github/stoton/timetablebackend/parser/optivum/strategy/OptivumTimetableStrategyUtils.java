package com.github.stoton.timetablebackend.parser.optivum.strategy;

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


}
