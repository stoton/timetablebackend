package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.domain.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableIndexItemsParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumTimetableIndexItemsParserTests {

    private OptivumTimetableIndexItemsParser optivumTimetableIndexItemsParser;


    @Before
    public void init() {
        optivumTimetableIndexItemsParser = new OptivumTimetableIndexItemsParser();
    }

    @Test
    public void parseStudentTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/o1.html\" target=\"plan\">3Zm</a></li>");

        OptivumTimetableIndexItem optivumTimetableIndexItem =
                new OptivumTimetableIndexItem();

        optivumTimetableIndexItem.setShortName("");
        optivumTimetableIndexItem.setFullName("3Zm");
        optivumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/o1.html");
        optivumTimetableIndexItem.setTimetableType(TimetableType.STUDENT);

        List<OptivumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optivumTimetableIndexItem);

        List<OptivumTimetableIndexItem> actual =
                optivumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

    @Test
    public void parseTeacherTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/n1.html\" target=\"plan\">A.Mazur (MA)</a></li>");

        OptivumTimetableIndexItem optivumTimetableIndexItem =
                new OptivumTimetableIndexItem();

        optivumTimetableIndexItem.setShortName("MA");
        optivumTimetableIndexItem.setFullName("A.Mazur");
        optivumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/n1.html");
        optivumTimetableIndexItem.setTimetableType(TimetableType.TEACHER);

        List<OptivumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optivumTimetableIndexItem);

        List<OptivumTimetableIndexItem> actual =
                optivumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

    @Test
    public void parseRoomTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/s31.html\" target=\"plan\">112(1) j.ang</a></li>");

        OptivumTimetableIndexItem optivumTimetableIndexItem =
                new OptivumTimetableIndexItem();

        optivumTimetableIndexItem.setShortName("");
        optivumTimetableIndexItem.setFullName("112(1)j.ang");
        optivumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/s31.html");
        optivumTimetableIndexItem.setTimetableType(TimetableType.ROOM);

        List<OptivumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optivumTimetableIndexItem);

        List<OptivumTimetableIndexItem> actual =
                optivumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

}
