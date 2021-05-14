package navin.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import navin.springframework.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);

}
