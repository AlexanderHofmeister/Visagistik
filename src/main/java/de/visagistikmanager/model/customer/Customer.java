package de.visagistikmanager.model.customer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.ListAttribute;
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

	@ModelAttribute(placeholder = "Straße", row = 2, column = 0)
	private String street;

	@ModelAttribute(placeholder = "Hausnummer", row = 2, column = 1)
	private Integer streetNumber;

	@ModelAttribute(placeholder = "PLZ", row = 3, column = 0)
	private Integer zip;

	@ModelAttribute(placeholder = "Ort", row = 3, column = 1)
	private String city;

	@ModelAttribute(placeholder = "Unreinheiten", row = 4, column = 0)
	private boolean blemishes;

	@ElementCollection
	@ModelAttribute(placeholder = "Hautmerkmale", row = 5, column = 0)
	@ListAttribute({ "Trockene Haut", "Normale Haut", "Mischhaut", "Fettige Haut" })
	private Set<String> skinFeatures = new HashSet<>();

	@ElementCollection
	@ModelAttribute(placeholder = "Empfindlichkeit", row = 5, column = 1)
	@ListAttribute({ "Nicht empfindlich", "Etwas empfindlich", "Sehr empfindlich" })
	private Set<String> sensitivities = new HashSet<>();

	@ElementCollection
	@ModelAttribute(placeholder = "Verbesserungswünsche", row = 5, column = 2)
	@ListAttribute({ "Hautton ausgleichen", "Fältchen minimieren",
			"Haut um die Augen straffen, pflegen und Fältchen minimieren", "zusätzliche Feuchtigkeit spenden",
			"Augen-Make-up entfernen", "Unreinheiten bekämpfen, überschüssiges Hautfett regulieren",
			"Körper pflegen, die Silhouette straffen", "Hautbild verbessern", "Lippen pflegen", "Hände pflegen" })
	private Set<String> improvments = new HashSet<>();

	public String getAdress() {
		return street + " " + streetNumber + "\n" + zip + " " + city;
	}

}
