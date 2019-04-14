package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonDay {
    private List<Lesson> lessons;
}
