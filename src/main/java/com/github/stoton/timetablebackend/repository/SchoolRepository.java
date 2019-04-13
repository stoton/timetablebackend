package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
