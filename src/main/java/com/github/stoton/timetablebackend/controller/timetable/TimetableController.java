package com.github.stoton.timetablebackend.controller.timetable;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TimetableController {

    private TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Timetable>>> getAllTimetableHeadersBySchoolId(@PathVariable Long schoolId) {
        List<Timetable> allTimetableHeadersBySchoolId = timetableService.getAllTimetableHeadersBySchoolId(schoolId);

        if (allTimetableHeadersBySchoolId.isEmpty()) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        timetableService.updateSchoolPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(allTimetableHeadersBySchoolId));
    }

    @GetMapping(value = "/schools/{schoolId}/timetables/{timetableId}", produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Timetable>> getTimetableByIdAndSchoolId(@PathVariable Long timetableId, @PathVariable Long schoolId) {
        Timetable timetableByTimetableIdAndSchoolId = timetableService.getTimetableByTimetableIdAndSchoolId(timetableId, schoolId);

        if (timetableByTimetableIdAndSchoolId == null) {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        timetableService.updateSchoolPopularity(schoolId);

        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetableByTimetableIdAndSchoolId));
    }

    @PostMapping(value = "/schools/{schoolId}/scheduleParser")
    @Async
    public CompletableFuture<Mono<ResponseEntity<Void>>> parseAllTimetablesBySchoolId(@PathVariable("schoolId") Long schoolId) {
        timetableService.parseAllTimetablesBySchoolId(schoolId);
        return CompletableFuture.completedFuture(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }


    @GetMapping(value = "/schools/lastParsingErrors")
    public Mono<ResponseEntity<List<UnsuccessfulParsedTimetable>>> getUnsuccessfulParsedTimetableFromLastParsing() {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(timetableService.getListOfUnsuccessfulParsesOfLastParsing()));
    }
}
