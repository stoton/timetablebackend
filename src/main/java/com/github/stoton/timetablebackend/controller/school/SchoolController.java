package com.github.stoton.timetablebackend.controller.school;

import com.github.stoton.timetablebackend.domain.school.Address;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.properties.TimetableProducerType;
import com.github.stoton.timetablebackend.repository.AddressRepository;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import com.github.stoton.timetablebackend.service.TimetableProducerRegonizer;
import com.github.stoton.timetablebackend.service.UnknownTimetableProducerType;
import org.apache.commons.io.IOUtils;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SchoolController {

    private SchoolRepository schoolRepository;
    private AddressRepository addressRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository, AddressRepository addressRepository) {
        this.schoolRepository = schoolRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping(value = "/schools", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<School>>> getAllSchools() {

        List<School> schools = schoolRepository.findAll();

        for (School school : schools) {
            school.setIcon("http://apps.shemhazai.com:7081/schools/schoolIcon/" + school.getId());
            schoolRepository.save(school);
        }

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(schoolRepository.findAll()));
    }

    @GetMapping(value = "/schools/{schoolId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<School>> getSchool(@PathVariable("schoolId") Long schoolId) {
        final Optional<School> schoolOptional = schoolRepository.findById(schoolId);

        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            school.setIcon("http://apps.shemhazai.com:7081/schools/schoolIcon/" + school.getId());
            schoolRepository.save(school);
        }

        return schoolOptional.map(school1 -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(school1)))
                .orElseGet(() -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping(value = "/schools/schoolIcon/{schoolId}")
    public @ResponseBody
    Mono<ResponseEntity<byte[]>> getFile(@PathVariable Long schoolId) {
        Optional<School> schoolOptional = schoolRepository.findById(schoolId);

        return schoolOptional
                .map(school -> Mono.just(ResponseEntity.status(HttpStatus.OK)
                        .body(school.getIconContent())))
                .orElseGet(() -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @PostMapping(value = "/schools")
    public Mono<ResponseEntity<Void>> insertSchool(@RequestBody School school) throws IOException, UnknownTimetableProducerType {

        School existedSchool = schoolRepository.findByNameAndHrefAndTimetableHrefAndAddress_CityAndAddress_Country(
                school.getName(),
                school.getHref(),
                school.getTimetableHref(),
                school.getAddress().getCity(),
                school.getAddress().getCountry()
        );

        if (existedSchool != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
        }

        TimetableProducerType timetableProducerType = TimetableProducerRegonizer
                .recognizeTimetable(school.getTimetableHref());

        school.setTimetableProducerType(timetableProducerType);
        school.setTimestamp(LocalDateTime.now());

        Address address =
                addressRepository.findByCityAndCountry(school.getAddress().getCity(), school.getAddress().getCountry());

        if (address != null) {
            school.setAddress(address);
        }

        addressRepository.save(school.getAddress());
        schoolRepository.save(school);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
    }

    @PutMapping(value = "/schools/{schoolId}")
    public void editSchool(@PathVariable("schoolId") Long schoolId, @RequestBody School school) {
        School schoolToUpdate = schoolRepository.getOne(schoolId);

        schoolToUpdate.setTimestamp(LocalDateTime.now());
        schoolToUpdate.setName(school.getName());
        schoolToUpdate.setHref(school.getHref());
        schoolToUpdate.setAddress(school.getAddress());
        schoolToUpdate.setTimetableHref(school.getTimetableHref());
        schoolToUpdate.setTimetableProducerType(school.getTimetableProducerType());

        addressRepository.save(school.getAddress());
        schoolRepository.save(schoolToUpdate);
    }
}
