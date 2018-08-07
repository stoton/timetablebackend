package com.github.stoton.timetablebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class Timetable {

    private Long id;
    private String title;
    private String href;
    private Boolean active;
    private Instant timestamp;
    private Instant expires;
    private TimetableType type;
    private List<Schedule> schedule;

}

