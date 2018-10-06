package de.visagistikmanager.view.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.order.Notification;
import de.visagistikmanager.model.order.NotificationType;
import de.visagistikmanager.model.order.Order;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class OrderEditNotificationsController implements Initializable, BaseEditController<Order> {

	@FXML
	private TableView<Notification> notifications;

	@FXML
	private ComboBox<NotificationType> notificationType;

	@FXML
	private DatePicker date;

	@FXML
	private Button addNotification;

	@FXML
	private TableColumn<Notification, NotificationType> typeColumn;

	@FXML
	private TableColumn<Notification, Boolean> doneColumn;

	@FXML
	private TableColumn<Notification, LocalDate> dateColumn;

	@FXML
	private TableColumn<Notification, Notification> notificationActionColumn;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.notificationType.setItems(FXCollections.observableArrayList(NotificationType.values()));

		this.date.setValue(LocalDate.now());

		this.addNotification.setOnAction(e -> {
			this.notifications.getItems().add(new Notification(this.notificationType.getValue(), this.date.getValue()));
		});

		this.typeColumn
				.setCellValueFactory(new PropertyValueFactory<Notification, NotificationType>("notificationType"));
		this.doneColumn.setCellValueFactory(new PropertyValueFactory<Notification, Boolean>("done"));
		this.dateColumn.setCellValueFactory(new PropertyValueFactory<Notification, LocalDate>("date"));

		this.notificationActionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.notificationActionColumn.setPrefWidth(100);
		this.notificationActionColumn.setCellFactory(param -> {
			return new TableCell<Notification, Notification>() {

				@Override
				protected void updateItem(final Notification entity, final boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						OrderEditNotificationsController.this.notifications.getItems().remove(entity);
					});
					setGraphic(new HBox(15, deleteButton));

				}
			};
		});

	}

	@Override
	public void applyValuesToEntity(final Order order) {
		final ObservableList<Notification> items = this.notifications.getItems();
		for (final Notification notification : items) {
			notification.setOrder(order);
		}
		order.setNotifications(items);

	}

	@Override
	public void setValuesFromEntity(final Order order) {
		this.notifications.getItems().setAll(order.getNotifications());
	}

}
