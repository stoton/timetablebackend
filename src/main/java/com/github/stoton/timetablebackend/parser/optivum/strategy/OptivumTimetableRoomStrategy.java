package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;

import java.util.List;

public class OptivumTimetableRoomStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        return null;
    }
}
