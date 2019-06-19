package com.github.stoton.timetablebackend.service.tests;


import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.Schedule;
import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import com.github.stoton.timetablebackend.repository.*;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import com.github.stoton.timetablebackend.service.TimetableServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.Silent.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TimetableServiceImplTests {

    @Mock
    private OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private TimetableRepository timetableRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private Logger logger;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    @Test
    public void getAllTimetableHeadersBySchoolIdTest() {
        Timetable timetable = new Timetable();
        Schedule schedule = new Schedule();

        timetable.setSchedule(schedule);

        List<Timetable> expected = new ArrayList<>();

        Mockito.when(timetableRepository.findTimetableHeadersBySchoolId(1L)).thenReturn(expected);

        List<Timetable> actual = timetableService.getAllTimetableHeadersBySchoolId(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void getTimetableByTimetableIdAndSchoolIdWhenTimetableExistTest() {
        Timetable expected = new Timetable();
        Schedule schedule = new Schedule();

        expected.setSchedule(schedule);

        Mockito.when(timetableRepository.findByIdAndSchoolId(1L, 1L)).thenReturn(expected);

        Timetable actual = timetableService.getTimetableByTimetableIdAndSchoolId(1L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    public void getTimetableByTimetableIdAndSchoolIdWhenTimetableNotExistTest() {
        Timetable expected = null;

        Mockito.when(timetableRepository.findByIdAndSchoolId(1L, 1L)).thenReturn(null);

        Timetable actual = timetableService.getTimetableByTimetableIdAndSchoolId(1L, 1L);

        assertEquals(expected, actual);
    }

    @Test
    public void parseAllTimetablesBySchoolIdWhenSchoolNotExistTest() throws ExecutionException, InterruptedException {
        Optional<School> schoolOptional = Optional.ofNullable(null);

        Mockito.when(schoolRepository.findById(1L)).thenReturn(schoolOptional);

        Future<Boolean> actual = timetableService.parseAllTimetablesBySchoolId(1L);

        assertFalse(actual.get());
    }

}
