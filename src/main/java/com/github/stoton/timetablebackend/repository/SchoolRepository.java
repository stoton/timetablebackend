package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.school.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    School findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country
            (String name, String href, String timetableHref, String addressCity, String addressCountry);
}
