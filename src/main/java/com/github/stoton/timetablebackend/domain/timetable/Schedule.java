package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Schedule {
    private List<Lesson> mon = new ArrayList<>();
    private List<Lesson> tue = new ArrayList<>();
    private List<Lesson> wed = new ArrayList<>();
    private List<Lesson> thu = new ArrayList<>();
    private List<Lesson> fri = new ArrayList<>();
    private List<Lesson> sat = new ArrayList<>();
    private List<Lesson> sun = new ArrayList<>();
}
