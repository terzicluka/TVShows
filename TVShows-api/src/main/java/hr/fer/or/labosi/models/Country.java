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
@Table(name = "country")
@Inheritance(strategy = InheritanceType.JOINED)
public class Country {

	@Id
	@GenericGenerator(name = "sequence_dep_id", strategy = "hr.fer.or.labosi.utility.IntegerIDGenerator")
	@GeneratedValue(generator = "sequence_dep_id")
	@Column(name = "countryid", columnDefinition = "int4", nullable = false)
	private int countryId;

	@Column(name = "countryname", nullable = false)
	private String countryName;

	public Country(int countryId, String countryName) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Country() {

	}
}
