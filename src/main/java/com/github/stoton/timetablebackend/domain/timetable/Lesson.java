package com.github.stoton.timetablebackend.domain.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private int num;
    private String start;
    private String end;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    private List<LessonGroup> lessonGroups;

    public Lesson() {}

    public Lesson(int num, String start, String end, List<LessonGroup> lessonGroups) {
        this.num = num;
        this.start = start;
        this.end = end;
        this.lessonGroups = lessonGroups;
    }
}
