package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.TimetableJson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableJsonRepository extends JpaRepository<TimetableJson, Long> {

    TimetableJson findBySchoolIdAndId(Long schoolId, Long id);
}
