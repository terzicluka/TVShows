package hr.fer.or.labosi.models;

import java.util.List;

public class ShowsResponse {

	public int showId;
	public int numberOfReviews;
	public String description;
	public char isDiscontinued;
	public Genre genre;
	public List<Actor> actors;
	public Country country;
	public String showName;
	public int averageRating;

	public ShowsResponse() {

	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getIsDiscontinued() {
		return isDiscontinued;
	}

	public void setIsDiscontinued(char isDiscontinued) {
		this.isDiscontinued = isDiscontinued;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public int getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}

	public ShowsResponse(int showId, int numberOfReviews, String description, char isDiscontinued, Genre genre,
			List<Actor> actors, Country country, String showName, int averageRating) {
		super();
		this.showId = showId;
		this.numberOfReviews = numberOfReviews;
		this.description = description;
		this.isDiscontinued = isDiscontinued;
		this.genre = genre;
		this.actors = actors;
		this.country = country;
		this.showName = showName;
		this.averageRating = averageRating;
	}
}
