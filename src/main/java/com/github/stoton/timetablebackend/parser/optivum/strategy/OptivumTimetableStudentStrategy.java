package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public  class OptivumTimetableStudentStrategy implements OptivumTimetableStrategy {

    private static final String STUDENT_CLASS = "\"p\"";
    private static final String TEACHER_CLASS = "\"n\"";

    private TimetableType timetableType;

    public OptivumTimetableStudentStrategy(TimetableType timetableType) {
        this.timetableType = timetableType;
    }

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        return null;
    }

    @Override
    public String fixHtml(String html) throws UnknownTimetableTypeException {
        int indexOfElementToMerge;

        while (!isHtmlValid(html, timetableType)) {
            StringBuilder correctHtml = new StringBuilder();

            indexOfElementToMerge = getIndexOfElementToMerge(html);

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

    private String appendEndOfHtmlAndReplace(String html, int start) {
        return html.substring(start).replaceFirst(STUDENT_CLASS, TEACHER_CLASS);
    }

}
