package de.visagistikmanager.view.customer;

import de.visagistikmanager.model.Customer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class CustomerEditView extends GridPane {

	private TextField inputSurename = new TextField();
	private TextField inputForename = new TextField();
	private DatePicker inputBirthday = new DatePicker();

	private TextField inputStreet = new TextField();
	private TextField inputStreetNumber = new TextField();
	private TextField inputZip = new TextField();
	private TextField inputCity = new TextField();

	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");

	public void setModel(final Customer model) {
		this.model = model;
		this.inputSurename.setText(model.getSurname());
		this.inputForename.setText(model.getForename());
		this.inputBirthday.setValue(model.getBirthday());
		this.inputStreet.setText(model.getStreet());
		this.inputStreetNumber.setText(String.valueOf(model.getStreetNumber() == null ? "" : model.getStreetNumber()));
		this.inputZip.setText(String.valueOf(model.getZip() == null ? "" : model.getZip()));
		this.inputCity.setText((model.getCity()));
	}

	@Getter
	private Customer model;

	public void applyValuesToModel() {
		this.model.setSurname(this.inputSurename.getText());
		this.model.setForename(this.inputForename.getText());
		this.model.setBirthday(this.inputBirthday.getValue());
		this.model.setStreet(this.inputStreet.getText());
		this.model.setStreetNumber(Integer.valueOf(this.inputStreetNumber.getText()));
		this.model.setZip(Integer.valueOf(this.inputZip.getText()));
		this.model.setCity(this.inputCity.getText());
	}

	public CustomerEditView() {
		inputSurename.setPromptText("Nachname");
		inputForename.setPromptText("Vorname");
		inputBirthday.setPromptText("Geburtstag");
		inputStreet.setPromptText("Straﬂe");
		inputStreetNumber.setPromptText("Hausnummer");
		inputZip.setPromptText("PLZ");
		inputCity.setPromptText("Stadt");

		add(inputSurename, 0, 0);
		add(inputForename, 1, 0);

		add(inputBirthday, 0, 1, 2, 1);

		add(inputStreet, 0, 2);
		add(inputStreetNumber, 1, 2);

		add(inputZip, 0, 3);
		add(inputCity, 1, 3);

		add(saveButton, 0, 4, 2, 1);
		add(cancelButton, 1, 4, 2, 1);
	}

	public void setSaveAction(EventHandler<ActionEvent> event) {
		this.saveButton.setOnAction(event);
	}

	public void setCancelAction(EventHandler<ActionEvent> event) {
		this.cancelButton.setOnAction(event);
	}

}
