package com.github.stoton.timetablebackend.service;

import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TimetableProducerRegonizer {

    public static TimetableProducerType recognizeTimetable(String url) throws UnknownTimetableProducerType {

        Document html;

        try {
            html = Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new UnknownTimetableProducerType("Url is incorrect.");

        }

        if (isOptivumTimetable(html)) return TimetableProducerType.OPTIVUM_TIMETABLE;

        throw new UnknownTimetableProducerType("Timetable producer seems to be unknown.");
    }

    private static boolean isOptivumTimetable(Document document) {
        Element description = document.getElementsByTag("meta")
                .get(2);

        return description.attr("content").contains("f");
    }
}
