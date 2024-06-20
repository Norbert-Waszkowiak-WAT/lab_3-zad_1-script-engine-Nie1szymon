package pl.edu.wat.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.entity.Book;
import pl.edu.wat.knowledge.entity.Chapter;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.BookRepository;
import pl.edu.wat.knowledge.repository.ChapterRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScoreService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Double> getArticleScores(Author author, Integer year) {
        // Pobierz wszystkie artykuły opublikowane przez autora w danym roku
        List<Article> articles = articleRepository.findByAuthorsContainingAndYear(author, year);

        // Oblicz punkty dla każdego artykułu, dzieląc je przez liczbę autorów, i posortuj malejąco
        return articles.stream()
                .map(article -> article.getScore() / (double) article.getAuthors().size())
                .sorted(Comparator.reverseOrder()) // Sortowanie malejąco
                .collect(Collectors.toList());
    }

    public List<Double> getChapterScores(Author author, Integer year) {
        // Pobierz książki opublikowane w danym roku
        List<Book> books = bookRepository.findByYear(year);

        // Pobierz rozdziały opublikowane przez autora w książkach z odpowiednim rokiem
        List<Chapter> chapters = chapterRepository.findByAuthorsInAndBookIn(List.of(author), books);

        // Oblicz punkty dla każdego rozdziału, dzieląc je przez liczbę autorów, i posortuj malejąco
        return chapters.stream()
                .map(chapter -> chapter.getScore() / (double) chapter.getAuthors().size())
                .sorted(Comparator.reverseOrder()) // Sortowanie malejąco
                .collect(Collectors.toList());
    }

    public Integer getScore(Author author, Integer year) {
        // Pobierz punkty z artykułów i rozdziałów
        List<Double> articleScores = getArticleScores(author, year);
        List<Double> chapterScores = getChapterScores(author, year);

        // Połącz i posortuj punkty malejąco
        List<Double> allScores = Stream.concat(articleScores.stream(), chapterScores.stream())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        // Suma punktów z czterech najwyżej punktowanych pozycji
        return allScores.stream()
                .limit(4)
                .mapToInt(Double::intValue)
                .sum();
    }
}




