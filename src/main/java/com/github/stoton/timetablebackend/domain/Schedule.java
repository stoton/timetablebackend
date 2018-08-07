package com.github.stoton.timetablebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Schedule {
    private SchoolDay mon;
    private SchoolDay tue;
    private SchoolDay wed;
    private SchoolDay thu;
    private SchoolDay fri;
    private SchoolDay sat;
    private SchoolDay sun;
}
