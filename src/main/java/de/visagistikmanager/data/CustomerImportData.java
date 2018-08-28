package de.visagistikmanager.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		addCustomer("Alexander", "Mustermann");
		addCustomer("Bugs", "Bunny");
		addCustomer("Daisy", "Duck");
		addCustomer("Donald", "Duck");
		addCustomer("Nina", "Hagen");
		addCustomer("Jennie", "Musterfrau");
	}

	private void addCustomer(String forename, String surename) {
		Customer customer = new Customer();
		customer.setForename(forename);
		customer.setSurname(surename);
		this.entities.add(customer);

	}
}