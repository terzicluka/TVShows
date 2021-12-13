package hr.fer.or.labosi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.fer.or.labosi.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

	@Query(value = "SELECT * FROM country u where u.countryid = ?1", nativeQuery = true)
	Optional<Country> findCountryFromId(int id);
}