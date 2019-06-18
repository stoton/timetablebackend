package com.github.stoton.timetablebackend.service;

import com.github.stoton.timetablebackend.domain.school.Address;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.repository.AddressRepository;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {

    private static final String SERVER_HOST_NAME = "http://apps.shemhazai.com:7081/schools/schoolIcon/";

    private final SchoolRepository schoolRepository;
    private final AddressRepository addressRepository;
    private final Logger logger;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, AddressRepository addressRepository, Logger logger) {
        this.schoolRepository = schoolRepository;
        this.addressRepository = addressRepository;
        this.logger = logger;
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public Optional<School> getSchoolBySchoolId(Long schoolId) {
        return schoolRepository.findById(schoolId);
    }

    @Override
    public boolean insertSchool(School school) {
        School existedSchool = schoolRepository.findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country(
                school.getName(),
                school.getHref(),
                school.getTimetableHref(),
                school.getAddress().getCity(),
                school.getAddress().getCountry()
        );

        if (existedSchool != null) {
            return false;
        }

        school.setIcon(SERVER_HOST_NAME + school.getId());
        school.setTimestamp(LocalDateTime.now());

        Address address =
                addressRepository.findByCityAndCountry(school.getAddress().getCity(), school.getAddress().getCountry());

        if (address != null) {
            school.setAddress(address);
        }

        addressRepository.save(school.getAddress());
        schoolRepository.save(school);

        return true;
    }

    @Override
    public boolean editSchool(Long schoolId, School school) {
        try {
            School schoolToUpdate = schoolRepository.getOne(schoolId);
            schoolToUpdate.setTimestamp(LocalDateTime.now());
            schoolToUpdate.setName(school.getName());
            schoolToUpdate.setHref(school.getHref());
            schoolToUpdate.setAddress(school.getAddress());
            schoolToUpdate.setTimetableHref(school.getTimetableHref());
            addressRepository.save(school.getAddress());
            schoolRepository.save(schoolToUpdate);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
