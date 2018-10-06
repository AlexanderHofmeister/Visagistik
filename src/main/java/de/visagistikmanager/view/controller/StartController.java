package de.visagistikmanager.view.controller;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.visagistikmanager.model.order.Notification;
import de.visagistikmanager.service.NotificationService;
import de.visagistikmanager.util.DateUtil;
import de.visagistikmanager.view.components.CalendarView;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartController implements Initializable {

	@FXML
	private GridPane calendarPane;

	private int selectedDay;

	private Month selectedMonth;

	private int selectedYear;

	private final NotificationService notificationService = new NotificationService();

	private Map<LocalDate, List<Notification>> allNotifications;

	@FXML
	private Label notificationDayLabel;

	@FXML
	private Label currentMonthYear;

	@FXML
	private VBox notifications;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.allNotifications = this.notificationService.findCurrentNotifications().stream()
				.collect(Collectors.groupingBy(Notification::getDate));

		final LocalDate now = LocalDate.now();
		this.selectedDay = now.getDayOfMonth();
		this.selectedMonth = now.getMonth();
		this.selectedYear = now.getYear();

		setCurrentMonthYearLabel(this.selectedMonth, this.selectedYear);

		buildCalendarView();

		final Optional<LocalDate> firstNotificationDayAfterToday = this.allNotifications.keySet().stream()
				.filter(item -> item.isAfter(now))
				.min(Comparator.comparingLong(item -> ChronoUnit.DAYS.between(now, item)));

		if (firstNotificationDayAfterToday.isPresent()) {
			this.notificationDayLabel.setText(DateUtil.formatDate(firstNotificationDayAfterToday.get()));
		}

	}

	private void buildCalendarView() {
		final CalendarView a = new CalendarView(buildCurrentDate());
		final DateFormatSymbols dfs = new DateFormatSymbols();
		final List<String> weekdays = new ArrayList<String>(Arrays.asList(dfs.getShortWeekdays()));
		weekdays.remove(0);
		weekdays.add(weekdays.remove(0));
		for (int i = 0; i < weekdays.size(); i++) {
			final String weekday = weekdays.get(i);
			final Label weekdayName = new Label(weekday);
			weekdayName.getStyleClass().add("calendarCell");
			weekdayName.setAlignment(Pos.CENTER);
			weekdayName.setMaxWidth(Double.MAX_VALUE);
			this.calendarPane.add(weekdayName, i, 0);
		}

		for (int i = 0; i < a.getDaysOfMonth().size(); i++) {
			final LocalDate date = a.getDaysOfMonth().get(i);

			final Label cell = new Label(date.format(DateTimeFormatter.ofPattern("d")));
			cell.getStyleClass().add("calendarCell");

			if (!date.getMonth().equals(this.selectedMonth)) {
				cell.getStyleClass().add("notCurrentMonthCell");
			} else {

				cell.setOnMouseClicked(mouseEvent -> {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						if (mouseEvent.getClickCount() == 2) {

							this.notifications.getChildren().clear();
							final Label source = (Label) mouseEvent.getSource();

							final LocalDate selectedDate = LocalDate.of(this.selectedYear, this.selectedMonth,
									Integer.parseInt(source.getText()));

							final List<Notification> list = this.allNotifications.get(selectedDate);

							if (list != null) {
								for (int j = 0; j < list.size(); j++) {
									final Notification notification = list.get(j);
									final HBox box = new HBox(10);

									box.getChildren().add(new Label(notification.getNotificationType().getLabel()));
									final CheckBox checkbox = new CheckBox();
									checkbox.setSelected(notification.isDone());

									checkbox.selectedProperty()
											.addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {

												if (newValue) {
													notification.setDone(true);
													this.notificationService.update(notification);
												}

											});

									box.getChildren().add(checkbox);

									this.notifications.getChildren().add(box);

								}
							}

							this.notificationDayLabel.setText(DateUtil.formatDate(selectedDate));
						}
					}

				});

			}

			cell.setOnMouseEntered(e -> {
				cell.getStyleClass().add("selectedDayCell");
			});
			cell.setOnMouseExited(e -> {
				cell.getStyleClass().remove("selectedDayCell");
			});

			this.allNotifications.keySet().forEach(d -> {
				if (date.equals(d)) {
					cell.getStyleClass().add("hasNotification");
				}
			});

			if (date.equals(LocalDate.now())) {
				cell.getStyleClass().add("todayDayCell");
			}

			cell.setAlignment(Pos.CENTER);
			cell.setMaxWidth(Double.MAX_VALUE);
			cell.setMaxHeight(Double.MAX_VALUE);

			this.calendarPane.add(cell, i % 7, i / 7 + 1);
		}
	}

	private LocalDate buildCurrentDate() {
		return LocalDate.of(this.selectedYear, this.selectedMonth, this.selectedDay);
	}

	private void setCurrentMonthYearLabel(final Month month, final int year) {
		this.currentMonthYear.setText(month.getDisplayName(TextStyle.FULL, Locale.GERMAN) + " " + year);
	}

	public void increaseCurrentMonth() {
		final Month[] allMonth = Month.values();
		if (this.selectedMonth.ordinal() == allMonth.length - 1) {
			this.selectedYear++;
		}
		this.selectedMonth = allMonth[(this.selectedMonth.ordinal() + 1) % allMonth.length];
		setCurrentMonthYearLabel(this.selectedMonth, this.selectedYear);
		this.notifications.getChildren().clear();
		this.calendarPane.getChildren().clear();
		this.selectedDay = 1;
		buildCalendarView();
	}

	public void decreaseCurrentMonth() {
		final Month[] allMonth = Month.values();
		if (this.selectedMonth.ordinal() == 0) {
			this.selectedYear--;
		}
		final int indexOfMonth = this.selectedMonth.ordinal() - 1;
		this.selectedMonth = allMonth[indexOfMonth < 0 ? allMonth.length - 1
				: this.selectedMonth.ordinal() - 1 % allMonth.length];
		setCurrentMonthYearLabel(this.selectedMonth, this.selectedYear);
		this.notifications.getChildren().clear();
		this.calendarPane.getChildren().clear();
		this.selectedDay = 1;
		buildCalendarView();
	}
}
