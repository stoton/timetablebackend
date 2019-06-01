package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
