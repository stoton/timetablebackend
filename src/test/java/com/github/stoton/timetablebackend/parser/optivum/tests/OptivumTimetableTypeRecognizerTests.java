package com.github.stoton.timetablebackend.parser.optivum.tests;

import com.github.stoton.timetablebackend.domain.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableTypeRecognizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptivumTimetableTypeRecognizerTests {

    private OptivumTimetableTypeRecognizer optiviumTimetableTypeRecognizer;

    @Before
    public void init() {
        optiviumTimetableTypeRecognizer = new OptivumTimetableTypeRecognizer();
    }

    @Test
    public void recognizeStudentTimetableWhenUrlIsCorrect() throws UnknownTimetableTypeException {
        String url = "http://szkola.zsat.linuxpl.eu/planlekcji/plany/o1.html";

        TimetableType expected = TimetableType.STUDENT;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

        assertEquals(expected, actual);
    }

    @Test
    public void recognizeTeacherTimetableWhenUrlIsCorrect() throws UnknownTimetableTypeException {
        String url = "http://szkola.zsat.linuxpl.eu/planlekcji/plany/n18.html";

        TimetableType expected = TimetableType.TEACHER;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

        assertEquals(expected, actual);
    }

    @Test
    public void recognizeRoomTimetableWhenUrlIsCorrect() throws UnknownTimetableTypeException {
        String url = "http://szkola.zsat.linuxpl.eu/planlekcji/plany/s12.html";

        TimetableType expected = TimetableType.ROOM;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

        assertEquals(expected, actual);
    }


    @Test(expected = UnknownTimetableTypeException.class)
    public void recognizeTimetableWhenUrlIsIncorrect() throws UnknownTimetableTypeException {
        String url = "http://google.com";

        optiviumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);
    }

    @Test(expected = UnknownTimetableTypeException.class)
    public void recognizeTimetableWhenSymbolIsIncorrect() throws UnknownTimetableTypeException {
        String url =  "http://szkola.zsat.linuxpl.eu/planlekcji/plany/g1.html";

        optiviumTimetableTypeRecognizer.recognizeTimetableTypeByUrl(url);
    }

    @Test
    public void recognizeStudentTimetableWhenContentIsCorrect() throws UnknownTimetableTypeException {
        String content = "<title>Plan lekcji oddziału 113</title>";

        TimetableType expected = TimetableType.STUDENT;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByContent(content);

        assertEquals(expected, actual);
    }

    @Test
    public void recognizeTeacherTimetableWhenContentIsCorrect() throws UnknownTimetableTypeException {
        String content = "<title>Plan lekcji nauczyciela A. Kucharska </title>";

        TimetableType expected = TimetableType.TEACHER;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByContent(content);

        assertEquals(expected, actual);
    }

    @Test
    public void recognizeRoomTimetableWhenContentIsCorrect() throws UnknownTimetableTypeException {
        String content = "<title>Plan lekcji sali 133 </title>";

        TimetableType expected = TimetableType.ROOM;

        TimetableType actual = optiviumTimetableTypeRecognizer.recognizeTimetableTypeByContent(content);

        assertEquals(expected, actual);
    }

    @Test(expected = UnknownTimetableTypeException.class)
    public void recognizeTimetableWhenContentIsIncorrect() throws UnknownTimetableTypeException {
        String content = "incorrect something sala";


        optiviumTimetableTypeRecognizer.recognizeTimetableTypeByContent(content);


    }
}
