package pl.edu.wat.knowledge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.knowledge.AbstractContainerBaseTest;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.util.ResourceUtils.getFile;

public class UpdateAuthorsScoreTest extends AbstractContainerBaseTest {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setup() {
        setUpDatabase();
    }

    @Test
    public void testUpdateAuthorsScore() throws FileNotFoundException {
        // Ensure there are authors in the database
        List<Author> authors = authorRepository.findAll();
        assertEquals(20, authors.size());

        // Execute the script
        File scriptFile = getFile("classpath:scripts/updateAuthorsScore.js");
        scriptService.execScriptFromFile(scriptFile.getAbsolutePath());

        // Validate scores are updated
        authors = authorRepository.findAll();
        for (Author author : authors) {
            int expectedScore = scriptService.getScoreService().getScore(author, 2024);
            assertEquals(expectedScore, author.getScore());
        }
    }
}


