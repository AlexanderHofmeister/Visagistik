package de.visagistikmanager.view.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javafx.scene.control.TextFormatter;

public class CurrencyUtil {

	public static TextFormatter<String> getTextFormatterForBigDecimal() {
		final TextFormatter<String> formatter = new TextFormatter<String>(
				(UnaryOperator<TextFormatter.Change>) change -> {
					return Pattern.compile("\\d*|\\d+\\,\\d{0,2}").matcher(change.getControlNewText()).matches()
							? change
							: null;
				});
		return formatter;
	}

	public static String toEuro(final BigDecimal value) {
		return value.toString() + " €";
	}

	public static BigDecimal calculateDiscountValue(final BigDecimal value, final String discount) {
		return value.divide(new BigDecimal(100))
				.multiply(discount.isEmpty() ? BigDecimal.ZERO : new BigDecimal(discount))
				.setScale(2, RoundingMode.CEILING);
	}

	public static BigDecimal sumBigDecimal(final Stream<BigDecimal> values) {
		return values.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public static BigDecimal calculateTotal(final BigDecimal value, final BigDecimal discount) {
		return value.subtract(discount).setScale(2, RoundingMode.CEILING);
	}

}
