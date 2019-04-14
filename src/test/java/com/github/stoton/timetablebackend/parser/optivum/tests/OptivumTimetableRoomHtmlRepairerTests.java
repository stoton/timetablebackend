package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumHtmlRepairer;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableRoomStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumTimetableRoomHtmlRepairerTests {

    private OptivumHtmlRepairer optivumHtmlRepairer;

    @Before
    public void init() {
        OptivumTimetableStrategy optivumTimetableStrategy = new OptivumTimetableRoomStrategy(TimetableType.ROOM);
        optivumHtmlRepairer = new OptivumHtmlRepairer(optivumTimetableStrategy);
    }

    @Test
    public void fixRoomHtmlWhenHtmlIsCorrectTest() throws UnknownTimetableTypeException {
        String html = "<td class=\"l\"><a href=\"n10.html\" class=\"n\">CA</a> " +
                "<a href=\"o9.html\" class=\"o\">2TżG-3/3</a> <span class=\"p\">ćwi.ob.i kar</span><br></td>";

        String expected = "<td class=\"l\"><a href=\"n10.html\" class=\"n\">CA</a> " +
                "<a href=\"o9.html\" class=\"o\">2TżG-3/3</a> <span class=\"p\">ćwi.ob.i kar</span><br></td>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixRoomHtmlWhenOneTagIsIncorrectTest() throws UnknownTimetableTypeException {
        String html = "<td class=\"l\"><a href=\"n38.html\" class=\"n\">MG</a> " +
                "<a href=\"o4.html\" class=\"o\">3TIG</a>-3/3 <span class=\"p\">geodez. inż.</span><br></td>";

        String expected = "<td class=\"l\"><a href=\"n38.html\" class=\"n\">MG</a> " +
                "<a href=\"o4.html\" class=\"o\">3TIG-3/3 </a><span class=\"p\">geodez. inż.</span><br></td>";

        String actual = optivumHtmlRepairer.fixHtml(html);
        assertEquals(expected,actual);
    }
}
