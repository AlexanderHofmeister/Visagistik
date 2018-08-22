/**
 * 
 */
package de.visagistikmanager.model.order;

import de.visagistikmanager.model.LabeledEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderState implements LabeledEnum {

	OPEN("Offen"),

	MODIFIED("Bearbeitet"),

	COMPLETE("Versendet");

	@Getter
	private final String label;

}
