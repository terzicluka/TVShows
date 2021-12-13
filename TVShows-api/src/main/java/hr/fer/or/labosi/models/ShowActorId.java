package hr.fer.or.labosi.models;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class ShowActorId implements Serializable {

	@OnDelete(action = OnDeleteAction.CASCADE)
	private int showId;

	@OnDelete(action = OnDeleteAction.CASCADE)
	private int actorId;

	public ShowActorId() {

	}

	public ShowActorId(int showId, int actorId) {
		super();
		this.showId = showId;
		this.actorId = actorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId, showId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShowActorId other = (ShowActorId) obj;
		return actorId == other.actorId && showId == other.showId;
	}
}
