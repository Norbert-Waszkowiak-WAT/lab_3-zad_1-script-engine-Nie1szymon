package pl.edu.wat.knowledge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.entity.Affilation;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Book;
import pl.edu.wat.knowledge.entity.Chapter;
import pl.edu.wat.knowledge.entity.Journal;
import pl.edu.wat.knowledge.entity.Publisher;
import pl.edu.wat.knowledge.repository.AffilationRepository;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;
import pl.edu.wat.knowledge.repository.JournalRepository;
import pl.edu.wat.knowledge.repository.PublisherRepository;

@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.6");

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected AffilationRepository affilationRepository;

    @Autowired
    protected PublisherRepository publisherRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected ChapterRepository chapterRepository;

    @Autowired
    protected JournalRepository journalRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    public void setUpDatabase() {
        // Dodanie Affilations
        for (int i = 1; i <= 10; i++) {
            var affilation = new Affilation();
            affilation.setName("Affilation " + i);
            affilationRepository.save(affilation);
        }

        // Dodanie Authors
        for (int i = 1; i <= 20; i++) {
            var author = new Author();
            author.setName("Name " + i);
            author.setSurname("Surname " + i);
            author.setPesel("1234567890" + i);
            author.setScore(i);
            author.setAffilation(affilationRepository.findAll().get(i % 10)); 
            authorRepository.save(author);
        }

        // Dodanie Publishers
        for (int i = 1; i <= 5; i++) {
            var publisher = new Publisher();
            publisher.setName("Publisher " + i);
            publisher.setLocation("Location " + i);
            publisherRepository.save(publisher);
        }

        // Dodanie Books
        for (int i = 1; i <= 5; i++) {
            var book = new Book();
            book.setIsbn("978-3-16-14841" + i);
            book.setYear(2020 + i);
            book.setBaseScore(10 + i);
            book.setTitle("Book " + i);
            book.setEditor(authorRepository.findAll().get(i % 20)); 
            book.setPublisher(publisherRepository.findAll().get(i % 5)); 
            bookRepository.save(book);
        }

        // Dodanie Chapters
        for (int i = 1; i <= 10; i++) {
            var chapter = new Chapter();
            chapter.setScore(10 + i);
            chapter.setCollection("Collection " + i);
            chapter.setTitle("Chapter " + i);
            chapter.setBook(bookRepository.findAll().get(i % 5)); 
            chapter.setAuthors(authorRepository.findAll().subList(0, 3)); 
            chapterRepository.save(chapter);
        }

        // Dodanie Journals
        for (int i = 1; i <= 5; i++) {
            var journal = new Journal();
            journal.setBaseScore(5 + i);
            journal.setTitle("Journal " + i);
            journal.setIssn("1234-567" + i);
            journal.setPublisher(publisherRepository.findAll().get(i % 5)); 
            journalRepository.save(journal);
        }

        // Dodanie Articles
        for (int i = 1; i <= 10; i++) {
            var article = new Article();
            article.setTitle("Article " + i);
            article.setNo(i);
            article.setCollection("Collection " + i);
            article.setScore(20 + i);
            article.setArticleNo(1000 + i);
            article.setVol(i);
            article.setAuthors(authorRepository.findAll().subList(0, 3)); 
            article.setJournal(journalRepository.findAll().get(i % 5)); 
            articleRepository.save(article);
        }
    }

}