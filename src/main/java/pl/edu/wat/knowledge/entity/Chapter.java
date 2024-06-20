package pl.edu.wat.knowledge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Chapter extends Entity{
    private Integer score;
    private String collection;
    private String title;
    @DBRef
    private Book book;
    @DBRef
    private List<Author> authors;
}
