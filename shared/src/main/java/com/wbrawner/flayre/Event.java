package com.wbrawner.flayre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static com.wbrawner.flayre.Utils.randomId;

public class Event {
    private final String id;
    private final String appId;
    private final Date date;
    private final InteractionType type;
    private final String userAgent;
    private final String platform;
    private final String manufacturer;
    private final String model;
    private final String version;
    private final String locale;
    private final String sessionId;
    private final String data;

    public Event(String appId,
                 Date date,
                 InteractionType type,
                 String userAgent,
                 String platform,
                 String manufacturer,
                 String model,
                 String version,
                 String locale,
                 String sessionId,
                 String data) {
        this(
                randomId(32),
                appId,
                date,
                type,
                userAgent,
                platform,
                manufacturer,
                model,
                version,
                locale,
                sessionId,
                data
        );
    }

    public Event(String id,
                 String appId,
                 Date date,
                 InteractionType type,
                 String userAgent,
                 String platform,
                 String manufacturer,
                 String model,
                 String version,
                 String locale,
                 String sessionId,
                 String data) {
        this.id = id;
        this.appId = appId;
        this.date = date;
        this.type = type;
        this.userAgent = userAgent;
        this.platform = platform;
        this.manufacturer = manufacturer;
        this.model = model;
        this.version = version;
        this.locale = locale;
        this.sessionId = sessionId;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public Date getDate() {
        return date;
    }

    public InteractionType getType() {
        return type;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPlatform() {
        return platform;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getVersion() {
        return version;
    }

    public String getLocale() {
        return locale;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getData() {
        return data;
    }

    public enum InteractionType {
        VIEW,
        CLICK,
        ERROR,
        CRASH
    }

    public static Event fromResultSet(ResultSet rs) throws SQLException {
        return new Event(
                rs.getString(rs.findColumn("id")),
                rs.getString(rs.findColumn("app_id")),
                rs.getDate(rs.findColumn("date")),
                Event.InteractionType.valueOf(rs.getString(rs.findColumn("type"))),
                rs.getString(rs.findColumn("user_agent")),
                rs.getString(rs.findColumn("platform")),
                rs.getString(rs.findColumn("manufacturer")),
                rs.getString(rs.findColumn("model")),
                rs.getString(rs.findColumn("version")),
                rs.getString(rs.findColumn("locale")),
                rs.getString(rs.findColumn("session_id")),
                rs.getString(rs.findColumn("data"))
        );
    }
}

