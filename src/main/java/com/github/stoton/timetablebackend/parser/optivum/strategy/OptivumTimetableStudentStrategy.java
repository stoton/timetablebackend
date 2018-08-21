package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public  class OptivumTimetableStudentStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        throw new NotImplementedException();
    }

    @Override
    public String fixHtml(String html) {
        int indexOfElementToMerge = NO_OCCURRENCE;

        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        while (!isHtmlValid(html)) {

            StringBuilder correctHtml = new StringBuilder();

            if (matcher.find()) {
                indexOfElementToMerge = html.indexOf(matcher.group())+1;
            }

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 7))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 7, indexOfElementToMerge))
                    .append(appendEndOfHtmlAndReplace(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }
        return html;
    }

    @Override
    public boolean isHtmlValid(String html) {
        int subjectClass = html
                .split(SUBJECT_CLASS, -1).length - 1;

        int teacherTags = html
                .split(TEACHER_CLASS, -1).length - 1;

        int roomTags = html
                .split(ROOM_CLASS, -1).length - 1;

        return subjectClass == teacherTags && subjectClass == roomTags &&
                !hasClassPartOutOfTag(html);
    }

    private String appendEndOfHtmlAndReplace(String html, int start) {
        return html.substring(start).replaceFirst("\"p\"", "\"n\"");
    }
}
