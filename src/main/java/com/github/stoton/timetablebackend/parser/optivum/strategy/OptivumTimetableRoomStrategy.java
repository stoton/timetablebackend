package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.nodes.Document;

import static com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategyUtils.*;

public class OptivumTimetableRoomStrategy implements OptivumTimetableStrategy {

    private TimetableType timetableType;
    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    private Long schoolId;

    public OptivumTimetableRoomStrategy(TimetableType timetableType,
                                        Long schoolId, OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) {
        this.timetableType = timetableType;
        this.schoolId = schoolId;
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
    }

    @Override
    public Timetable parseAllLessonsFromHtml(Document document) throws UnknownTimetableTypeException {
        Timetable timetable = new Timetable();

        timetable.setType(timetableType.toString());

        String room = document.select(".tytulnapis")
                .first().text();

        return getTimetable(document, timetable, room, timetableType, schoolId, optivumTimetableIndexItemRepository);
    }

    @Override
    public String fixHtml(String html) throws UnknownTimetableTypeException {
        return OptivumTimetableStrategyUtils.fixHtml(html, timetableType);
    }
}
