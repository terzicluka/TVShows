package hr.fer.or.labosi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "genre")
@Inheritance(strategy = InheritanceType.JOINED)
public class Genre {

	@Id
	@GenericGenerator(name = "sequence_dep_id", strategy = "hr.fer.or.labosi.utility.IntegerIDGenerator")
	@GeneratedValue(generator = "sequence_dep_id")
	@Column(name = "genreid", columnDefinition = "int4", nullable = false)
	private int genreId;

	@Column(name = "genrename", nullable = false)
	private String genreName;

	public Genre(int genreId, String genreName) {
		super();
		this.genreId = genreId;
		this.genreName = genreName;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public Genre() {

	}
}
