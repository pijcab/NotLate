package com.projet.e4fi.notlate;

import javax.xml.datatype.Duration;

public class Action {
    private String name;
    private Duration duration;

    public Action() {
    }

    public Action(String _name, Duration _duration) {
        name = _name;
        _duration = duration;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
