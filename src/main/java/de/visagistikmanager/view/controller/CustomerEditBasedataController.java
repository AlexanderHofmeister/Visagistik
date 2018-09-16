package de.visagistikmanager.view.controller;

import de.visagistikmanager.model.customer.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class CustomerEditBasedataController implements BaseEditController<Customer> {

	@FXML
	private TextField surname;

	@FXML
	private TextField forename;

	@FXML
	private DatePicker birthday;

	@FXML
	private TextField street;

	@FXML
	private TextField streetNumber;

	@FXML
	private TextField zip;

	@FXML
	private TextField city;

	@Override
	public void applyValuesToEntity(final Customer customer) {
		customer.setSurname(this.surname.getText());
		customer.setForename(this.forename.getText());
		customer.setBirthday(this.birthday.getValue());
		customer.setStreet(this.street.getText());
		customer.setStreetNumber(this.streetNumber.getText());
		customer.setZip(Integer.valueOf(this.zip.getText()));
		customer.setCity(this.city.getText());
	}

	@Override
	public void setValuesFromEntity(final Customer customer) {
		this.surname.setText(customer.getSurname());
		this.forename.setText(customer.getForename());
		this.birthday.setValue(customer.getBirthday());
		this.street.setText(customer.getStreet());
		this.streetNumber.setText(customer.getStreetNumber());
		this.zip.setText(String.valueOf(customer.getZip()));
		this.city.setText(customer.getCity());

	}

}