package app.model;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DadoAgregadoRepository extends MongoRepository<DadoAgregado, String> {
    Optional<DadoAgregado> findByType(String type);
}
