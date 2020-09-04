package com.wbrawner.flayre;

import static com.wbrawner.flayre.Utils.randomId;

class App {
    private final String id;
    private final String name;

    App(String name) {
        this(randomId(32), name);
    }

    App(String id, String name) {
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
