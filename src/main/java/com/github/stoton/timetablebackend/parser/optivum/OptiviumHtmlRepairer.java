package com.github.stoton.timetablebackend.parser.optivum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class OptiviumHtmlRepairer {

    private static final String PART_OF_CLASS = "(-[0-9]/[0-9])";
    private static final String STUDENT_CLASS = "class=\"p\"";
    private static final String TEACHER_CLASS = "class=\"n\"";
    private static final String ROOM_CLASS = "class=\"s\"";
    private static final int NO_OCCURRENCE = -1;

    public String fixHtml(String html) {
        int indexOfElementToMerge = NO_OCCURRENCE;

        Pattern pattern = Pattern.compile(PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        int groupNumber = 0;

        while (!isHtmlValid(html)) {
            StringBuilder correctHtml = new StringBuilder();

            if (matcher.find()) {
                indexOfElementToMerge = html.indexOf(matcher.group(groupNumber));
                groupNumber++;
            }

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(htmlUntilPartToMerge(html, indexOfElementToMerge - 7))
                    .append(partOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(endOfSpan(html, indexOfElementToMerge - 7, indexOfElementToMerge))
                    .append(endOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }

        return html;
    }

    public boolean isHtmlValid(String html) {

        int studentTags = html
                .split(STUDENT_CLASS, -1).length - 1;

        int teacherTags = html
                .split(TEACHER_CLASS, -1).length - 1;

        int roomTags = html
                .split(ROOM_CLASS, -1).length - 1;

        return Stream
                    .of(studentTags, teacherTags, roomTags)
                    .distinct()
                    .count() == 1;
    }

    private String htmlUntilPartToMerge(String html, int end) {
        return html.substring(0, end);
    }

    private String partOfClassToMerge(String html, int start, int end) {
        return html.substring(start, end);
    }

    private String endOfSpan(String html, int start, int end) {
        return html.substring(start, end);
    }

    private String endOfHtml(String html, int start) {
        return html.substring(start).replaceFirst("\"p\"", "\"n\"");
    }
}
