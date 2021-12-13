package hr.fer.or.labosi.TVShows;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.or.labosi.models.Actor;
import hr.fer.or.labosi.models.Genre;
import hr.fer.or.labosi.models.ShowsResponse;

@RestController
@RequestMapping(path = "/TVShows/v1")
public class TVShowsController {
	
	private final TVShowsService showService;
	
	@Autowired
	public TVShowsController(TVShowsService showService) {
		this.showService = showService;
	}
	
	@GetMapping(path = "/shows")
	public Map<String, List<ShowsResponse>> getShows() {
		return showService.getShows();
	}
	
	@GetMapping(path = "/show")
	public ShowsResponse getShowFromShowId(@RequestParam("showId") int showId) {
		return showService.getShowFromShowId(showId);
	}
	
	@GetMapping(path = "/shows/discontinued")
	public Map<String, List<ShowsResponse>> getDiscontinuedShows() {
		return showService.getDiscontinuedShows();
	}
	
	@GetMapping(path = "/genres")
	public List<Genre> getGenres() {
		return showService.getGenres();
	}
	
	@GetMapping(path = "/actor")
	public Actor getActorFromActorId(@RequestParam("actorId") int actorId) {
		return showService.getActorFromActorId(actorId);
	}
	
	@DeleteMapping(path = "/delete/show")
	public void deleteShow(@RequestParam("showId") int showId) {
		showService.deleteShow(showId);
	}
	
	@PostMapping(path = "/actor")
	public Actor addActor(@RequestBody Actor actor) {
		return showService.addActor(actor);
	}
	
	@PutMapping(path = "/actor")
	public void updateActor(@RequestBody Actor actor, @RequestParam("actorId") int actorId) {
		showService.updateActor(actor, actorId); 
	}
}
