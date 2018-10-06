package de.visagistikmanager.service;

import java.util.Arrays;
import java.util.List;

import de.visagistikmanager.model.customer.Customer;

public class CustomerService extends AbstractEntityService<Customer> {

	public Customer findBySurnameAndforename(final String surname, final String forename) {
		return findWithNamedQuery(Customer.FIND_BY_SURNAME_AND_LASTNAME,
				QueryParameter.with("surname", surname).and("forename", forename).parameters()).stream().findFirst()
						.get();
	}

	public List<Customer> findBirthdays() {
		return findWithNamedQuery(Customer.FIND_CUSTOMER_WITH_BIRTHDAYS);
	}

	public static List<String> getSkinFeatures() {
		return Arrays.asList("Trockene Haut", "Normale Haut", "Mischhaut", "Fettige Haut");
	}

	public static List<String> getSensitives() {
		return Arrays.asList("Nicht empfindlich", "Etwas empfindlich", "Sehr empfindlich");
	}

	public static List<String> getImprovements() {
		return Arrays.asList("Hautton ausgleichen", "Fältchen minimieren",
				"Haut um die Augen straffen, pflegen und Fältchen minimieren", "zusätzliche Feuchtigkeit spenden",
				"Augen-Make-up entfernen", "Unreinheiten bekämpfen, überschüssiges Hautfett regulieren",
				"Körper pflegen, die Silhouette straffen", "Hautbild verbessern", "Lippen pflegen", "Hände pflegen");
	}

	public static List<String> getCurrentSkincare() {
		return Arrays.asList("Reinigung", "Maske", "Gesichtswasser", "Feuchtigkeitscreme", "Grundierung",
				"Wasser und Seife", "Sonstiges");
	}

	public static List<String> getInterests() {
		return Arrays.asList("Produkte für Familienmitglieder", "Make-up Tipps", "weitere Hautpflegeprodukte",
				"Gastgeberinnenprogramm", "Hautpflege für Männer", "Düfte und Körperpflege", "Geschäftsmöglichkeit",
				"Geschenkideen");
	}

	public static List<String> getContactOptions() {
		return Arrays.asList("E-Mail", "SMS", "Telefon", "Post");
	}

}
