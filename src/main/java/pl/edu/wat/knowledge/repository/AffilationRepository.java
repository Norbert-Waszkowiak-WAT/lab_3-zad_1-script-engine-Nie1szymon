package pl.edu.wat.knowledge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.wat.knowledge.entity.Affilation;

@RepositoryRestResource(collectionResourceRel = "affilations", path = "affilations")
public interface AffilationRepository extends MongoRepository<Affilation, String> {
}