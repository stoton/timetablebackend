package com.github.stoton.timetablebackend.repository;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    School findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country
            (String name, String href, String timetableHref, String addressCity, String addressCountry);

    List<School> findAllByTimetableProducerType(TimetableProducerType timetableProducerType);
}
