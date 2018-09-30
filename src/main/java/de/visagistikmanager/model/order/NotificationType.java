package de.visagistikmanager.model.order;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType implements LabeledEnum {

	CUSTOMERSERVICE("Kundenservice"),

	REORDER("Nachbestellung");

	@Getter
	private final String label;

	@Override
	public String toString() {
		return this.label;
	}
}
