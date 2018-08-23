package de.visagistikmanager.view.customer;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.view.BaseEntityView;
import lombok.Getter;

public class CustomerView extends BaseEntityView<Customer> {

	@Getter
	private CustomerService service;

	private CustomerEditView editView;

	private CustomerListView listView;

	@Override
	protected CustomerEditView getEditView() {
		if (editView == null) {
			editView = new CustomerEditView();
		}
		return editView;
	}

	@Override
	protected CustomerListView getListView() {
		if (listView == null) {
			service = new CustomerService();
			listView = new CustomerListView(getEditView(), panel);
		}
		return listView;
	}
}
