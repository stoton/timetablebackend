package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.Application;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.TimetableParser;
import com.github.stoton.timetablebackend.parser.TimetableTypeRecognizer;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableRoomStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStudentStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableTeacherStrategy;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class OptivumTimetableParser implements TimetableParser {

    @Override
    public Timetable parseTimetable(String url, TimetableTypeRecognizer timetableTypeRecognizer, Long schoolId) throws UnknownTimetableTypeException, IOException {
        TimetableType timetableType = timetableTypeRecognizer.recognizeTimetableTypeByUrl(url);

        OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository
                = Application.getAppContext().getBean(OptivumTimetableIndexItemRepository.class);

        OptivumTimetableStrategy optivumTimetableStrategy;

        switch (timetableType) {
            case STUDENT:
                optivumTimetableStrategy = new OptivumTimetableStudentStrategy(timetableType, schoolId, optivumTimetableIndexItemRepository);
                break;
            case TEACHER:
                optivumTimetableStrategy = new OptivumTimetableTeacherStrategy(timetableType, schoolId, optivumTimetableIndexItemRepository);
                break;
            case ROOM:
                optivumTimetableStrategy = new OptivumTimetableRoomStrategy(timetableType, schoolId, optivumTimetableIndexItemRepository);
                break;
            default:
                throw new UnknownTimetableTypeException("Unknown timetable type");
        }


        System.out.println(url);
        Document document = Jsoup.connect(url).get();

        return optivumTimetableStrategy.parseAllLessonsFromHtml(document);
    }
}
