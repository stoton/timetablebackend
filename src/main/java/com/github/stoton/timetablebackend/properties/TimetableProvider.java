package com.github.stoton.timetablebackend.properties;

public enum TimetableProvider {

    OPTIVUM_TIMETABLE("optivum-timetable");

    private final String text;

    TimetableProvider(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
