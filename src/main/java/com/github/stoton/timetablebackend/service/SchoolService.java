package com.github.stoton.timetablebackend.service;

import com.github.stoton.timetablebackend.domain.school.School;

import java.util.List;
import java.util.Optional;

public interface SchoolService {

    List<School> getAllSchools();

    Optional<School> getSchoolBySchoolId(Long schoolId);

    boolean insertSchool(School school);

    boolean editSchool(Long schoolId, School school);
}
