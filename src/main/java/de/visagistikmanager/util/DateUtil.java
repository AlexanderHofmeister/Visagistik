package de.visagistikmanager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	public static String formatDate(final LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	public static String formatDateForFilename(final LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

}
