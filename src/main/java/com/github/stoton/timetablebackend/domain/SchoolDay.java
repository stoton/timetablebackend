package com.github.stoton.timetablebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SchoolDay {
    private List<Lesson> lessons;
}
