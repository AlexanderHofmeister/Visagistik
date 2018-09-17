package de.visagistikmanager.data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fastnate.data.AbstractDataProvider;

import de.visagistikmanager.model.customer.Customer;
import lombok.Getter;

public class CustomerImportData extends AbstractDataProvider {

	/** A list that contains all the created data. */
	@Getter
	private final List<Customer> entities = new ArrayList<>();

	/** Create the entities. */
	@Override
	public void buildEntities() throws IOException {
		for (int i = 0; i < 40; i++) {

			addCustomer("Alexander", "Mustermann", "Straße", "14D", 12020, "Berlüüün", true, "Senf",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
			addCustomer("Bugs", "Bunny", "Straße", "14D", 12020, "Berlüüün", false, "Senf",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
			addCustomer("Daisy", "Duck", "Straße", "14D", 12020, "Berlüüün", true, "Erdnüsse",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
			addCustomer("Donald", "Duck", "Straße", "14D", 12020, "Berlüüün", true, "Mais",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
			addCustomer("Nina", "Hagen", "Straße", "14D", 12020, "Berlüüün", true, "Soja",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
			addCustomer("Jennie", "Musterfrau", "Straße", "14D", 12020, "Blub", true, "Weizen",
					new HashSet<>(Arrays.asList("Trockene Haut", "Fettige Haut")));
		}
	}

	private void addCustomer(final String forename, final String surename, final String street,
			final String streetnumber, final int zip, final String city, final boolean blemishes,
			final String allergies, final Set<String> skinFeatrues) {
		final Customer customer = new Customer();
		customer.setForename(forename);
		customer.setBirthday(LocalDate.now());
		customer.setSurname(surename);
		customer.setStreet(street);
		customer.setStreetNumber(streetnumber);
		customer.setZip(zip);
		customer.setCity(city);
		customer.setBlemishes(blemishes);
		customer.setAllergies(allergies);
		customer.setSkinFeatures(skinFeatrues);
		customer.setSensitivities(new HashSet<>(Arrays.asList("Nicht empfindlich")));
		customer.setImprovments(
				new HashSet<>(Arrays.asList("Haut um die Augen straffen, pflegen und Fältchen minimieren",
						"Unreinheiten bekämpfen, überschüssiges Hautfett regulieren")));
		customer.setCurrentSkinCare(new HashSet<>(Arrays.asList("Maske", "Gesichtswasser")));
		customer.setSkinFeatures(skinFeatrues);
		customer.setSkinFeatures(skinFeatrues);

		this.entities.add(customer);

	}
}