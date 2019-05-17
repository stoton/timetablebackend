package com.github.stoton.timetablebackend.controller;


import ch.qos.logback.core.encoder.EchoEncoder;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableIndexItemsParser;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class OptivumTimetableIndexItemController {

    private final SchoolRepository schoolRepository;

    private OptivumTimetableIndexItemsParser optivumTimetableIndexItemsParser = new OptivumTimetableIndexItemsParser();

    private final OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository;

    @Autowired
    public OptivumTimetableIndexItemController(OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, SchoolRepository schoolRepository) {
        this.optivumTimetableIndexItemRepository = optivumTimetableIndexItemRepository;
        this.schoolRepository = schoolRepository;
    }

    @GetMapping("/timetableindexitem")
    public void insertToDatabase() throws IOException, UnknownTimetableTypeException {

        Document document = Jsoup.connect("http://szkola.zsat.linuxpl.eu/planlekcji/lista.html").get();

        Optional<School> schoolOptional = schoolRepository.findById(1L);

        School school;

        if (schoolOptional.isPresent()) {
            school = schoolOptional.get();
        } else {
            throw new IllegalArgumentException("Unknown school");
        }

        List<OptivumTimetableIndexItem> optivumTimetableIndexItems = optivumTimetableIndexItemsParser.parseIndexItems(document);

        for (OptivumTimetableIndexItem optivumTimetableIndexItem : optivumTimetableIndexItems) {
            optivumTimetableIndexItem.setSchool(school);
            optivumTimetableIndexItemRepository.save(optivumTimetableIndexItem);
        }
    }
}
