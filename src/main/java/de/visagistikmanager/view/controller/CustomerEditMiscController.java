package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckListView;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustomerEditMiscController implements Initializable, BaseEditController<Customer> {

	@FXML
	private CheckListView<String> interests;

	@FXML
	private CheckListView<String> contactOptions;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.interests.getItems().addAll(CustomerService.getInterests());
		this.contactOptions.getItems().addAll(CustomerService.getContactOptions());
	}

	@Override
	public void setValuesFromEntity(final Customer customer) {
		customer.getInterests().forEach(this.interests.getCheckModel()::check);
		customer.getContactOptions().forEach(this.contactOptions.getCheckModel()::check);
	}

	@Override
	public void applyValuesToEntity(final Customer customer) {
		customer.setInterests(new HashSet<>(this.interests.getCheckModel().getCheckedItems()));
		customer.setContactOptions(new HashSet<>(this.contactOptions.getCheckModel().getCheckedItems()));
	}

}
