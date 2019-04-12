package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.domain.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptivumTimetableIndexItemsParser {

    private OptivumTimetableTypeRecognizer optivumTimetableTypeRecognizer;

    public OptivumTimetableIndexItemsParser() {
        optivumTimetableTypeRecognizer = new OptivumTimetableTypeRecognizer();
    }

    private static final String ROOT_URL = "http://szkola.zsat.linuxpl.eu/planlekcji/";

    public List<OptivumTimetableIndexItem> parseIndexItems(Document html) throws UnknownTimetableTypeException {
        List<OptivumTimetableIndexItem> optivumTimetableIndexItems = new ArrayList<>();


        Elements timetableIndexItems = html.select("a");

        for (Element timetableIndexItem : timetableIndexItems) {

            String href = timetableIndexItem.attr("href");


            System.out.println(href);
            if(!href.startsWith("plany") || !href.endsWith("html")) continue;
            String url = ROOT_URL + timetableIndexItem.attr("href");


            TimetableType timetableType = optivumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

            OptivumTimetableIndexItem optivumTimetableIndexItem = new OptivumTimetableIndexItem();

            String shortText = parseShortName(timetableIndexItem);

            optivumTimetableIndexItem.setTimetableType(timetableType);
            optivumTimetableIndexItem.setShortName(shortText);
            optivumTimetableIndexItem.setFullName(toStringWithoutShortNameAndWhitespaces(timetableIndexItem));
            optivumTimetableIndexItem.setLink(url);

            optivumTimetableIndexItems.add(optivumTimetableIndexItem);
        }
        return optivumTimetableIndexItems;
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
