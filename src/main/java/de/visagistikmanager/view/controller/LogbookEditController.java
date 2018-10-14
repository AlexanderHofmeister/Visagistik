package de.visagistikmanager.view.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import de.visagistikmanager.model.logbook.LogEntry;
import de.visagistikmanager.model.logbook.Logbook;
import de.visagistikmanager.service.LogbookService;
import de.visagistikmanager.view.components.TimeSpinner;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

public class LogbookEditController implements Initializable {

	@FXML
	private Label heading;

	private Logbook logbook;

	@FXML
	private ComboBox<Integer> year;

	@FXML
	private ComboBox<Month> month;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	@FXML
	private TableView<LogEntry> logentries;

	@FXML
	private TextField day;

	@FXML
	private TimeSpinner departure;

	@FXML
	private TimeSpinner arrival;

	@FXML
	private TextField km;

	@FXML
	private TextField start;

	@FXML
	private TextField destination;

	@FXML
	private TextField purpose;

	@FXML
	private TextField customer;

	@FXML
	private TableColumn<LogEntry, Integer> dayColumn;

	@FXML
	private TableColumn<LogEntry, LocalTime> departureColumn;

	@FXML
	private TableColumn<LogEntry, LocalTime> arrivalColumn;

	@FXML
	private TableColumn<LogEntry, Double> kmColumn;

	@FXML
	private TableColumn<LogEntry, String> startColumn;

	@FXML
	private TableColumn<LogEntry, String> destinationColumn;

	@FXML
	private TableColumn<LogEntry, String> purposeColumn;

	@FXML
	private TableColumn<LogEntry, String> customerColumn;

	public void addLogEntry() {
		final LogEntry logEntry = new LogEntry();
		logEntry.setDay(Integer.parseInt(this.day.getText()));
		logEntry.setDeparture(this.departure.getValue());
		logEntry.setArrival(this.arrival.getValue());
		logEntry.setKm(Double.parseDouble(this.km.getText()));
		logEntry.setStart(this.start.getText());
		logEntry.setDestination(this.destination.getText());
		logEntry.setPurpose(this.purpose.getText());
		logEntry.setCustomer(this.customer.getText());
		this.logentries.getItems().add(logEntry);
	}

	private final LogbookService logbookService = new LogbookService();

	public void setLogbook(final Logbook logbook) {
		this.logentries.setItems(FXCollections.observableArrayList(logbook.getEntries()));
		this.logbook = logbook;
		this.year.setValue(logbook.getYear());
		final String currentMonth = logbook.getMonth();

		if (StringUtils.isNotBlank(currentMonth)) {
			this.month.setValue(Stream.of(Month.values()).filter(month -> {
				return month.getDisplayName(TextStyle.FULL, Locale.GERMAN).equals(currentMonth);
			}).findFirst().get());
		}
		this.heading.setText(logbook.isNew() ? "Fahrtenbuch anlegen"
				: "Fahrtenbuch für " + this.logbook.getMonth() + " / " + this.logbook.getYear() + " bearbeiten");
	}

	public void saveLookbook() {
		this.logbook.setMonth(this.month.getValue().getDisplayName(TextStyle.FULL, Locale.GERMAN));
		this.logbook.setYear(this.year.getValue());
		this.logbook.setEntries(this.logentries.getItems());
		this.logbookService.update(this.logbook);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.dayColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, Integer>("day"));
		this.departureColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, LocalTime>("departure"));
		this.arrivalColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, LocalTime>("arrival"));
		this.kmColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, Double>("km"));
		this.startColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("start"));
		this.destinationColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("destination"));
		this.purposeColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("purpose"));
		this.customerColumn.setCellValueFactory(new PropertyValueFactory<LogEntry, String>("customer"));

		this.month.setItems(FXCollections.observableArrayList(Month.values()));
		final int currentYear = LocalDate.now().getYear();
		this.year.setItems(FXCollections.observableArrayList(Arrays.asList(currentYear, currentYear + 1)));
	}

}
