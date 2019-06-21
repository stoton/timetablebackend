package com.github.stoton.timetablebackend.parser.optivum.strategy;

import com.github.stoton.timetablebackend.domain.timetable.*;
import com.github.stoton.timetablebackend.domain.timetableindexitem.optivum.OptivumTimetableIndexItem;
import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import com.github.stoton.timetablebackend.repository.optivum.OptivumTimetableIndexItemRepository;
import lombok.Builder;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class OptivumTimetableStrategyUtils {

    static final String OUTER_PART_OF_CLASS = ">-[0-9]/[0-9]";
    static final String SUBJECT_CLASS = "class=\"p\"";
    static final String STUDENT_CLASS = "class=\"o\"";
    static final String TEACHER_CLASS = "class=\"n\"";
    static final String ROOM_CLASS = "class=\"s\"";
    static final String A_TAG_END = "</a>";

    static final int NO_OCCURRENCE = -1;
    static final String STUDENT_CLASS_PURE = "\"p\"";
    static final String TEACHER_CLASS_PURE = "\"n\"";
    static final int NUMBER_OF_LESSON = 0;
    static final int START = 1;
    static final int END = 1;
    static final int MONDAY = 2;
    static final int TUESDAY = 3;
    static final int WEDNESDAY = 4;
    static final int THURSDAY = 5;
    static final int FRIDAY = 6;
    static final int SATURDAY = 7;
    static final int SUNDAY = 8;

    static String appendHtmlUntilPartToMerge(String html, int end) {
        return html.substring(0, end);
    }

    static String appendPartOfClassToMerge(String html, int start, int end) {
        return html.substring(start, end);
    }

    static String appendEndOfSpan(String html, int start, int end) {
        return html.substring(start, end);
    }

    static String appendEndOfHtml(String html, int start) {
        return html.substring(start);
    }

    static boolean hasClassPartOutOfTag(String html) {
        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        return matcher.find();
    }

    static Timetable getTimetable(Document document, Timetable timetable, String teacher, TimetableType timetableType,
                                  Long schoolId, OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) throws UnknownTimetableTypeException {
        Element table = document.selectFirst(".tabela");
        Elements trs = table.select("tr:not(:first-child)");

        timetable.setSchedule(new Schedule());

        buildTimetable(timetable, trs, teacher, timetableType, schoolId, optivumTimetableIndexItemRepository);

        deleteEmptyLessons(timetable);

        timetable.setTimestamp(LocalDateTime.now());

        return timetable;
    }

    static boolean isHtmlValid(String html, TimetableType timetableType) throws UnknownTimetableTypeException {

        int studentTags = html
                .split(STUDENT_CLASS, -1).length - 1;

        int subjectTagsCount = html
                .split(SUBJECT_CLASS, -1).length - 1;

        int teacherTagsCount = html
                .split(TEACHER_CLASS, -1).length - 1;

        int roomTagsCount = html
                .split(ROOM_CLASS, -1).length - 1;


        switch (timetableType) {
            case ROOM:
                return studentTags == subjectTagsCount && studentTags == teacherTagsCount &&
                        !hasClassPartOutOfTag(html);
            case STUDENT:
                return subjectTagsCount == teacherTagsCount && subjectTagsCount == roomTagsCount &&
                        !hasClassPartOutOfTag(html);
            case TEACHER:
                return !hasClassPartOutOfTag(html);

            default:
                throw new UnknownTimetableTypeException("TimetableType cannot be recognized.");
        }
    }

    static int getIndexOfElementToMerge(String html) {
        int indexOfElementToMerge = NO_OCCURRENCE;

        Pattern pattern = Pattern.compile(OUTER_PART_OF_CLASS);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            indexOfElementToMerge = html.indexOf(matcher.group()) + 1;
        }

        return indexOfElementToMerge;
    }

    static void buildTimetable(Timetable timetable, Elements trs, String extractedName, TimetableType timetableType,
                               Long schoolId, OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) throws UnknownTimetableTypeException {

        timetable.setName(extractedName);

        for (Element tr : trs) {
            Elements tds = tr.select("td");

            int tdsCount = tds.size();

            int num = Integer.valueOf(tds.get(NUMBER_OF_LESSON).text()) - 1;
            String start = tds.get(START).text().split("-")[0].trim();
            String end = tds.get(END).text().split("-")[1].trim();


            if (tdsCount >= MONDAY) {
                timetable
                        .getSchedule()
                        .getMon()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(MONDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount >= TUESDAY) {
                timetable
                        .getSchedule()
                        .getTue()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(TUESDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount >= WEDNESDAY) {
                timetable
                        .getSchedule()
                        .getWed()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(WEDNESDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount >= THURSDAY) {
                timetable
                        .getSchedule()
                        .getThu()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(THURSDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount >= FRIDAY) {
                timetable
                        .getSchedule()
                        .getFri()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(FRIDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount - 1 == SATURDAY) {
                timetable
                        .getSchedule()
                        .getSat()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(SATURDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }

            if (tdsCount - 1 == SUNDAY) {
                timetable
                        .getSchedule()
                        .getSun()
                        .add(new Lesson(num, start, end, parseGroups(fixHtml(tds.get(SUNDAY).html(), timetableType), extractedName,
                                timetableType, schoolId, optivumTimetableIndexItemRepository)));
            }
        }
    }

    static List<Group> parseGroups(String html, String name, TimetableType timetableType,
                                   Long schoolId, OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository) throws UnknownTimetableTypeException {

        Document currentLesson = Jsoup.parse(html);

        Elements p = currentLesson.select(".p");
        Elements o = currentLesson.select(".o");
        Elements n = currentLesson.select(".n");
        Elements s = currentLesson.select(".s");

        HtmlElements htmlElements = HtmlElements.builder()
                .p(p)
                .o(o)
                .n(n)
                .s(s)
                .build();

        switch (timetableType) {
            case ROOM:
                return parseRoomGroups(htmlElements, name, optivumTimetableIndexItemRepository, schoolId);
            case STUDENT:
                return parseStudentGroups(htmlElements, name, optivumTimetableIndexItemRepository, schoolId);
            case TEACHER:
                return parseTeacherGroups(htmlElements, name);
            default:
                throw new UnknownTimetableTypeException("TimetableType couldn't be recognized :(");
        }
    }

    static String fixHtml(String html, TimetableType timetableType) throws UnknownTimetableTypeException {

        switch (timetableType) {
            case TEACHER:
                return fixTeacherHtml(html, timetableType);

            case ROOM:
                return fixRoomHtml(html, timetableType);

            case STUDENT:
                return fixStudentHtml(html, timetableType);

            default:
                throw new UnknownTimetableTypeException("TimetableType couldn't be recognized :(");
        }
    }

    private static List<Group> parseRoomGroups(HtmlElements htmlElements, String room,
                                               OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, Long schoolId) {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < htmlElements.p.size(); i++) {
            Group group = new Group();

            String student = htmlElements.o.get(i).text();

            student = formatElementWithGroup(student);

            String teacher = optivumTimetableIndexItemRepository
                    .findFirstByShortNameAndSchool_Id(htmlElements.n.get(i).text(), schoolId).getFullName();

            group.setSubject(htmlElements.p.get(i).text());
            group.setRoom(room);
            group.setTeacher(teacher);
            group.setStudent(student);

            groups.add(group);
        }
        return groups;
    }

    private static List<Group> parseTeacherGroups(HtmlElements htmlElements, String teacher) {
        List<Group> groups = new ArrayList<>();

        if (htmlElements.o.size() > htmlElements.p.size()) {
            for (int i = 0; i < htmlElements.o.size(); i++) {
                Group group = new Group();

                String student = htmlElements.o.get(i).text();

                student = formatElementWithGroup(student);

                group.setSubject(htmlElements.p.get(0).text());
                group.setRoom(htmlElements.s.get(0).text());
                group.setTeacher(teacher);
                group.setStudent(student);

                groups.add(group);
            }

            return groups;
        }

        for (int i = 0; i < htmlElements.p.size(); i++) {
            Group group = new Group();

            String student = htmlElements.o.get(i).text();

            student = formatElementWithGroup(student);

            group.setSubject(htmlElements.p.get(i).text());
            group.setRoom(htmlElements.s.get(i).text());
            group.setTeacher(teacher);
            group.setStudent(student);

            groups.add(group);
        }
        return groups;
    }

    private static List<Group> parseStudentGroups(HtmlElements htmlElements, String student,
                                                  OptivumTimetableIndexItemRepository optivumTimetableIndexItemRepository, Long schoolId) {

        List<Group> groups = new ArrayList<>();

        String studentTemp = student;

        for (int i = 0; i < htmlElements.p.size(); i++) {
            Group group = new Group();

            student = studentTemp;

            String subject = htmlElements.p.get(i).text();

            if (subject.contains("-")) {
                int indexOfDash = subject.indexOf("-");
                String numberOfGroup = subject.substring(indexOfDash);
                student += numberOfGroup;
                subject = subject.substring(0, indexOfDash);
            }

            OptivumTimetableIndexItem teacherTimetableIndexItem =
                    optivumTimetableIndexItemRepository.findFirstByShortNameAndSchool_Id(htmlElements.n.get(i).text(), schoolId);
            String teacher = teacherTimetableIndexItem != null ? teacherTimetableIndexItem.getFullName() : htmlElements.n.get(i).text();
            group.setSubject(subject);
            group.setRoom(htmlElements.s.get(i).text());
            group.setTeacher(teacher);
            group.setStudent(student);

            groups.add(group);
        }
        return groups;
    }

    private static String fixTeacherHtml(String html, TimetableType timetableType) throws UnknownTimetableTypeException {
        int indexOfElementToMerge;

        while (!isHtmlValid(html, timetableType)) {
            StringBuilder correctHtml = new StringBuilder();

            indexOfElementToMerge = getIndexOfElementToMerge(html);

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 4))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 4, indexOfElementToMerge))
                    .append(appendEndOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }


        html = html.replaceAll(",", "");
        return html;
    }

    private static String fixRoomHtml(String html, TimetableType timetableType) throws UnknownTimetableTypeException {
        int indexOfElementToMerge;

        while (!isHtmlValid(html, timetableType)) {
            StringBuilder correctHtml = new StringBuilder();
            indexOfElementToMerge = getIndexOfElementToMerge(html);

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 7))
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 7, indexOfElementToMerge - 4))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(A_TAG_END)
                    .append(appendEndOfHtml(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }
        return html;
    }

    private static String fixStudentHtml(String html, TimetableType timetableType) throws UnknownTimetableTypeException {
        int indexOfElementToMerge;

        while (!isHtmlValid(html, timetableType)) {
            StringBuilder correctHtml = new StringBuilder();

            indexOfElementToMerge = getIndexOfElementToMerge(html);

            if (indexOfElementToMerge == NO_OCCURRENCE) break;

            correctHtml
                    .append(appendHtmlUntilPartToMerge(html, indexOfElementToMerge - 7))
                    .append(appendPartOfClassToMerge(html, indexOfElementToMerge, indexOfElementToMerge + 5))
                    .append(appendEndOfSpan(html, indexOfElementToMerge - 7, indexOfElementToMerge))
                    .append(appendEndOfHtmlAndReplace(html, indexOfElementToMerge + 5));

            html = correctHtml.toString();
        }
        return html;
    }

    private static String formatElementWithGroup(String element) {
        if (element.contains("-")) {
            int indexOfDash = element.indexOf("-");
            String numberOfGroup = element.substring(indexOfDash);

            element = element.substring(0, 1) + " " + element.substring(1, indexOfDash) + numberOfGroup;
        } else {
            element = element.substring(0, 1) + " " + element.substring(1);
        }

        return element;
    }

    private static String appendEndOfHtmlAndReplace(String html, int start) {
        return html.substring(start).replaceFirst(STUDENT_CLASS_PURE, TEACHER_CLASS_PURE);
    }

    private static void deleteEmptyLessons(Timetable timetable) {
        for (int i = timetable.getSchedule().getMon().size()-1; i >= 0; i--) {
            System.out.println(timetable.getSchedule().getMon().get(i));
            if (!timetable.getSchedule().getMon().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getMon().remove(i);
        }

        for (int i = timetable.getSchedule().getTue().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getTue().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getTue().remove(i);
        }

        for (int i = timetable.getSchedule().getWed().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getWed().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getWed().remove(i);
        }

        for (int i = timetable.getSchedule().getThu().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getThu().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getThu().remove(i);
        }

        for (int i = timetable.getSchedule().getFri().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getFri().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getFri().remove(i);
        }

        for (int i = timetable.getSchedule().getSat().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getSat().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getSat().remove(i);
        }

        for (int i = timetable.getSchedule().getSun().size()-1; i >= 0; i--) {
            if (!timetable.getSchedule().getSun().get(i).getGroups().isEmpty()) {
                break;
            }
            timetable.getSchedule().getSun().remove(i);
        }
    }

    @Data
    @Builder
    private static class HtmlElements {
        private Elements o;
        private Elements n;
        private Elements s;
        private Elements p;
    }
}
