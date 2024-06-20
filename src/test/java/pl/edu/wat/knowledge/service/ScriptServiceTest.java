package pl.edu.wat.knowledge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.wat.knowledge.AbstractContainerBaseTest;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScriptServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setup() {
        setUpDatabase();
    }

    @Test
    @Disabled 
    public void testCalc() {
        String script = """
                var x = 1;
                var y = 2;
                x + y;
                """;

        assertEquals("3", scriptService.exec(script));
    }

    @Test
    @Disabled 
    public void testAddAndRemoveAuthor() {
        String addScript = """
                var Author = Java.type('pl.edu.wat.knowledge.entity.Author');
                var author = new Author();
                author.setName("Patrycja");
                author.setSurname("Woda");
                author.setPesel("123123123");
                var savedAuthor = authorRepository.save(author);
                savedAuthor.getId();
                """;
        String id = scriptService.exec(addScript);
        assertNotNull(id);

        String deleteScript = String.format("""
                authorRepository.deleteById('%s');
                """, id);
        scriptService.exec(deleteScript);

        Author deletedAuthor = authorRepository.findById(id).orElse(null);
        assertEquals(null, deletedAuthor);
    }
}


