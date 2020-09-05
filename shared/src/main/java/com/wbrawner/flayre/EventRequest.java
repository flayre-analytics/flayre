package com.wbrawner.flayre;

import java.util.Date;

public class EventRequest {
    public final String appId;
    public final Date date;
    public final Event.InteractionType type;
    public final String platform;
    public final String manufacturer;
    public final String model;
    public final String version;
    public final String locale;
    public final String sessionId;
    public final String data;

    public EventRequest() {
        // Default constructor required for Spring
        appId = null;
        date = null;
        type = null;
        platform = null;
        manufacturer = null;
        model = null;
        version = null;
        locale = null;
        sessionId = null;
        data = null;
    }

    public EventRequest(String appId,
                        Date date,
                        Event.InteractionType type,
                        String platform,
                        String manufacturer,
                        String model,
                        String version,
                        String locale,
                        String sessionId,
                        String data) {
        this.appId = appId;
        this.date = date;
        this.type = type;
        this.platform = platform;
        this.manufacturer = manufacturer;
        this.model = model;
        this.version = version;
        this.locale = locale;
        this.sessionId = sessionId;
        this.data = data;
    }
}
