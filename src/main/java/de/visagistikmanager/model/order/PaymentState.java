package de.visagistikmanager.model.order;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentState implements LabeledEnum {

	COMPLETE("Vollständig bezahlt"),

	PARTIALLY("Teilweise bezahlt"),

	NONE("Nicht bezahlt");

	@Getter
	private final String label;

	@Override
	public String toString() {
		return this.label;
	}

}
