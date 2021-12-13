package hr.fer.or.labosi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "shows")
@Inheritance(strategy = InheritanceType.JOINED)
@OnDelete(action = OnDeleteAction.CASCADE)
public class Show {

	@Id
	@GenericGenerator(name = "sequence_dep_id", strategy = "hr.fer.or.labosi.utility.IntegerIDGenerator")
	@GeneratedValue(generator = "sequence_dep_id")
	@Column(name = "showid", columnDefinition = "int4", nullable = false)
	private int showId;

	@Column(name = "showname", nullable = false)
	private String showName;

	@Column(name = "description", nullable = false)
	private String showDescription;

	@Column(name = "numberofreviews", nullable = true)
	private int numberOfReviews;

	@Column(name = "averagerating", nullable = true)
	private int averageRating;

	@Column(name = "countryid", nullable = true)
	private int countryId;

	@Column(name = "genreid", nullable = true)
	private int genreId;

	@Column(name = "isdiscontinued", nullable = true)
	private char isDiscontinued;

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getShowDescription() {
		return showDescription;
	}

	public void setShowDescription(String showDescription) {
		this.showDescription = showDescription;
	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public char getIsDiscontinued() {
		return isDiscontinued;
	}

	public void setIsDiscontinued(char isDiscontinued) {
		this.isDiscontinued = isDiscontinued;
	}

	public Show(int showId, String showName, String showDescription, int numberOfReviews, int averageRating,
			int countryId, char isDiscontinued, int genreId) {
		super();
		this.showId = showId;
		this.showName = showName;
		this.showDescription = showDescription;
		this.numberOfReviews = numberOfReviews;
		this.averageRating = averageRating;
		this.countryId = countryId;
		this.isDiscontinued = isDiscontinued;
		this.genreId = genreId;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public Show() {

	}
}
