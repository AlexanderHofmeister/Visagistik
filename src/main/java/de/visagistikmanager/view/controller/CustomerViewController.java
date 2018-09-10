package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import de.visagistikmanager.model.customer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerViewController implements Initializable {

	private Customer customer;

	@FXML
	public Label name;

	@FXML
	public Label birthday;

	@FXML
	public Label adress;

	@FXML
	public Label blemishes;

	@FXML
	public Label allergies;

	@FXML
	public Label skinFeatures;

	@FXML
	public Label sensitivities;

	@FXML
	public Label improvements;

	@FXML
	public Label currentSkinCare;

	@FXML
	public Label contactOptions;

	@FXML
	public Label interests;

	public CustomerViewController(final Customer customer) {
		setCustomer(customer);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
		this.name.setText(this.customer.getFullNameInverse());
		this.birthday.setText(this.customer.getBirthday().toString());
		this.blemishes.setText(this.customer.isBlemishes() ? "Ja" : "Nein");
		this.allergies.setText(this.customer.isAllergies() ? "Ja" : "Nein");
		this.skinFeatures.setText(String.join("\n", this.customer.getSkinFeatures()));
		this.sensitivities.setText(String.join("\n", this.customer.getSensitivities()));
		this.improvements.setText(String.join("\n", this.customer.getImprovments()));
		this.currentSkinCare.setText(String.join("\n", this.customer.getCurrentSkinCare()));
		this.contactOptions.setText(String.join("\n", this.customer.getContactOptions()));
		this.interests.setText(StringUtils.defaultIfBlank(String.join("\n", this.customer.getInterests()), "-"));
	}

}
