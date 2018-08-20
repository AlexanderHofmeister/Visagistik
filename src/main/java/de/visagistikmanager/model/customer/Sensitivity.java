package de.visagistikmanager.model.customer;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sensitivity implements LabeledEnum {

	NO_SENSIVITY("Nicht empfindlich"),

	SLIGHTLY_SENSIVITY("Etwas empfindlich"),

	VERY_SENSIVITY("Sehr empfindlich");

	private final String label;

}
