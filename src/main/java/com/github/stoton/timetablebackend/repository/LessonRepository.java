package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
