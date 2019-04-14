package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Timetable {

    private Long id;
    private String name;
    private String href;
    private TimetableType type;
    private Schedule schedule;
}
