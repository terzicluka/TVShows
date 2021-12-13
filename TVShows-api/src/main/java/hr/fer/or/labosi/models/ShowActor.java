package hr.fer.or.labosi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "showactor")
@IdClass(ShowActorId.class)
public class ShowActor {

	@Id
	@Column(name = "showid")
	private int showId;

	@Id
	@Column(name = "actorid")
	private int actorId;

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public ShowActor(int showId, int actorId) {
		super();
		this.showId = showId;
		this.actorId = actorId;
	}
}
