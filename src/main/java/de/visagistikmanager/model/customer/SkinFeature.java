package de.visagistikmanager.model.customer;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkinFeature implements LabeledEnum {

	DRY("Trockene Haut"),

	NORMAL("Normale Haut"),

	MIX("Mischhaut"),

	GREASY("Fettige Haut"),

	AGING("Fettige Haut");

	@Getter
	private final String label;

}
