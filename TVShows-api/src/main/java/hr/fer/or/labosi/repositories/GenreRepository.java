package hr.fer.or.labosi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.fer.or.labosi.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {

	@Query(value = "SELECT * FROM genre u where u.genreid = ?1", nativeQuery = true)
	Genre findGenreFromId(int id);
}