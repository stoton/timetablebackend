package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.Group;
import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStudentStrategy;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumTimetableStudentParseAllLessonsTests {

    @Mock
    OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;

    @Test
    public void parseStudentTimetableWhenTimetableIsEmptyTest() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();
        expected.setName("4 Tż");
        expected.setType("student");

        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getTue().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getWed().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getThu().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getFri().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableStudentStrategy(TimetableType.STUDENT, 1L, optivumTimetableIndexItemRepository);

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/student.emptyTimetable.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

        Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);

        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseStudentTimetableHtmlWhenTwoGroupsAreInvalid() throws IOException, UnknownTimetableTypeException {

        Timetable expected = new Timetable();
        expected.setName("4 Tż");
        expected.setType("student");

        List<Group> groups = new ArrayList<>();
        groups.add(new Group("4 Tż-1/2", "#W8", "wf", "@"));
        groups.add(new Group("4 Tż-2/2", "#W9", "wf", "@"));
        expected.getSchedule().getMon().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getTue().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getWed().add(new Lesson(0, "8:00", "8:45", groups));
        expected.getSchedule().getThu().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));
        expected.getSchedule().getFri().add(new Lesson(0, "8:00", "8:45", new ArrayList<>()));

        OptivumTimetableStrategy optivumTimetableStrategy =
                new OptivumTimetableStudentStrategy(TimetableType.STUDENT, 58L, optivumTimetableIndexItemRepository);

        OptivumTimetableIndexItem optivumTimetableIndexItemW8 = new OptivumTimetableIndexItem(null, null, "#W8",
                null,  null, null);

        OptivumTimetableIndexItem optivumTimetableIndexItemW9 = new OptivumTimetableIndexItem(null, null, "#W9",
                null,  null, null);


        when(optivumTimetableIndexItemRepository.findFirstByShortNameAndSchool_Id("#W8", 58L)).thenReturn(optivumTimetableIndexItemW8);
        when(optivumTimetableIndexItemRepository.findFirstByShortNameAndSchool_Id("#W9", 58L)).thenReturn(optivumTimetableIndexItemW9);

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("OptivumTimetableHtmls/student.twoInvalidGroupNumer.html");

        assert file != null;

        String html = CharStreams.toString(new InputStreamReader(
                file, Charsets.UTF_8));

            Document document = Jsoup.parse(html);

        Timetable actual = optivumTimetableStrategy.parseAllLessonsFromHtml(document);
        actual.setTimestamp(null);

        Assert.assertEquals(expected, actual);
    }
}
