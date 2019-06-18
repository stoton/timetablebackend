package com.github.stoton.timetablebackend.domain.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


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
    @JsonProperty("groups")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "group_id")
    private List<Group> groups;


    public Lesson() {
    }

    public Lesson(int num, String start, String end, List<Group> groups) {
        this.num = num;
        this.start = start;
        this.end = end;
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {

        System.out.println("2");


        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return num == lesson.num &&
                Objects.equals(start, lesson.start) &&
                Objects.equals(end, lesson.end) &&
                Objects.equals(groups, lesson.groups);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + num;
        hash = 31 * hash + (start == null ? 0 : start.hashCode());
        hash = 31 * hash + (end == null ? 0 : end.hashCode());
        for (Group group : groups) {
            hash = 31 * hash + (group.getRoom() == null ? 0 : group.getRoom().hashCode());
            hash = 31 * hash + (group.getStudent() == null ? 0 : group.getStudent().hashCode());
            hash = 31 * hash + (group.getSubject() == null ? 0 : group.getSubject().hashCode());
            hash = 31 * hash + (group.getTeacher() == null ? 0 : group.getTeacher().hashCode());
        }

        return hash;
    }
}
