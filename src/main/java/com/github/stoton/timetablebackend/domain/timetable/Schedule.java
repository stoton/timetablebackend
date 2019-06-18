package com.github.stoton.timetablebackend.domain.timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "mon_id")
    private List<Lesson> mon = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "tue_id")
    private List<Lesson> tue = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "wed_id")
    private List<Lesson> wed = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "thu_id")
    private List<Lesson> thu = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "fri_id")
    private List<Lesson> fri = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "sat_id")
    private List<Lesson> sat = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "sun_id")
    private List<Lesson> sun = new ArrayList<>();

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;

        if (mon.size() != schedule.mon.size()) {
            return false;
        }

        for (int i = 0; i < mon.size(); i++) {
            if (mon.get(i).getNum() != schedule.mon.get(i).getNum()) {
                return false;
            }

            if (!mon.get(i).getStart().equals(schedule.getMon().get(i).getStart())) {
                return false;
            }

            if (!mon.get(i).getEnd().equals(schedule.getMon().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < mon.get(i).getGroups().size(); j++) {
                if (!mon.get(i).getGroups().get(j).getTeacher().equals(schedule.getMon().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!mon.get(i).getGroups().get(j).getSubject().equals(schedule.getMon().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!mon.get(i).getGroups().get(j).getStudent().equals(schedule.getMon().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!mon.get(i).getGroups().get(j).getRoom().equals(schedule.getMon().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (tue.size() != schedule.tue.size()) {
            return false;
        }

        for (int i = 0; i < tue.size(); i++) {
            if (tue.get(i).getNum() != schedule.tue.get(i).getNum()) {
                return false;
            }

            if (!tue.get(i).getStart().equals(schedule.getTue().get(i).getStart())) {
                return false;
            }

            if (!tue.get(i).getEnd().equals(schedule.getTue().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < tue.get(i).getGroups().size(); j++) {
                if (!tue.get(i).getGroups().get(j).getTeacher().equals(schedule.getTue().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!tue.get(i).getGroups().get(j).getSubject().equals(schedule.getTue().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!tue.get(i).getGroups().get(j).getStudent().equals(schedule.getTue().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!tue.get(i).getGroups().get(j).getRoom().equals(schedule.getTue().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (wed.size() != schedule.wed.size()) {
            return false;
        }

        for (int i = 0; i < wed.size(); i++) {
            if (wed.get(i).getNum() != schedule.getWed().get(i).getNum()) {
                return false;
            }

            if (!wed.get(i).getStart().equals(schedule.getWed().get(i).getStart())) {
                return false;
            }

            if (!wed.get(i).getEnd().equals(schedule.getWed().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < wed.get(i).getGroups().size(); j++) {
                if (!wed.get(i).getGroups().get(j).getTeacher().equals(schedule.getWed().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!wed.get(i).getGroups().get(j).getSubject().equals(schedule.getWed().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!wed.get(i).getGroups().get(j).getStudent().equals(schedule.getWed().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!wed.get(i).getGroups().get(j).getRoom().equals(schedule.getWed().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (thu.size() != schedule.thu.size()) {
            return false;
        }

        for (int i = 0; i < thu.size(); i++) {
            if (thu.get(i).getNum() != schedule.thu.get(i).getNum()) {
                return false;
            }

            if (!thu.get(i).getStart().equals(schedule.getThu().get(i).getStart())) {
                return false;
            }

            if (!thu.get(i).getEnd().equals(schedule.getThu().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < thu.get(i).getGroups().size(); j++) {
                if (!thu.get(i).getGroups().get(j).getTeacher().equals(schedule.getThu().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!thu.get(i).getGroups().get(j).getSubject().equals(schedule.getThu().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!thu.get(i).getGroups().get(j).getStudent().equals(schedule.getThu().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!thu.get(i).getGroups().get(j).getRoom().equals(schedule.getThu().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (fri.size() != schedule.fri.size()) {
            return false;
        }

        for (int i = 0; i < fri.size(); i++) {
            if (fri.get(i).getNum() != schedule.fri.get(i).getNum()) {
                return false;
            }

            if (!fri.get(i).getStart().equals(schedule.getFri().get(i).getStart())) {
                return false;
            }

            if (!fri.get(i).getEnd().equals(schedule.getFri().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < fri.get(i).getGroups().size(); j++) {
                if (!fri.get(i).getGroups().get(j).getTeacher().equals(schedule.getFri().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!fri.get(i).getGroups().get(j).getSubject().equals(schedule.getFri().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!fri.get(i).getGroups().get(j).getStudent().equals(schedule.getFri().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!fri.get(i).getGroups().get(j).getRoom().equals(schedule.getFri().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (sat.size() != schedule.sat.size()) {
            return false;
        }

        for (int i = 0; i < sat.size(); i++) {
            if (sat.get(i).getNum() != schedule.sat.get(i).getNum()) {
                return false;
            }

            if (!sat.get(i).getStart().equals(schedule.getSat().get(i).getStart())) {
                return false;
            }

            if (!sat.get(i).getEnd().equals(schedule.getSat().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < sat.get(i).getGroups().size(); j++) {
                if (!sat.get(i).getGroups().get(j).getTeacher().equals(schedule.getSat().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!sat.get(i).getGroups().get(j).getSubject().equals(schedule.getSat().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!sat.get(i).getGroups().get(j).getStudent().equals(schedule.getSat().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!sat.get(i).getGroups().get(j).getRoom().equals(schedule.getSat().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        if (sun.size() != schedule.sun.size()) {
            return false;
        }

        for (int i = 0; i < sun.size(); i++) {
            if (sun.get(i).getNum() != schedule.sun.get(i).getNum()) {
                return false;
            }

            if (!sun.get(i).getStart().equals(schedule.getSun().get(i).getStart())) {
                return false;
            }

            if (!sun.get(i).getEnd().equals(schedule.getSun().get(i).getEnd())) {
                return false;
            }

            for(int j = 0; j < sun.get(i).getGroups().size(); j++) {
                if (!sun.get(i).getGroups().get(j).getTeacher().equals(schedule.getSun().get(i).getGroups().get(j).getTeacher())) {
                    return false;
                }

                if (!sun.get(i).getGroups().get(j).getSubject().equals(schedule.getSun().get(i).getGroups().get(j).getSubject())) {
                    return false;
                }

                if (!sun.get(i).getGroups().get(j).getStudent().equals(schedule.getSun().get(i).getGroups().get(j).getStudent())) {
                    return false;
                }

                if (!sun.get(i).getGroups().get(j).getRoom().equals(schedule.getSun().get(i).getGroups().get(j).getRoom())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {

        int hash = 1;

        for (Lesson lesson : mon) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : tue) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : wed) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : thu) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : fri) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : sat) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        for (Lesson lesson : sun) {
            hash = 31 * hash + lesson.getNum();
            hash = 31 * hash + (lesson.getStart() == null ? 0 : lesson.getStart().hashCode());
            hash = 31 * hash + (lesson.getEnd() == null ? 0 : lesson.getEnd().hashCode());
            hash = 31 * hash + lesson.getGroups().hashCode();
        }

        return hash;
    }
}
