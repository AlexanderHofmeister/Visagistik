package de.visagistikmanager.view.components;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;

public class ComponentUtil {

	public static TextFormatter<String> getTextFormatterForBigDecimal() {
		final TextFormatter<String> formatter = new TextFormatter<String>(
				(UnaryOperator<TextFormatter.Change>) change -> {
					return Pattern.compile("\\d*|\\d+\\,\\d{0,2}").matcher(change.getControlNewText()).matches()
							? change
							: null;
				});
		return formatter;
	}

}
