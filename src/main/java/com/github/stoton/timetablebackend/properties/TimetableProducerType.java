package com.github.stoton.timetablebackend.properties;

public enum TimetableProducerType {

    OPTIVUM_TIMETABLE(0);

    private final int text;

    TimetableProducerType(int text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.valueOf(text);
    }
}
