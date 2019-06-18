package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.TimetableIndexItemsParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptivumTimetableIndexItemsParser implements TimetableIndexItemsParser<OptivumTimetableIndexItem> {

    private OptivumTimetableTypeRecognizer optivumTimetableTypeRecognizer;

    public OptivumTimetableIndexItemsParser() {
        optivumTimetableTypeRecognizer = new OptivumTimetableTypeRecognizer();
    }

    public List<OptivumTimetableIndexItem> parseIndexItems(Document html, String timetableHref) throws UnknownTimetableTypeException {
        List<OptivumTimetableIndexItem> optivumTimetableIndexItems = new ArrayList<>();

        Pattern pattern = Pattern.compile("(.*?)index.html");
        Matcher matcher = pattern.matcher(timetableHref);

        if (matcher.find()) {
            timetableHref = matcher.group(1);
        }

        Elements timetableIndexItems = html.select("a");

        for (Element timetableIndexItem : timetableIndexItems) {

            String href = timetableIndexItem.attr("href");

            if (!href.startsWith("plany") || !href.endsWith("html")) continue;
            String url = timetableHref + timetableIndexItem.attr("href");

            TimetableType timetableType = optivumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

            OptivumTimetableIndexItem optivumTimetableIndexItem = new OptivumTimetableIndexItem();

            String shortText = parseShortName(timetableIndexItem);

            optivumTimetableIndexItem.setTimetableType(timetableType);
            optivumTimetableIndexItem.setShortName(shortText);
            optivumTimetableIndexItem.setFullName(toStringWithoutShortNameAndWhitespaces(timetableIndexItem, timetableType));
            optivumTimetableIndexItem.setLink(url);

            optivumTimetableIndexItems.add(optivumTimetableIndexItem);
        }
        return optivumTimetableIndexItems;
    }

    private String toStringWithoutShortNameAndWhitespaces(Element html, TimetableType timetableType) {
        String fullName = html.text().replaceFirst("\\([A-ZŁŚĆa-z]+\\)", "")
                .replaceAll("\\s", "");

        return timetableType.equals(TimetableType.TEACHER) ? fullName.replace(".", ". ") : fullName;
    }

    private String parseShortName(Element element) {

        Pattern pattern = Pattern.compile("\\([A-Za-zŁŚĆ]+\\)");
        Matcher matcher = pattern.matcher(element.toString());

        String shortName = "";
        if (matcher.find()) {
            shortName = matcher.group().substring(1, 3);
        }

        return shortName;
    }
}
