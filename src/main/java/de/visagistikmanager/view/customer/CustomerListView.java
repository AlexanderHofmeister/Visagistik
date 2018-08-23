package de.visagistikmanager.view.customer;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.view.BaseListView;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class CustomerListView extends BaseListView<Customer> {

	private CustomerService service;

	@Getter
	private CustomerEditView editView;

	public CustomerListView(CustomerEditView editView, GridPane pane) {
		super(pane);
		this.editView = editView;
	}

	@Override
	public CustomerService getService() {
		if (this.service == null) {
			this.service = new CustomerService();
		}
		return service;
	}
}
