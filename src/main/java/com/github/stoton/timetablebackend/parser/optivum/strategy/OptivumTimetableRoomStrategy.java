package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableRoomStrategy implements OptivumTimetableStrategy {

    private static final String A_TAG_END = "</a>";

    private TimetableType timetableType;

    public OptivumTimetableRoomStrategy(TimetableType timetableType) {
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
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 7, indexOfElementToMerge-4))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(A_TAG_END)
                    .append(appendEndOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }
        return html;
    }
}
