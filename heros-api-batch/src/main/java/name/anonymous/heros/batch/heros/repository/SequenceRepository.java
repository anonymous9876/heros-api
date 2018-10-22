package name.anonymous.heros.batch.heros.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import name.anonymous.heros.batch.heros.model.entity.batch.Sequence;

@Repository
public interface SequenceRepository extends CrudRepository<Sequence, String> {

}
