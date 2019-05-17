package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.TimetableHeaderJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableHeaderJsonRepository extends JpaRepository<TimetableHeaderJson, Long> {
    TimetableHeaderJson findBySchoolIdAndId(Long schoolId, Long id);

    List<TimetableHeaderJson> getAllBySchoolId(Long schoolId);
}
