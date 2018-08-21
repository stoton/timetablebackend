package com.github.stoton.timetablebackend.parser.optivum;

import com.github.stoton.timetablebackend.parser.optivum.strategy.OptivumTimetableStrategy;

public class OptivumHtmlRepairer {

    private OptivumTimetableStrategy optivumTimetableStrategy;

    public OptivumHtmlRepairer(OptivumTimetableStrategy optivumTimetableStrategy) {
        this.optivumTimetableStrategy = optivumTimetableStrategy;
    }

    public String fixHtml(String html) {
        return optivumTimetableStrategy.fixHtml(html);
    }

    public boolean isHtmlValid(String html) {
        return optivumTimetableStrategy.isHtmlValid(html);
    }

}