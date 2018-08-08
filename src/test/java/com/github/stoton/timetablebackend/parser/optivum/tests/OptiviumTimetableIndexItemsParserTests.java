package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.domain.optivium.OptiviumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptiviumTimetableIndexItemsParser;
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
public class OptiviumTimetableIndexItemsParserTests {

    private OptiviumTimetableIndexItemsParser optiviumTimetableIndexItemsParser;


    @Before
    public void init() {
        optiviumTimetableIndexItemsParser = new OptiviumTimetableIndexItemsParser();
    }

    @Test
    public void parseStudentTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/o1.html\" target=\"plan\">3Zm</a></li>");

        OptiviumTimetableIndexItem optiviumTimetableIndexItem =
                new OptiviumTimetableIndexItem();

        optiviumTimetableIndexItem.setShortName("");
        optiviumTimetableIndexItem.setFullName("3Zm");
        optiviumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/o1.html");
        optiviumTimetableIndexItem.setTimetableType(TimetableType.STUDENT);

        List<OptiviumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optiviumTimetableIndexItem);

        List<OptiviumTimetableIndexItem> actual =
                optiviumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

    @Test
    public void parseTeacherTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/n1.html\" target=\"plan\">A.Mazur (MA)</a></li>");

        OptiviumTimetableIndexItem optiviumTimetableIndexItem =
                new OptiviumTimetableIndexItem();

        optiviumTimetableIndexItem.setShortName("MA");
        optiviumTimetableIndexItem.setFullName("A.Mazur");
        optiviumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/n1.html");
        optiviumTimetableIndexItem.setTimetableType(TimetableType.TEACHER);

        List<OptiviumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optiviumTimetableIndexItem);

        List<OptiviumTimetableIndexItem> actual =
                optiviumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

    @Test
    public void parseRoomTimetableIndexItemWhenHtmlIsCorrect() throws UnknownTimetableTypeException {
        Document html = Jsoup.parse("<li><a href=\"plany/s31.html\" target=\"plan\">112(1) j.ang</a></li>");

        OptiviumTimetableIndexItem optiviumTimetableIndexItem =
                new OptiviumTimetableIndexItem();

        optiviumTimetableIndexItem.setShortName("");
        optiviumTimetableIndexItem.setFullName("112(1)j.ang");
        optiviumTimetableIndexItem.setLink("http://szkola.zsat.linuxpl.eu/planlekcji/plany/s31.html");
        optiviumTimetableIndexItem.setTimetableType(TimetableType.ROOM);

        List<OptiviumTimetableIndexItem> expected =
                new ArrayList<>();
        expected.add(optiviumTimetableIndexItem);

        List<OptiviumTimetableIndexItem> actual =
                optiviumTimetableIndexItemsParser.parseIndexItems(html);

        assertEquals(expected, actual);
    }

}
