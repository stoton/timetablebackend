package com.github.stoton.timetablebackend.parser;

import com.github.stoton.timetablebackend.exception.UnknownTimetableTypeException;
import org.jsoup.nodes.Document;

import java.util.List;

public interface TimetableIndexItemsParser<T> {
    List<T> parseIndexItems(Document html) throws UnknownTimetableTypeException;
}
