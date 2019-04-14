package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableRoomStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStudentStrategy;
import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableTeacherStrategy;

public class OptivumHtmlRepairer {

    private OptivumTimetableStrategy optivumTimetableStrategy;

    private TimetableType timetableType;

    public OptivumHtmlRepairer(OptivumTimetableStrategy optivumTimetableStrategy) {
        this.optivumTimetableStrategy = optivumTimetableStrategy;

        if(optivumTimetableStrategy instanceof OptivumTimetableStudentStrategy)
            timetableType = TimetableType.STUDENT;
        if(optivumTimetableStrategy instanceof OptivumTimetableRoomStrategy)
            timetableType = TimetableType.ROOM;
        if(optivumTimetableStrategy instanceof OptivumTimetableTeacherStrategy)
            timetableType = TimetableType.TEACHER;
    }

    public String fixHtml(String html) throws UnknownTimetableTypeException {
        return optivumTimetableStrategy.fixHtml(html);
    }

}