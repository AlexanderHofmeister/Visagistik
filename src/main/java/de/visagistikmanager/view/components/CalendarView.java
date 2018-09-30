package de.visagistikmanager.view.components;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class CalendarView {

	private final List<LocalDate> daysOfMonth;

	private static final int TOTAL_DAY_DISPLAY_COUNT = 42;

	public CalendarView(final LocalDate today) {

		final LocalDate firstOfMonth = today.withDayOfMonth(1);

		final LocalDate lastOfMonth = today.withDayOfMonth(today.lengthOfMonth());
		this.daysOfMonth = Stream.iterate(firstOfMonth, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(firstOfMonth, lastOfMonth) + 1).collect(Collectors.toList());

		LocalDate temp = firstOfMonth;
		while (!temp.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
			temp = temp.minusDays(1);
			this.daysOfMonth.add(temp);
		}

		this.daysOfMonth.addAll(Stream.iterate(lastOfMonth.plusDays(1), date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(lastOfMonth,
						lastOfMonth.plusDays(TOTAL_DAY_DISPLAY_COUNT - this.daysOfMonth.size())))
				.collect(Collectors.toList()));

		Collections.sort(this.daysOfMonth);

	}

}