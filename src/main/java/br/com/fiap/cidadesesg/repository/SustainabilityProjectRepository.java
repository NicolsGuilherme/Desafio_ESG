package br.com.fiap.cidadesesg.repository;

import br.com.fiap.cidadesesg.domain.SustainabilityProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SustainabilityProjectRepository extends MongoRepository<SustainabilityProject, String> {
    List<SustainabilityProject> findByCategory(String category);
    List<SustainabilityProject> findByStatus(String status);
    long countByStatus(String status);
}
