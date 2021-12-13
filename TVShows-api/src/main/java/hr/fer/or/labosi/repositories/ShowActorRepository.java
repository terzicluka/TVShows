package hr.fer.or.labosi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.fer.or.labosi.models.ShowActor;

@Repository
public interface ShowActorRepository extends JpaRepository<ShowActor, String> {

	@Query(value = "SELECT actorid FROM showactor u where u.showid = ?1", nativeQuery = true)
	List<Integer> findActorIdsFromShowId(int id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM showactor u where u.showid = ?1", nativeQuery = true)
	void deleteByShowId(int showId);
}