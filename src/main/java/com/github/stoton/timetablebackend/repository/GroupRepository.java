package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.timetable.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
