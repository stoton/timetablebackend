package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository  extends JpaRepository<Timetable, Long> {

    List<Timetable> findAllBySchoolId(Long schoolId);
    Timetable findBySchoolIdAndId(Long schoolId, Long id);
}
