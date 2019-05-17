package com.github.stoton.timetablebackend.controller.school;

import com.github.stoton.timetablebackend.domain.school.Address;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.repository.AddressRepository;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(schoolRepository.findAll()));
    }

    @GetMapping(value = "schools/{schoolId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<School>> getSchool(@PathVariable("schoolId") Long schoolId) {
        final Optional<School> school = schoolRepository.findById(schoolId);

        return school.map(school1 -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(school1)))
                .orElseGet(() -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @PostMapping(value = "/schools")
    public void insertSchool(@RequestBody School school) {
        school.setTimestamp(LocalDateTime.now());

        Address address =
                addressRepository.findByCityAndCountry(school.getAddress().getCity(), school.getAddress().getCountry());

        if (address != null) {
            school.setAddress(address);
        }

        addressRepository.save(school.getAddress());
        schoolRepository.save(school);
    }

    @PutMapping(value = "/schools/{schoolId}")
    public void editSchool(@PathVariable("schoolId") Long schoolId, @RequestBody School school) {
        School schoolToUpdate = schoolRepository.getOne(schoolId);

        schoolToUpdate.setTimestamp(LocalDateTime.now());
        schoolToUpdate.setName(school.getName());
        schoolToUpdate.setHref(school.getHref());
        schoolToUpdate.setAddress(school.getAddress());

        schoolRepository.save(schoolToUpdate);
    }
}
