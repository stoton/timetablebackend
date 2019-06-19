package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableTeacherStrategy;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OptivumTimetableTeacherParseAllLessonsTests {

    @Mock
    OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;

    @Test
    public void parseTeacherTimetableWhenTimetableIsEmptyTest() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();
        expected.setSchedule(new Schedule());

        expected.setName("A. Mazur");
        expected.setType("teacher");

        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getTue().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getWed().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getThu().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getFri().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableTeacherStrategy(TimetableType.TEACHER, 1L, optivumTimetableIndexItemRepository);


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/teacher.emptyTimetable.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseTeacherTimetableWhenTeacherHasOneClassTest() throws IOException, UnknownTimetableTypeException {
        Timetable expected = new Timetable();
        expected.setSchedule(new Schedule());

        expected.setName("A. Mazur");
        expected.setType("teacher");

        List<Group> groups = new ArrayList<>();
        groups.add(new Group("1 TAG", "A. Mazur", "biologia", "310"));

        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", groups));
        expected.getSchedule().getTue().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getWed().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getThu().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getFri().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableTeacherStrategy(TimetableType.TEACHER, 1L, optivumTimetableIndexItemRepository);


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/teacher.teacherWithOneClass.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void parseTeacherTimetableWhenTeacherHasTwoClassTest() throws IOException, UnknownTimetableTypeException {
        Timetable expected = new Timetable();
        expected.setSchedule(new Schedule());

        expected.setName("L. Kędzior");
        expected.setType("teacher");

        List<Group> groups = new ArrayList<>();
        groups.add(new Group("4 Tż-2/2", "L. Kędzior", "wf", "@"));
        groups.add(new Group("4 TIG-1/3", "L. Kędzior", "wf", "@"));


        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", groups));
        expected.getSchedule().getTue().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getWed().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getThu().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getFri().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableTeacherStrategy(TimetableType.TEACHER, 1L, optivumTimetableIndexItemRepository);


        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/teacher.teacherWithTwoClasses.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }
}



