package de.visagistikmanager.view.controller;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;

public class CustomerEditController {

	@FXML
	private CustomerEditBasedataController basedataController;

	@FXML
	private CustomerEditProfileController profileController;

	@FXML
	private CustomerEditMiscController miscController;

	@FXML
	private Label heading;

	private Customer customer;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	private final CustomerService customerService = new CustomerService();

	public void setCustomer(final Customer customer) {
		this.customer = customer;
		this.heading.setText(customer.isNew() ? "Kunde anlegen"
				: "Kunde " + customer.getForename() + " " + customer.getSurname() + " bearbeiten");
		this.basedataController.setValuesFromEntity(customer);
		this.profileController.setValuesFromEntity(customer);
		this.miscController.setValuesFromEntity(customer);

	}

	public void saveCustomer() {
		this.basedataController.applyValuesToEntity(this.customer);
		this.profileController.applyValuesToEntity(this.customer);
		this.miscController.applyValuesToEntity(this.customer);
		this.customerService.update(this.customer);
	}

}
