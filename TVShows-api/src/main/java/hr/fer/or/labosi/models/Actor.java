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
@Table(name = "actor")
@Inheritance(strategy = InheritanceType.JOINED)
public class Actor {

	@Id
	@GenericGenerator(name = "sequence_dep_id", strategy = "hr.fer.or.labosi.utility.IntegerIDGenerator")
	@GeneratedValue(generator = "sequence_dep_id")
	@Column(name = "actorid", columnDefinition = "int4", nullable = false)
	private int actorId;

	@Column(name = "actorname", nullable = false)
	private String actorName;

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public Actor(int actorId, String actorName) {
		super();
		this.actorId = actorId;
		this.actorName = actorName;
	}

	public Actor() {

	}
}
