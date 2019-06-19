package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;

@Data
public class UnsuccessfulParsedTimetable {

    private Long id;
    private String name;
    private String type;
    private Long schoolId;
}
