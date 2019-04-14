package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Group {

    private String student;
    private String teacher;
    private String subject;
    private String room;
}
