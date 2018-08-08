package com.github.stoton.timetablebackend.controller;

import com.github.stoton.timetablebackend.domain.optivium.OptiviumTimetableIndexItem;
import com.github.stoton.timetablebackend.repository.TimetableIndexItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TimetableController {

    @Autowired
    private TimetableIndexItemRepository timetableIndexItemRepository;

    @GetMapping("/databaseTest")
    public void test() {
        OptiviumTimetableIndexItem optiviumTimetableIndexItem = new OptiviumTimetableIndexItem();
        optiviumTimetableIndexItem.setFullName("Test");
        optiviumTimetableIndexItem.setLink("test");
        optiviumTimetableIndexItem.setShortName("Teeest");


        timetableIndexItemRepository.save(optiviumTimetableIndexItem);
    }

    @GetMapping(value = "/schools/0/timetables/0", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getExampleTimetable() {
        return Mono.just("{\n" +
                "    \"timestamp\": 1529163372000,\n" +
                "    \"expires\": 1529163689000,\n" +
                "    \"id\": \"0\",\n" +
                "    \"active\": true,\n" +
                "    \"title\": \"3 Tża\",\n" +
                "    \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "    \"type\": \"student\",\n" +
                "    \"schedule\": {\n" +
                "        \"mon\": [\n" +
                "            {\n" +
                "                \"num\": 0,\n" +
                "                \"start\": \"8:00\",\n" +
                "                \"end\": \"8:45\",\n" +
                "                \"groups\": [\n" +
                "                    {\n" +
                "                        \"class\": \"3 Tża 1/2\",\n" +
                "                        \"teacher\": \"M. Matusik\",\n" +
                "                        \"subject\": \"Informatyka\",\n" +
                "                        \"room\": \"218\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"class\": \"3 Tża 2/2\",\n" +
                "                        \"teacher\": \"R. Feret\",\n" +
                "                        \"subject\": \"Noszenie krzeseł\",\n" +
                "                        \"room\": \"204\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"tue\": [],\n" +
                "        \"wed\": [],\n" +
                "        \"thu\": [],\n" +
                "        \"fri\": [],\n" +
                "        \"sat\": [],\n" +
                "        \"sun\": []\n" +
                "    }\n" +
                "}");
    }

    @GetMapping(value = "schools/0/timetables", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getAllTimetables() {
        return Mono.just("{\n" +
                "    \"timestamp\": 1529163372000,\n" +
                "    \"expires\": 1529163689000,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"timestamp\": 1529163372000,\n" +
                "            \"expires\": 1529163689000,\n" +
                "            \"id\": \"0\",\n" +
                "            \"active\": true,\n" +
                "            \"title\": \"3 Tża\",\n" +
                "            \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "            \"type\": \"student\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"timestamp\": 1529163372000,\n" +
                "            \"expires\": 1529163689000,\n" +
                "            \"id\": \"1\",\n" +
                "            \"active\": true,\n" +
                "            \"title\": \"R. feret\",\n" +
                "            \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "            \"type\": \"teacher\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"timestamp\": 1529163372000,\n" +
                "            \"expires\": 1529163689000,\n" +
                "            \"id\": \"2\",\n" +
                "            \"active\": true,\n" +
                "            \"title\": \"204\",\n" +
                "            \"href\": \"http://szkola.zsat.linuxpl.eu/planlekcji/plany/o4.html\",\n" +
                "            \"type\": \"room\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    @GetMapping(value = "schools/{schoolId}", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getSchool() {
        return Mono.just("{\n" +
                "    \"timestamp\": 1529163372000,\n" +
                "    \"expires\": 1529163689000,\n" +
                "    \"id\": \"0\",\n" +
                "    \"active\": true,\n" +
                "    \"title\": \"ZSA-T Ropczyce\",\n" +
                "    \"href\": \"http://www.zsat-ropczyce.pl\"\n" +
                "}"
        );
    }

    @GetMapping(value = "/schools", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getAllSchools() {
        return Mono.just("{\n" +
                "    \"timestamp\": 1529163372000,\n" +
                "    \"expires\": 1529163689000,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"timestamp\": 1529163372000,\n" +
                "            \"expires\": 1529163689000,\n" +
                "            \"id\": \"0\",\n" +
                "            \"active\": true,\n" +
                "            \"title\": \"ZSA-T Ropczyce\",\n" +
                "            \"href\": \"http://www.zsat-ropczyce.pl\"\n" +
                "        }\n" +
                "    ]\n" +
                "}"
        );
    }
}
