package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.parser.optivum.OptiviumHtmlRepairer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumHtmlRepairerTests {

    private OptiviumHtmlRepairer optiviumHtmlRepairer;

    @Before
    public void init() {
        optiviumHtmlRepairer = new OptiviumHtmlRepairer();
    }

    @Test
    public void isHtmlValidWhenHtmlIsCorrectTest() {
        String html = "<span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a>";

        boolean actual = optiviumHtmlRepairer.isHtmlValid(html);

        assertTrue(actual);
    }

    @Test
    public void isHtmlValidWhenHtmlIsIncorrectTest() {
        String html = "<span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br>" +
                "<span style=\"font-size:85%\"><span class=\"p\">wf-2/2" +
                "</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span>";

        boolean actual = optiviumHtmlRepairer.isHtmlValid(html);
        assertFalse(actual);
    }

    @Test
    public void fixHtmlWhenIsCorrectTest() {
        String html = "<span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a>";
        String expected = "<span class=\"p\">matematyka</span> <a href=\"n29.html\" class=\"n\">KJ</a> <a href=\"s8.html\" class=\"s\">204</a>";

        String actual = optiviumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixHtmlWhenOneTagIsIncorrectTest() {
        String html = "<span class=\"p\">wf</span>-1/2 <span class=\"p\">#W6</span> <span class=\"s\">@</span><br>" +
                "<span style=\"font-size:85%\"><span class=\"p\">wf-2/2" +
                "</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span>";

        String expected = "<span class=\"p\">wf-1/2 </span><span class=\"n\">#W6</span> <span class=\"s\">@</span><br>" +
                "<span style=\"font-size:85%\"><span class=\"p\">wf-2/2" +
                "</span> <a href=\"n8.html\" class=\"n\">CU</a> <span class=\"s\">@</span></span>";

        String actual = optiviumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }

    @Test
    public void fixHtmlWhenTwoTagsAreIncorrectTest() {
        String html = "<span class=\"p\">wf</span>-1/2 <span class=\"p\">#W1</span>" +
                " <span class=\"s\">@</span><br><span class=\"p\">wf</span>-2/2 " +
                "<span class=\"p\">#W2</span> <span class=\"s\">@</span>";

        String expected = "<span class=\"p\">wf-1/2 </span><span class=\"n\">#W1</span>" +
                " <span class=\"s\">@</span><br><span class=\"p\">wf-2/2 </span>" +
                "<span class=\"n\">#W2</span> <span class=\"s\">@</span>";

        String actual = optiviumHtmlRepairer.fixHtml(html);

        assertEquals(expected, actual);
    }
}
