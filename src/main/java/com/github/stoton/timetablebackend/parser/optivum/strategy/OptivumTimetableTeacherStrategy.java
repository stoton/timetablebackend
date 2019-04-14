package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableTeacherStrategy implements OptivumTimetableStrategy {

    private TimetableType timetableType;

    public OptivumTimetableTeacherStrategy(TimetableType timetableType) {
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
                    .append(appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 4))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 4, indexOfElementToMerge))
                    .append(appendEndOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }

        html = html.replaceAll(",", "");
        return html;
    }


}
