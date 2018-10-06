package de.visagistikmanager.view.controller;

import org.apache.commons.lang3.StringUtils;

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
		final String zip = this.zip.getText();
		if (StringUtils.isNotBlank(zip)) {
			customer.setZip(Integer.valueOf(zip));
		}
		customer.setCity(this.city.getText());
	}

	@Override
	public void setValuesFromEntity(final Customer customer) {
		this.surname.setText(customer.getSurname());
		this.forename.setText(customer.getForename());
		this.birthday.setValue(customer.getBirthday());
		this.street.setText(customer.getStreet());
		this.streetNumber.setText(customer.getStreetNumber());

		final Integer zip = customer.getZip();
		if (zip != null) {
			this.zip.setText(String.valueOf(zip));
		}
		this.city.setText(customer.getCity());

	}

}