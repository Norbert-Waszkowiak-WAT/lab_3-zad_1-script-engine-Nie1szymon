package pl.edu.wat.knowledge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Author extends Entity{
    private String surname;
    private String name;
    private String pesel;
    private Integer score;
    @DBRef
    private Affilation affilation;
}
