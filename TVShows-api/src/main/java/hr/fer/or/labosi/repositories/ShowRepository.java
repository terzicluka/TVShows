package hr.fer.or.labosi.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.fer.or.labosi.models.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, String> {

	@Query(value = "SELECT * FROM shows", nativeQuery = true)
	List<Show> findAll();
	
	@Query(value = "SELECT * FROM shows u where u.showid = ?1", nativeQuery = true)
	Optional<Show> findShowById(int id);

	@Query(value = "SELECT * FROM shows u where u.isdiscontinued = 't'", nativeQuery = true)
	Optional<List<Show>> findAllDiscontinedShows();
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM shows u where u.showid = ?1", nativeQuery = true)
	void deleteShowById(int showId);
	
	@Query(value = "SELECT showid FROM shows u where u.showname = ?1", nativeQuery = true)
	Optional<Integer> getShowIdFromShowName(String showname);  
}
