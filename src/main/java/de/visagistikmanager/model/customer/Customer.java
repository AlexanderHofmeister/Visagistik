package de.visagistikmanager.model.customer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.ModelAttribute;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Nachname", row = 0, column = 0)
	private String surname;

	@ModelAttribute(placeholder = "Vorname", row = 0, column = 1)
	private String forename;

	@ModelAttribute(placeholder = "Geburtstag", row = 1, column = 0)
	private LocalDate birthday;

	@ModelAttribute(placeholder = "Straﬂe", row = 2, column = 0)
	private String street;

	@ModelAttribute(placeholder = "Hausnummer", row = 2, column = 1)
	private Integer streetNumber;

	@ModelAttribute(placeholder = "PLZ", row = 3, column = 0)
	private Integer zip;

	@ModelAttribute(placeholder = "Ort", row = 3, column = 1)
	private String city;

	@ModelAttribute(placeholder = "Unreinheiten", row = 4, column = 0)
	private boolean blemishes;

	@Enumerated(EnumType.STRING)
	@ElementCollection
	@ModelAttribute(placeholder = "Hautmerkmale", row = 5, column = 0)
	private Set<SkinFeature> skinFeatures = new HashSet<>();

	@Enumerated(EnumType.STRING)
	@ElementCollection
	@ModelAttribute(placeholder = "Empfindlichkeit", row = 5, column = 1)
	private Set<Sensitivity> sensitivities = new HashSet<>();
	
	public String getAdress() {
		return street + " " + streetNumber + "\n" + zip + " " + city;
	}

}
