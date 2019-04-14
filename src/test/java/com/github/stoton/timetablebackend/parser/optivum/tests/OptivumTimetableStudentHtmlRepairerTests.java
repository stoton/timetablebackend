package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumHtmlRepairer;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStudentStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumTimetableStudentHtmlRepairerTests {

    private OptivumHtmlRepairer optivumHtmlRepairer;

    @Before
    public void init() {
        OptivumTimetableStrategy optivumTimetableStrategy = new OptivumTimetableStudentStrategy(TimetableType.STUDENT);
        optivumHtmlRepairer = new OptivumHtmlRepairer(optivumTimetableStrategy);
    }

    @Test
    public void fixHtmlWhenIsCorrectTest() throws UnknownTimetableTypeException {
        String html = "<span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a>";
        String expected = "<span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixHtmlWhenOneTagIsIncorrectTest() throws UnknownTimetableTypeException {
        String html = "<span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br>" +
                "<span style=\"font-size:85%\"><span class=\"p\">wf-2/2" +
                "</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span>";

        String expected = "<span class=\"p\">wf-1/2 </span><span class=\"n\">#W6</span> <span class=\"s\">@</span><br>" +
                "<span style=\"font-size:85%\"><span class=\"p\">wf-2/2" +
                "</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixHtmlWhenTwoTagsAreIncorrectTest() throws UnknownTimetableTypeException {
        String html = "<span class=\"p\">wf</span>-1/2 <span class=\"p\">#W1</span>" +
                " <span class=\"s\">@</span><br><span class=\"p\">wf</span>-2/2 " +
                "<span class=\"p\">#W2</span> <span class=\"s\">@</span>";

        String expected = "<span class=\"p\">wf-1/2 </span><span class=\"n\">#W1</span>" +
                " <span class=\"s\">@</span><br><span class=\"p\">wf-2/2 </span>" +
                "<span class=\"n\">#W2</span> <span class=\"s\">@</span>";

        String actual = optivumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }
}
