package de.visagistikmanager.view.components;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class YesNoRadioButtonGroup extends VBox {

	private RadioButton yesRadioButton = new RadioButton("Ja");
	private RadioButton noRadioButton = new RadioButton("Nein");

	public YesNoRadioButtonGroup(String label) {

		final ToggleGroup toggleGroup = new ToggleGroup();
		yesRadioButton.setToggleGroup(toggleGroup);

		noRadioButton.setToggleGroup(toggleGroup);

		HBox radioButtonGroup = new HBox();
		radioButtonGroup.getChildren().addAll(yesRadioButton, noRadioButton);

		getChildren().add(new Label(label));
		getChildren().add(radioButtonGroup);
	}

	public void setValue(boolean value) {
		if (value) {
			yesRadioButton.setSelected(true);
		} else {
			noRadioButton.setSelected(true);
		}
	}

	public boolean getValue() {
		return yesRadioButton.isSelected();
	}

	public void setValue(String value) {
		setValue(Boolean.parseBoolean(value));
	}

}
