package com.github.stoton.timetablebackend.domain.timetable;

public enum TimetableType {
    STUDENT("student"),
    TEACHER("teacher"),
    ROOM("room");

    private final String text;

    TimetableType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
