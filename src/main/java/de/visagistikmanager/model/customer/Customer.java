package de.visagistikmanager.model.customer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.ListAttribute;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.model.Title;
import de.visagistikmanager.model.order.Order;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Title("Kunde")
@NamedQuery(name = Customer.FIND_BY_SURNAME_AND_LASTNAME, query = "SELECT c from Customer c WHERE c.surname = :surname AND c.forename = :forename")
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_SURNAME_AND_LASTNAME = "findBySurnameAndLastName";

	@ModelAttribute(placeholder = "Nachname", row = 0, column = 0)
	@TableAttribute(headerLabel = "Nachname")
	private String surname;

	@ModelAttribute(placeholder = "Vorname", row = 0, column = 1)
	@TableAttribute(headerLabel = "Vorname")
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

	@ModelAttribute(placeholder = "Allergien", row = 4, column = 1)
	private boolean allergies;

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

	@ElementCollection
	@ModelAttribute(placeholder = "Derzeitige Hautpflege", row = 6, column = 0)
	@ListAttribute({ "Reinigung", "Maske", "Gesichtswasser", "Feuchtigkeitscreme", "Grundierung", "Wasser und Seife",
			"Sonstiges" })
	private Set<String> currentSkinCare = new HashSet<>();

	@ElementCollection
	@ModelAttribute(placeholder = "Interessiert an", row = 6, column = 1)
	@ListAttribute({ "Produkte für Familienmitglieder", "Make-up Tipps", "weitere Hautpflegeprodukte",
			"Gastgeberinnenprogramm", "Hautpflege für Männer", "Düfte und Körperpflege", "Geschäftsmöglichkeit",
			"Geschenkideen" })
	private Set<String> interests = new HashSet<>();

	@ElementCollection
	@ModelAttribute(placeholder = "Kontaktoptionen", row = 6, column = 2)
	@ListAttribute({ "E-Mail", "SMS", "Telefon", "Post" })
	private Set<String> contactOptions = new HashSet<>();

	@OneToMany
	private Set<Order> orders;

	@TableAttribute(headerLabel = "Anschrift")
	public String getAdress() {
		return this.street + " " + this.streetNumber + "\n" + this.zip + " " + this.city;
	}

	public String getFullNameInverse() {
		return this.surname + ", " + this.forename;
	}

	@Override
	public String toString() {
		return getFullNameInverse();
	}

}
