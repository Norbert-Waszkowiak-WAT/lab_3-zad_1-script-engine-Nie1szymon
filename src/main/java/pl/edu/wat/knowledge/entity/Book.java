package pl.edu.wat.knowledge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Book extends Entity{
    private String isbn;
    private Integer year;
    private Integer baseScore;
    private String title;
    @DBRef
    private Author editor;
    @DBRef
    private Publisher publisher;
}
