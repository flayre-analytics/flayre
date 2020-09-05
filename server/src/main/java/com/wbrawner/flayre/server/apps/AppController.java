package com.wbrawner.flayre.server.apps;

import com.wbrawner.flayre.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apps")
public class AppController {

    private final AppRepository appRepository;

    @Autowired
    public AppController(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @GetMapping
    public ResponseEntity<List<App>> getApps() {
        return ResponseEntity.ok(appRepository.getApps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<App> getApp(@PathVariable String id) {
        App app;
        if ((app = appRepository.getApp(id)) != null) {
            return ResponseEntity.ok(app);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<App> createApp(@RequestBody String name) {
        App app = new App(name);
        if (appRepository.saveApp(app)) {
            return ResponseEntity.ok(app);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateApp(@PathVariable String id, @RequestBody String name) {
        if (appRepository.updateApp(id, name)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApp(@PathVariable String id) {
        if (appRepository.deleteApp(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
