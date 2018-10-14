package de.visagistikmanager.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.logbook.Logbook;
import de.visagistikmanager.service.LogbookService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LogbookOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private Button createLogbookButton;

	private final LogbookService logbookService = new LogbookService();

	public void createLogbook() throws IOException {
		buildLogbookEdit(new Logbook());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		loadTable();
	}

	private void loadTable() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/logbookTable.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final LogbookTableController controller = loader.getController();

		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(pane);

		final TableView<Logbook> customerTable = controller.getLogbookTable();

		customerTable.setRowFactory(tv -> {
			final TableRow<Logbook> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					buildLogbookEdit(row.getItem());
				}
			});
			return row;
		});

		final TableColumn<Logbook, Logbook> actionColumn = controller.getAction();
		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		actionColumn.setCellFactory(param -> {
			return new TableCell<Logbook, Logbook>() {

				@Override
				protected void updateItem(final Logbook entity, final boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}
					final Button editButton = new Button();
					final FontAwesomeIcon editIcon = new FontAwesomeIcon();
					editIcon.setIcon(FontAwesomeIconName.EDIT);
					editButton.setGraphic(editIcon);
					editButton.getStyleClass().add("button");
					editButton.setOnAction(event -> {
						buildLogbookEdit(entity);
					});

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						LogbookOverviewController.this.logbookService.delete(entity);
						loadTable();
					});

					setGraphic(new HBox(15, editButton, deleteButton));

				}
			};
		});
	}

	private void buildLogbookEdit(final Logbook logbookl) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/logbookEdit.fxml"));
		Pane editPane = null;
		try {
			editPane = (Pane) loader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final LogbookEditController logbookEditController = loader.getController();
		logbookEditController.setLogbook(logbookl);
		logbookEditController.getCancel().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			loadTable();
		});
		logbookEditController.getSave().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			logbookEditController.saveLookbook();
			loadTable();
			Notifications.create().darkStyle().text("Fahrtenbuch erfolgreich gespeichert")
					.hideAfter(Duration.seconds(5)).owner(this.entityPane).show();
		});
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(editPane);
	}

}
