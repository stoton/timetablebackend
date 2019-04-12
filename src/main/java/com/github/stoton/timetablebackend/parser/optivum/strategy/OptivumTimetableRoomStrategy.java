package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;

import java.util.List;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableRoomStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        return null;
    }

    @Override
    public boolean isHtmlValid(String html) {

        int studentTags = html
                .split(STUDENT_CLASS, -1).length - 1;

        int subjectTags = html
                .split(SUBJECT_CLASS, -1).length - 1;

        int teacherTags = html
                .split(TEACHER_CLASS, -1).length - 1;

        return studentTags == subjectTags && studentTags == teacherTags &&
                !hasClassPartOutOfTag(html);
    }
}
