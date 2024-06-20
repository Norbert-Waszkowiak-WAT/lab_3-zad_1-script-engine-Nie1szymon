package pl.edu.wat.knowledge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends Entity{
    private String title;
    private Integer no;
    private String collection;
    private Integer score;
    private Integer articleNo;
    private Integer vol;
    private Integer year; // dodałem te pole bo inaczerj artykuł nie miał zadnego połączenia z rokiem
    @DBRef
    private List<Author> authors;
    @DBRef
    private Journal journal; 
}
