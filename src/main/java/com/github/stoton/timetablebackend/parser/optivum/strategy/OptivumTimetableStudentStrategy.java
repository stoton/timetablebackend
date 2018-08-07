package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.Lesson;
import org.jsoup.nodes.Document;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public  class OptivumTimetableStudentStrategy implements OptivumTimetableStrategy {

    @Override
    public List<Lesson> parseAllLessonsFromHtml(Document document) {
        throw new NotImplementedException();
    }
}
