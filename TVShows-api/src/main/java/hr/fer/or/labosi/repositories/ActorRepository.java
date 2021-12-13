package hr.fer.or.labosi.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.fer.or.labosi.models.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, String> {

	@Query(value = "SELECT * FROM actor u where u.actorid = ?1", nativeQuery = true)
	Optional<Actor> findActorFromId(int id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE actor u set actorname = ?1 where u.actorId = ?2", nativeQuery = true)
	void updateActorName(String actorName, int actorId);
}