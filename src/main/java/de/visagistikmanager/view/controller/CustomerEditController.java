package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.ToggleSwitch;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

public class CustomerEditController implements Initializable {

	@FXML
	private Label heading;

	@FXML
	public TextField surname;

	@FXML
	public TextField forename;

	@FXML
	public DatePicker birthday;

	@FXML
	public TextField street;

	@FXML
	public TextField streetNumber;

	@FXML
	public TextField zip;

	@FXML
	public TextField city;

	@FXML
	public ToggleSwitch blemishes;

	@FXML
	public ToggleSwitch allergies;

	private Customer customer;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	@FXML
	private CheckListView<String> skinFeatures;

	@FXML
	private CheckListView<String> sensitivities;

	@FXML
	private CheckListView<String> improvements;

	@FXML
	private CheckListView<String> currentSkincare;

	@FXML
	private CheckListView<String> interests;

	@FXML
	private CheckListView<String> contactOptions;

	private final CustomerService customerService = new CustomerService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.skinFeatures.getItems().addAll(CustomerService.getSkinFeatures());
		this.sensitivities.getItems().addAll(CustomerService.getSensitives());
		this.improvements.getItems().addAll(CustomerService.getImprovements());
		this.currentSkincare.getItems().addAll(CustomerService.getCurrentSkincare());
		this.interests.getItems().addAll(CustomerService.getInterests());
		this.contactOptions.getItems().addAll(CustomerService.getContactOptions());
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
		this.heading.setText(customer.isNew() ? "Kunde anlegen"
				: "Kunde " + customer.getForename() + " " + customer.getSurname() + " bearbeiten");
		this.surname.setText(customer.getSurname());
		this.forename.setText(customer.getForename());
		this.birthday.setValue(customer.getBirthday());
		this.street.setText(customer.getStreet());
		this.streetNumber.setText(customer.getStreetNumber());
		this.zip.setText(String.valueOf(customer.getZip()));
		this.city.setText(customer.getCity());
		this.blemishes.setSelected(customer.isBlemishes());
		this.allergies.setSelected(customer.isAllergies());
		customer.getSkinFeatures().forEach(this.skinFeatures.getCheckModel()::check);
		customer.getSensitivities().forEach(this.sensitivities.getCheckModel()::check);
		customer.getImprovments().forEach(this.improvements.getCheckModel()::check);
		customer.getCurrentSkinCare().forEach(this.currentSkincare.getCheckModel()::check);
		customer.getInterests().forEach(this.interests.getCheckModel()::check);
		customer.getContactOptions().forEach(this.contactOptions.getCheckModel()::check);

	}

	public void saveCustomer() {
		this.customer.setSurname(this.surname.getText());
		this.customer.setForename(this.forename.getText());
		this.customer.setBirthday(this.birthday.getValue());
		this.customer.setStreet(this.street.getText());
		this.customer.setStreetNumber(this.streetNumber.getText());
		this.customer.setZip(Integer.valueOf(this.zip.getText()));
		this.customer.setCity(this.city.getText());
		this.customer.setBlemishes(this.blemishes.isSelected());
		this.customer.setAllergies(this.allergies.isSelected());
		this.customer.setSkinFeatures(new HashSet<>(this.skinFeatures.getCheckModel().getCheckedItems()));
		this.customer.setSensitivities(new HashSet<>(this.sensitivities.getCheckModel().getCheckedItems()));
		this.customer.setImprovments(new HashSet<>(this.improvements.getCheckModel().getCheckedItems()));
		this.customer.setCurrentSkinCare(new HashSet<>(this.currentSkincare.getCheckModel().getCheckedItems()));
		this.customer.setInterests(new HashSet<>(this.interests.getCheckModel().getCheckedItems()));
		this.customer.setContactOptions(new HashSet<>(this.contactOptions.getCheckModel().getCheckedItems()));
		this.customerService.update(this.customer);
	}

}
