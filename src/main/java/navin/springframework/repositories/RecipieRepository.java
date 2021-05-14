package navin.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import navin.springframework.domain.Recipie;

public interface RecipieRepository extends CrudRepository<Recipie, Long> {

}
