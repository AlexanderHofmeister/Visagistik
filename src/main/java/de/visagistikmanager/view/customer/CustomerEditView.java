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
	private Button saveCustomerButton = new Button("Speichern");

	public void setModel(final Customer model) {
		this.model = model;
		this.inputSurename.setText(model.getSurname());
		this.inputForename.setText(model.getForename());
		this.inputBirthday.setValue(model.getBirthday());
	}

	@Getter
	private Customer model;

	public void applyValuesToModel() {
		this.model.setSurname(this.inputSurename.getText());
		this.model.setForename(this.inputForename.getText());
		this.model.setBirthday(this.inputBirthday.getValue());
	}

	public CustomerEditView() {
		inputSurename.setPromptText("Nachname");
		inputForename.setPromptText("Vorname");
		inputBirthday.setPromptText("Geburtstag");
		add(inputSurename, 0, 0);
		add(inputForename, 1, 0);
		add(inputBirthday, 0, 1, 2, 1);
		add(saveCustomerButton, 0, 2);
	}

	public void setSaveAction(EventHandler<ActionEvent> event) {
		this.saveCustomerButton.setOnAction(event);
	}

}
