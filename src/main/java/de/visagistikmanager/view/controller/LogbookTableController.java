package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.logbook.Logbook;
import de.visagistikmanager.service.LogbookService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

public class LogbookTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Logbook> logbookTable;

	@FXML
	@Setter
	private Pane logbookTablePane;

	@FXML
	private TableColumn<Logbook, Integer> year;

	@FXML
	private TableColumn<Logbook, String> month;

	@FXML
	@Getter
	private TableColumn<Logbook, Logbook> action;

	private final LogbookService logbookService = new LogbookService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final ObservableList<Logbook> logbooks = FXCollections.observableArrayList(this.logbookService.listAll());
		this.logbookTable.setItems(logbooks);

		this.month.setCellValueFactory(new PropertyValueFactory<Logbook, String>("month"));
		this.year.setCellValueFactory(new PropertyValueFactory<Logbook, Integer>("year"));
		this.logbookTable.maxHeightProperty().bind(
				Bindings.size(this.logbookTable.getItems()).multiply(this.logbookTable.getFixedCellSize()).add(30));
	}

}
