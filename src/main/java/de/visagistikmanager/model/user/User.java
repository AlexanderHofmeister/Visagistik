package de.visagistikmanager.model.user;

import javax.persistence.Entity;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Label("Nachname")
	private String surname;

	@Label("Vorname")
	private String forename;

	@Label("Firmenname")
	private String company;

	@Label("Straße")
	private String street;

	@Label("Hausnummer")
	private String streetNumber;

	@Label("Postleitzahl")
	private Integer zip;

	@Label("Ort")
	private String city;

	@Label("Telefon")
	private String telephone;

	@Label("E-Mail")
	private String email;

	@Label("Website")
	private String url;

	@Label("Website")
	private String secondUrl;

	@Label("Bank")
	private String bank;

	@Label("IBAN")
	private String iban;

}
