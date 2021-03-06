package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumHtmlRepairer;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableTeacherStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OptivumTimetableTeacherHtmlRepairerTests {

    private OptivumHtmlRepairer optivumHtmlRepairer;

    @Before
    public void init() {
        OptivumTimetableStrategy optivumTimetableStrategy = new OptivumTimetableTeacherStrategy(TimetableType.TEACHER, 1L, null);
        optivumHtmlRepairer = new OptivumHtmlRepairer(optivumTimetableStrategy);
    }

    @Test
    public void fixTeacherHtmlWhenOneTagIsIncorrectTest() throws UnknownTimetableTypeException {
        String html = "<td class=\"l\"><span style=\"font-size:85%\"><a href=\"o11.html\" class=\"o\">2Tia</a>-2/2 "
                + "<span class=\"p\">pra.w ob.at</span> <a href=\"s30.html\" class=\"s\">216</a><br></span></td>";

        String expected = "<td class=\"l\"><span style=\"font-size:85%\"><a href=\"o11.html\" class=\"o\">2Tia-2/2 </a>"
                + "<span class=\"p\">pra.w ob.at</span> <a href=\"s30.html\" class=\"s\">216</a><br></span></td>";

        String actual = optivumHtmlRepairer.fixHtml(html);
        assertEquals(expected, actual);
    }

    @Test
    public void fixTeacherHtmlWhenTwoTagsAreIncorrectTest() throws UnknownTimetableTypeException {
        String html = "<td class=\"l\"><a href=\"o2.html\" class=\"o\">3Tż</a>-1/2,<a href=\"o4.html\" class=\"o\">3TIG</a>-1/3 " +
                "<span class=\"p\">wf</span> <span class=\"s\">@</span><br></td>";

        String expected = "<td class=\"l\"><a href=\"o2.html\" class=\"o\">3Tż-1/2</a><a href=\"o4.html\" class=\"o\">3TIG-1/3 </a>" +
                "<span class=\"p\">wf</span> <span class=\"s\">@</span><br></td>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixTeacherHtmlWhenTeacherHasTwoClassOnOneLessonTest() throws UnknownTimetableTypeException {
        String html = "<td class=\"l\"><a href=\"student.twoInvalidGroupNumer.html\" class=\"o\">4Tż</a>-2/2,<a href=\"o3.html\" class=\"o\">4TIG</a>-1/3 " +
                "<span class=\"p\">wf</span> <span class=\"s\">@</span><br></td>";

        String expected = "<td class=\"l\"><a href=\"student.twoInvalidGroupNumer.html\" class=\"o\">4Tż-2/2</a><a href=\"o3.html\" class=\"o\">4TIG-1/3 </a>" +
                "<span class=\"p\">wf</span> <span class=\"s\">@</span><br></td>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

}
