package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.NO_OCCURRENCE;
import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.OUTER_PART_OF_CLASS;


public interface OptivumTimetableStrategy {

    List<Lesson> parseAllLessonsFromHtml(Document document);

    default String fixHtml(String html) {


        int indexOfElementToMerge = NO_OCCURRENCE;

        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        while (!isHtmlValid(html)) {
            StringBuilder correctHtml = new StringBuilder();

            if (matcher.find()) {
                indexOfElementToMerge = html.indexOf(matcher.group()) + 1;

            }

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(OptivumTimetableStrategyUtils.appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 4))
                    .append(OptivumTimetableStrategyUtils.appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(OptivumTimetableStrategyUtils.appendEndOfSpan(html, indexOfElementToMerge - 4, indexOfElementToMerge))
                    .append(OptivumTimetableStrategyUtils.appendEndOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
            html = html.replaceAll(",", "");
        }

        return html;
    }

    boolean isHtmlValid(String html);

}
