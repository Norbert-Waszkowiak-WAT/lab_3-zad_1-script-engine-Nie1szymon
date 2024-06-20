package pl.edu.wat.knowledge.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Journal extends Entity{
    private Integer baseScore;
    private String title;
    private String Issn;
    @DBRef
    private Publisher Publisher;
}
