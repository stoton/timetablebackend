package com.github.stoton.timetablebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Group {

    private String clazz;
    private String teacher;
    private String subject;
    private String room;
}
