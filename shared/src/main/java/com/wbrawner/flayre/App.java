package com.wbrawner.flayre;

import static com.wbrawner.flayre.Utils.randomId;

public class App {
    private final String id;
    private final String name;

    public App(String name) {
        this(randomId(32), name);
    }

    public App(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
