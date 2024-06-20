package pl.edu.wat.knowledge.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class ScriptService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final AffilationRepository affilationRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final ChapterRepository chapterRepository;
    private final JournalRepository journalRepository;
    private final ScoreService scoreService;

    @Autowired
    public ScriptService(ArticleRepository articleRepository,
                         AuthorRepository authorRepository,
                         AffilationRepository affilationRepository,
                         PublisherRepository publisherRepository,
                         BookRepository bookRepository,
                         ChapterRepository chapterRepository,
                         JournalRepository journalRepository,
                         ScoreService scoreService) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.affilationRepository = affilationRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
        this.journalRepository = journalRepository;
        this.scoreService = scoreService;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", articleRepository);
            bindings.putMember("authorRepository", authorRepository);
            bindings.putMember("affilationRepository", affilationRepository);
            bindings.putMember("publisherRepository", publisherRepository);
            bindings.putMember("bookRepository", bookRepository);
            bindings.putMember("chapterRepository", chapterRepository);
            bindings.putMember("journalRepository", journalRepository);
            bindings.putMember("scoreService", scoreService);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing script", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }

    public String execScriptFromFile(String filePath) {
        try {
            String script = new String(Files.readAllBytes(Paths.get(filePath)));
            return exec(script);
        } catch (IOException e) {
            log.error("Error reading script file", e);
            return "Error reading script file: " + e.getMessage();
        }
    }

    public ScoreService getScoreService() {
        return scoreService;
    }
}

