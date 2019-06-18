package com.github.stoton.timetablebackend.controller.school;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping(value = "/schools", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<School>>> getAllSchools() {
        List<School> allSchools = schoolService.getAllSchools();
        return Mono.just(status(HttpStatus.OK).body(allSchools));
    }

    @GetMapping(value = "/schools/{schoolId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<School>> getSchoolById(@PathVariable("schoolId") Long schoolId) {
        Optional<School> schoolOptional = schoolService.getSchoolBySchoolId(schoolId);

        return schoolOptional.map(
                school -> Mono.just(status(HttpStatus.OK).body(school)))
                .orElseGet(() -> Mono.just(status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping(value = "/schools/schoolIcon/{schoolId}")
    public @ResponseBody
    Mono<ResponseEntity<byte[]>> getSchoolIcon(@PathVariable Long schoolId) {
        Optional<School> schoolOptional = schoolService.getSchoolBySchoolId(schoolId);

        return schoolOptional
                .map(school -> Mono.just(status(HttpStatus.OK).contentType(MediaType.parseMediaType(school.getMediaType()))
                        .body(school.getIconContent())))
                .orElseGet(() -> Mono.just(status(HttpStatus.NOT_FOUND).build()));
    }

    @PostMapping(value = "/schools")
    public Mono<ResponseEntity<Void>> insertSchool(@RequestBody School school) {
        boolean isAdded = schoolService.insertSchool(school);

        if (isAdded) {
            return Mono.just(status(HttpStatus.OK).build());
        }
        return Mono.just(status(HttpStatus.CONFLICT).build());
    }

    @PutMapping(value = "/schools/{schoolId}")
    public Mono<ResponseEntity<Void>> editSchool(@PathVariable("schoolId") Long schoolId, @RequestBody School school) {
        boolean isEdited = schoolService.editSchool(schoolId, school);

        if (isEdited) {
            return Mono.just(status(HttpStatus.OK).build());

        }
        return Mono.just(status(HttpStatus.CONFLICT).build());
    }
}
