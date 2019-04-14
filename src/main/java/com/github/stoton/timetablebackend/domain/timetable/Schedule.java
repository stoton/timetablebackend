package com.github.stoton.timetablebackend.domain.timetable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Schedule {
    private LessonDay mon;
    private LessonDay tue;
    private LessonDay wed;
    private LessonDay thu;
    private LessonDay fri;
    private LessonDay sat;
    private LessonDay sun;
}
