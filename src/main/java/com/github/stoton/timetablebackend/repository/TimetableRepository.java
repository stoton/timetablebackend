package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    List<Timetable> findAllBySchoolId(Long schoolId);

    @Query("select new com.github.stoton.timetablebackend.domain.timetable.Timetable(t.id, t.schoolId, t.name, t.type, t.timestamp) from Timetable t where t.schoolId = ?1")
    List<Timetable> findTimetableHeadersBySchoolId(Long schoolId);

    Timetable findBySchoolIdAndId(Long schoolId, Long id);

    Timetable findBySchoolIdAndNameAndType(Long schoolId, String name, String type);
}
