package com.github.stoton.timetablebackend.domain.timetableindexitem.optivum;

import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class OptivumTimetableIndexItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortName;
    private String fullName;
    private String link;
    private TimetableType timetableType;

    @OneToOne
    @JoinColumn(name="school_id")
    private School school;
}
