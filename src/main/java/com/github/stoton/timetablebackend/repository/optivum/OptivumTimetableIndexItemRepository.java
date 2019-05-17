package com.github.stoton.timetablebackend.repository.optivum;


import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.repository.TimetableIndexItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptivumTimetableIndexItemRepository extends TimetableIndexItemRepository<OptivumTimetableIndexItem, Long> {

    OptivumTimetableIndexItem findFirstByShortNameAndSchool_Id(String shortName, Long schoolId);

    OptivumTimetableIndexItem findFirstByFullName(String fullName);

}
