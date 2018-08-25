package de.visagistikmanager.model.order;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentType implements LabeledEnum {

	CASH("Bar"),

	TRANSFER("Überweisung"),

	PAYPAL("PayPal");

	@Getter
	private final String label;

	@Override
	public String toString() {
		return this.label;
	}

}
