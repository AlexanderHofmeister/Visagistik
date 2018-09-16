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
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class CustomerEditProfileController implements BaseEditController<Customer>, Initializable {

	@FXML
	private CheckListView<String> skinFeatures;

	@FXML
	private CheckListView<String> sensitivities;

	@FXML
	private CheckListView<String> improvements;

	@FXML
	private CheckListView<String> currentSkincare;

	@FXML
	public ToggleSwitch blemishes;

	@FXML
	public TextField allergies;

	@Override
	public void applyValuesToEntity(final Customer customer) {
		customer.setBlemishes(this.blemishes.isSelected());
		customer.setAllergies(this.allergies.getText());
		customer.setSkinFeatures(new HashSet<>(this.skinFeatures.getCheckModel().getCheckedItems()));
		customer.setSensitivities(new HashSet<>(this.sensitivities.getCheckModel().getCheckedItems()));
		customer.setImprovments(new HashSet<>(this.improvements.getCheckModel().getCheckedItems()));
		customer.setCurrentSkinCare(new HashSet<>(this.currentSkincare.getCheckModel().getCheckedItems()));
	}

	@Override
	public void setValuesFromEntity(final Customer customer) {
		this.blemishes.setSelected(customer.isBlemishes());
		this.allergies.setText(customer.getAllergies());
		customer.getSkinFeatures().forEach(this.skinFeatures.getCheckModel()::check);
		customer.getSensitivities().forEach(this.sensitivities.getCheckModel()::check);
		customer.getImprovments().forEach(this.improvements.getCheckModel()::check);
		customer.getCurrentSkinCare().forEach(this.currentSkincare.getCheckModel()::check);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.skinFeatures.getItems().addAll(CustomerService.getSkinFeatures());
		this.sensitivities.getItems().addAll(CustomerService.getSensitives());
		this.improvements.getItems().addAll(CustomerService.getImprovements());
		this.currentSkincare.getItems().addAll(CustomerService.getCurrentSkincare());
	}

}