package com.wbrawner.flayre.server.events;

import com.wbrawner.flayre.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS events (\n" +
                "    id VARCHAR(32) PRIMARY KEY,\n" +
                "    app_id VARCHAR(32) NOT NULL,\n" +
                "    date DATETIME NOT NULL,\n" +
                "    user_agent VARCHAR(256),\n" +
                "    platform VARCHAR(32),\n" +
                "    manufacturer VARCHAR(256),\n" +
                "    model VARCHAR(256),\n" +
                "    version VARCHAR(32),\n" +
                "    locale VARCHAR(8),\n" +
                "    session_id VARCHAR(32),\n" +
                "    data TEXT DEFAULT NULL,\n" +
                "    type VARCHAR(256) DEFAULT NULL,\n" +
                "    FOREIGN KEY (app_id)\n" +
                "        REFERENCES apps(id)\n" +
                "        ON DELETE CASCADE\n" +
                ")");
    }

    @NonNull
    public List<Event> getEvents() {
        return jdbcTemplate.query("SELECT * from events", (rs, rowNum) -> Event.fromResultSet(rs));
    }

    @Nullable
    public Event getEvent(String eventId) {
        var results = jdbcTemplate.query(
                "SELECT * from events WHERE id = ?",
                new Object[]{eventId},
                (rs, rowNum) -> Event.fromResultSet(rs)
        );

        if (results.size() == 1) {
            return results.get(0);
        }

        return null;
    }

    public boolean saveEvent(Event event) {
        return jdbcTemplate.update(
                "INSERT INTO events (" +
                        "id, " +
                        "app_id, " +
                        "date, " +
                        "user_agent, " +
                        "platform, " +
                        "manufacturer, " +
                        "model, " +
                        "version, " +
                        "locale, " +
                        "session_id, " +
                        "data, " +
                        "type" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                event.getId(),
                event.getAppId(),
                event.getDate(),
                event.getUserAgent(),
                event.getPlatform(),
                event.getManufacturer(),
                event.getModel(),
                event.getVersion(),
                event.getLocale(),
                event.getSessionId(),
                event.getData(),
                event.getType().name()
        ) == 1;
    }

    /**
     * Delete all events before a given date.
     *
     * @param before the date before which all events will be deleted
     * @return the number of events that were deleted
     */
    public int deleteEvents(Date before) {
        return jdbcTemplate.update("DELETE FROM events WHERE date <= ?", before);
    }
}
