package com.github.stoton.timetablebackend.domain.timetableindexitem.optivum;

import com.github.stoton.timetablebackend.domain.timetable.TimetableType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class OptivumTimetableIndexItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shortName;
    private String fullName;
    private String link;
    private TimetableType timetableType;
}
