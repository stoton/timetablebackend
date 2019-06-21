package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableRoomStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;

import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OptivumTimetableRoomHtmlAllLessonsTests {

    @Mock
    OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;

    @Test
    public void parseTimetableRoomHtmlWhenGroupNumberIsInvalidTest() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();
        expected.setSchedule(new Schedule());

        expected.setName("112(1) j.ang");
        expected.setType("room");

        List<Group> groups = new ArrayList<>();

        groups.add(new Group("3 TżG-2/3", "S. Nizioł", "pr.pl.żipr.g", "112(1) j.ang"));
        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", groups));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableRoomStrategy(TimetableType.ROOM, 58L, optivumTimetableIndexItemRepository);


        OptivumTimetableIndexItem optivumTimetableIndexItem = new OptivumTimetableIndexItem(58L, "SN", "S. Nizioł",
                "http://szkola.zsat.linuxpl.eu/planlekcji/plany/n37.html",  TimetableType.TEACHER, null);

        when(optivumTimetableIndexItemRepository.findFirstByShortNameAndSchool_Id("SN", 58L)).thenReturn(optivumTimetableIndexItem);

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/room.invalidGroupNumer.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseTimetableRoomHtmlWhenTimetableIsEmptyTest() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();

        expected.setSchedule(new Schedule());

        expected.setName("inf4 zapl.kuch");
        expected.setType("room");

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableRoomStrategy(TimetableType.ROOM, 58L, optivumTimetableIndexItemRepository);


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/room.emptyTimetable.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseTimetableRoomHtmlWhenTimetableHasNoHtmlDataTest() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();

        expected.setSchedule(new Schedule());

        expected.setName("209 j.ang");
        expected.setType("room");

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableRoomStrategy(TimetableType.ROOM, 1L, optivumTimetableIndexItemRepository);


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/room.timetableWithoutHtmlData.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }
}
