package de.visagistikmanager.service;

import de.visagistikmanager.model.customer.Customer;

public class CustomerService extends AbstractEntityService<Customer> {

	public Customer findBySurnameAndforename(String surname, String forename) {
		return findWithNamedQuery(Customer.FIND_BY_SURNAME_AND_LASTNAME,
				QueryParameter.with("surname", surname).and("forename", forename).parameters()).stream().findFirst()
						.get();
	}

}
