package com.github.stoton.timetablebackend.controller.school;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SchoolController {

    private SchoolRepository schoolRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @GetMapping(value = "/schools", produces = APPLICATION_JSON_VALUE)
    public Flux<List<School>> getAllSchools() {
        return Flux.just(schoolRepository.findAll());
    }

    @GetMapping(value = "schools/{schoolId}", produces = APPLICATION_JSON_VALUE)
    public Mono<School> getSchool(@PathVariable("schoolId") Long schoolId) {
        final Optional<School> school = schoolRepository.findById(schoolId);

        if(school.isPresent())
            return Mono.just(school.get());
        else
            return Mono.just(new School());
    }

    @PostMapping(value = "/schools")
    public void insertSchool(@RequestBody School school) {
        schoolRepository.save(school);
    }

    @PutMapping(value = "/schools/{schoolId}")
    public void editSchool(@PathVariable("schoolId") Long schoolId, @RequestBody School school) {
        School schoolToUpdate = schoolRepository.getOne(schoolId);

        schoolToUpdate.setName(school.getName());
        schoolToUpdate.setHref(school.getHref());

        schoolRepository.save(schoolToUpdate);
    }
}
