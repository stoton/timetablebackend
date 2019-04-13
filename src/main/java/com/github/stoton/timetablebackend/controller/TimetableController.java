package com.github.stoton.timetablebackend.controller;

import com.github.stoton.timetablebackend.domain.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.domain.school.School;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.parser.optivum.OptivumTimetableIndexItemsParser;
import com.github.stoton.timetablebackend.repository.SchoolRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TimetableController {

//    @Autowired
//    private TimetableIndexItemRepository timetableIndexItemRepository;


    OptivumTimetableIndexItemsParser optivumTimetableIndexItemsParser = new OptivumTimetableIndexItemsParser();

    @GetMapping(value = "test")
    public Flux<List<OptivumTimetableIndexItem>> test() throws IOException, UnknownTimetableTypeException {
        Document document = Jsoup.connect("http://szkola.zsat.linuxpl.eu/planlekcji/lista.html").get();
        
        return Flux.just(optivumTimetableIndexItemsParser.parseIndexItems(document));
    }



    @GetMapping(value = "/schools/{schoolId}/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getAllTimetables() {
        return Mono.just("[\n" +
                "  {\n" +
                "    \"id\": \"0\",\n" +
                "    \"name\": \"3 Tża\",\n" +
                "    \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "    \"type\": \"student\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"R. feret\",\n" +
                "    \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "    \"type\": \"teacher\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2\",\n" +
                "    \"name\": \"204\",\n" +
                "    \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "    \"type\": \"room\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"3\",\n" +
                "    \"name\": \"Xyz\",\n" +
                "    \"href\": \"http://xyz.pl\",\n" +
                "    \"type\": \"unknown\"\n" +
                "  }\n" +
                "]");
    }

    @GetMapping(value = "/schools/{schoolId}/timetables/{timetableId}", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getExampleTimetable() {
        return Mono.just("{\n" +
                "  \"id\": \"0\",\n" +
                "  \"name\": \"3 Tża\",\n" +
                "  \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "  \"type\": \"student\",\n" +
                "  \"schedule\": {\n" +
                "    \"mon\": [\n" +
                "      {\n" +
                "        \"num\": 0,\n" +
                "        \"start\": \"8:00\",\n" +
                "        \"end\": \"8:45\",\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"class\": \"3 Tża 1/2\",\n" +
                "            \"teacher\": \"M. Matusik\",\n" +
                "            \"subject\": \"Informatyka\",\n" +
                "            \"room\": \"218\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"class\": \"3 Tża 2/2\",\n" +
                "            \"teacher\": \"R. Feret\",\n" +
                "            \"subject\": \"Noszenie krzeseł\",\n" +
                "            \"room\": \"204\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"tue\": [],\n" +
                "    \"wed\": [],\n" +
                "    \"thu\": [],\n" +
                "    \"fri\": [],\n" +
                "    \"sat\": [],\n" +
                "    \"sun\": []\n" +
                "  }\n" +
                "}");
    }
}
