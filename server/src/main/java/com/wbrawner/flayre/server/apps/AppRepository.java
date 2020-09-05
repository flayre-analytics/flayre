package com.wbrawner.flayre.server.apps;

import com.wbrawner.flayre.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AppRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS apps (\n" +
                "    id VARCHAR(32) PRIMARY KEY,\n" +
                "    name VARCHAR(256) UNIQUE NOT NULL\n" +
                ")");
    }

    @NonNull
    public List<App> getApps() {
        return jdbcTemplate.query("SELECT * from apps", (rs, rowNum) -> new App(
                rs.getString(rs.findColumn("id")),
                rs.getString(rs.findColumn("name"))
        ));
    }

    @Nullable
    public App getApp(String appId) {
        var results = jdbcTemplate.query(
                "SELECT * from apps WHERE id = ?",
                new Object[]{appId},
                (rs, rowNum) -> new App(
                        rs.getString(rs.findColumn("id")),
                        rs.getString(rs.findColumn("name"))
                )
        );

        if (results.size() == 1) {
            return results.get(0);
        }

        return null;
    }

    public boolean saveApp(App app) {
        return jdbcTemplate.update(
                "INSERT INTO apps (id, name) VALUES (?, ?)",
                app.getId(),
                app.getName()
        ) == 1;
    }

    /**
     * Update the name of a given app.
     *
     * @param appId the id of the app to update
     * @param name  the new name for the app
     * @return true on success or false on failure
     */
    public boolean updateApp(String appId, String name) {
        return jdbcTemplate.update("UPDATE apps SET name = ? WHERE id = ?", name, appId) == 1;
    }

    /**
     * Delete an app by its id.
     *
     * @param appId the id of the app to delete
     * @return true if the app was deleted or false if not
     */
    public boolean deleteApp(String appId) {
        return jdbcTemplate.update("DELETE FROM apps WHERE id = ?", appId) == 1;
    }
}
