package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.domain.optivium.OptiviumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptiviumTimetableIndexItemsParser {

    private OptivumTimetableTypeRecognizer optivumTimetableTypeRecognizer;

    public OptiviumTimetableIndexItemsParser() {
        optivumTimetableTypeRecognizer = new OptivumTimetableTypeRecognizer();
    }

    private static final String ROOT_URL = "http://szkola.zsat.linuxpl.eu/planlekcji/";

    public List<OptiviumTimetableIndexItem> parseIndexItems(Document html) throws UnknownTimetableTypeException {
        List<OptiviumTimetableIndexItem> optiviumTimetableIndexItems = new ArrayList<>();
        Elements timetableIndexItems = html.select("a");

        for (Element timetableIndexItem : timetableIndexItems) {

            String url = ROOT_URL + timetableIndexItem.attr("href");

            TimetableType timetableType = optivumTimetableTypeRecognizer.recognizeTimetableType(url);

            OptiviumTimetableIndexItem optiviumTimetableIndexItem = new OptiviumTimetableIndexItem();

            String shortText = parseShortName(timetableIndexItem);

            optiviumTimetableIndexItem.setTimetableType(timetableType);
            optiviumTimetableIndexItem.setShortName(shortText);
            optiviumTimetableIndexItem.setFullName(toStringWithoutShortNameAndWhitespaces(timetableIndexItem));
            optiviumTimetableIndexItem.setLink(url);

            optiviumTimetableIndexItems.add(optiviumTimetableIndexItem);
        }
        return optiviumTimetableIndexItems;
    }

    private String toStringWithoutShortNameAndWhitespaces(Element html) {
        return html.text().replaceFirst("\\([A-Z]+\\)", "").replaceAll("\\s", "");
    }

    private String parseShortName(Element element) {

        Pattern pattern = Pattern.compile("\\([A-Z]+\\)");
        Matcher matcher = pattern.matcher(element.toString());

        String shortName = "";
        if (matcher.find()) {
            shortName = matcher.group().substring(1, 3);
        }

        return shortName;
    }
}