package de.visagistikmanager.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String surname;

	private String forename;

	private LocalDate birthday;

	private String street;

	private Integer streetNumber;

	private Integer zip;

	private String city;

	public String getAdress() {
		return street + " " + streetNumber + "\n" + zip + " " + city;
	}

}
