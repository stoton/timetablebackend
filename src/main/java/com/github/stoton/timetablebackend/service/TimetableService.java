package com.github.stoton.timetablebackend.service;

import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.domain.timetable.UnsuccessfulParsedTimetable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface TimetableService {

    List<Timetable> getAllTimetableHeadersBySchoolId(Long schoolId);

    Timetable getTimetableByTimetableIdAndSchoolId(Long timetableId, Long schoolId);

    @Async
    Future<Boolean> parseAllTimetablesBySchoolId(Long schoolId);

    void updateSchoolPopularity(Long SchoolId);

    List<UnsuccessfulParsedTimetable> getListOfUnsuccessfulParsesOfLastParsing();

}
