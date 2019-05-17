package com.github.stoton.timetablebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface TimetableIndexItemRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findAllBySchoolId(Long schooId);

}

