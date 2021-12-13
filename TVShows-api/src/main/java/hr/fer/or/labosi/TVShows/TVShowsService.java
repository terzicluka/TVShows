package hr.fer.or.labosi.TVShows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hr.fer.or.labosi.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.or.labosi.models.Actor;
import hr.fer.or.labosi.models.Country;
import hr.fer.or.labosi.models.Genre;
import hr.fer.or.labosi.models.Show;
import hr.fer.or.labosi.models.ShowsResponse;
import hr.fer.or.labosi.repositories.ActorRepository;
import hr.fer.or.labosi.repositories.CountryRepository;
import hr.fer.or.labosi.repositories.GenreRepository;
import hr.fer.or.labosi.repositories.ShowActorRepository;
import hr.fer.or.labosi.repositories.ShowRepository;

@Service
public class TVShowsService {

	private final ShowRepository showRepository;
	private final GenreRepository genreRepository;
	private final ShowActorRepository showActorRepository;
	private final ActorRepository actorRepository;
	private final CountryRepository countryRepository;

	@Autowired
	public TVShowsService(ShowRepository showRepository, GenreRepository genreRepository,
			ShowActorRepository showActorRepository, ActorRepository actorRepository,
			CountryRepository countryRepository) {
		this.showRepository = showRepository;
		this.genreRepository = genreRepository;
		this.actorRepository = actorRepository;
		this.showActorRepository = showActorRepository;
		this.countryRepository = countryRepository;
	}

	public Map<String, List<ShowsResponse>> getShows() {
		List<Show> shows = showRepository.findAll();
		List<ShowsResponse> response = new ArrayList<>();
		for (Show show : shows) {
			Genre genre = genreRepository.findGenreFromId(show.getGenreId());
			Country country = countryRepository.findCountryFromId(show.getCountryId()).get();
			List<Integer> actorIds = showActorRepository.findActorIdsFromShowId(show.getShowId());
			List<Actor> actors = new ArrayList<>();
			for (int id : actorIds) {
				Optional<Actor> actor = actorRepository.findActorFromId(id);
				actors.add(actor.get());
			}
			response.add(new ShowsResponse(show.getShowId(), show.getNumberOfReviews(), show.getShowDescription(),
					show.getIsDiscontinued(), genre, actors, country, show.getShowName(), show.getAverageRating()));
		}
		HashMap<String, List<ShowsResponse>> finalResponse = new HashMap<>();
		finalResponse.put("shows", response);
		return finalResponse;
	}

	public ShowsResponse getShowFromShowId(int showId) {
		Optional<Show> optionalShow = showRepository.findShowById(showId);
		if (!optionalShow.isPresent())
			throw new ApiRequestException("A show with id " + showId + " does not exist");
		Show show = optionalShow.get();
		Genre genre = genreRepository.findGenreFromId(show.getGenreId());
		Country country = countryRepository.findCountryFromId(show.getCountryId()).get();
		List<Integer> actorIds = showActorRepository.findActorIdsFromShowId(show.getShowId());
		List<Actor> actors = new ArrayList<>();
		for (int id : actorIds) {
			Optional<Actor> actor = actorRepository.findActorFromId(id);
			actors.add(actor.get());
		}
		return new ShowsResponse(show.getShowId(), show.getNumberOfReviews(), show.getShowDescription(),
				show.getIsDiscontinued(), genre, actors, country, show.getShowName(), show.getAverageRating());
	}

	public Map<String, List<ShowsResponse>> getDiscontinuedShows() {
		Optional<List<Show>> optionalShows = showRepository.findAllDiscontinedShows();
		if (!optionalShows.isPresent())
			throw new ApiRequestException("No discontinued shows returned");
		List<Show> shows = optionalShows.get();
		List<ShowsResponse> response = new ArrayList<>();
		for (Show show : shows) {
			Genre genre = genreRepository.findGenreFromId(show.getGenreId());
			Country country = countryRepository.findCountryFromId(show.getCountryId()).get();
			List<Integer> actorIds = showActorRepository.findActorIdsFromShowId(show.getShowId());
			List<Actor> actors = new ArrayList<>();
			for (int id : actorIds) {
				Optional<Actor> actor = actorRepository.findActorFromId(id);
				actors.add(actor.get());
			}
			response.add(new ShowsResponse(show.getShowId(), show.getNumberOfReviews(), show.getShowDescription(),
					show.getIsDiscontinued(), genre, actors, country, show.getShowName(), show.getAverageRating()));
		}
		HashMap<String, List<ShowsResponse>> finalResponse = new HashMap<>();
		finalResponse.put("shows", response);
		return finalResponse;
	}

	public List<Genre> getGenres() {
		return genreRepository.findAll();
	}

	public Actor getActorFromActorId(int actorId) {
		Optional<Actor> optionalActor = actorRepository.findActorFromId(actorId);
		if (!optionalActor.isPresent())
			throw new ApiRequestException("An actor with id " + actorId + " does not exist");
		return optionalActor.get();
	}

	public void deleteShow(int showId) {
		Optional<Show> show = showRepository.findShowById(showId);
		if (!show.isPresent())
			throw new ApiRequestException("A show with id " + showId + " does not exist");
		showRepository.deleteShowById(showId);
		showActorRepository.deleteByShowId(showId);
	}

	public Actor addActor(Actor actor) {
		if (actor.getActorName().isBlank() || actor.getActorName().isEmpty())
			throw new ApiRequestException("An actor can't have an empty name");
		actorRepository.save(actor);
		return actor;
	}

	public void updateActor(Actor actor, int actorId) {
		Optional<Actor> optionalActor = actorRepository.findActorFromId(actorId);
		if (!optionalActor.isPresent())
			throw new ApiRequestException("An actor with id " + actorId + " does not exist");
		if (actor.getActorName().isBlank() || actor.getActorName().isEmpty())
			throw new ApiRequestException("An actor can't have an empty name");
		actorRepository.updateActorName(actor.getActorName(), actorId);
	}
}
