package de.visagistikmanager.model.template;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemplateType implements LabeledEnum {

	BILL("Rechnung für Kunden"),

	RECEIPT("Quittung für Bestellungen"),

	BIRTHDAY("Geburtstagsbrief"),

	CARE_RECOMMENDATION("Pflegeempfehlung");

	@Getter
	private final String label;

	@Override
	public String toString() {
		return this.label;
	}

}
