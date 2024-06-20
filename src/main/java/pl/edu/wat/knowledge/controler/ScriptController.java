package pl.edu.wat.knowledge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.knowledge.service.ScriptService;

import java.io.File;
import java.io.FileNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PutMapping()
    public ResponseEntity<String> execScript(@RequestBody String script) {
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK);
    }

    @GetMapping("/update-authors-score")
    public ResponseEntity<String> updateAuthorsScore() {
        try {
            File scriptFile = ResourceUtils.getFile("classpath:scripts/updateAuthorsScore.js");
            String result = scriptService.execScriptFromFile(scriptFile.getAbsolutePath());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>("Script file not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

