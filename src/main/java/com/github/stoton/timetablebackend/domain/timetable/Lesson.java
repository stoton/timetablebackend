package com.github.stoton.timetablebackend.domain.timetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    private int num;
    private String start;
    private String end;
    private List<Group> groups;

}
