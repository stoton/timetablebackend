package com.github.stoton.timetablebackend.domain.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "`group`")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String student;
    private String teacher;
    private String subject;
    private String room;

    public Group() {}

    public Group(String student, String teacher, String subject, String room) {
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group that = (Group) o;

        return Objects.equals(student, that.student) &&
                Objects.equals(teacher, that.teacher) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, teacher, subject, room);
    }
}
