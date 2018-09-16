package de.visagistikmanager.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
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

public class CustomerOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private Button createCustomerButton;

	private final CustomerService customerService = new CustomerService();

	public void createCustomer() throws IOException {
		buildCustomerEdit(new Customer());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		loadTable();
	}

	private void loadTable() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/customerTable.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final CustomerTableController controller = loader.getController();

		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(pane);

		final TableView<Customer> customerTable = controller.getCustomerTable();

		customerTable.setRowFactory(tv -> {
			final TableRow<Customer> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					final Customer selectedCustomer = row.getItem();
					buildCustomerView(selectedCustomer);
				}
			});
			return row;
		});

		final TableColumn<Customer, Customer> actionColumn = new TableColumn<>("Aktionen");
		actionColumn.setMinWidth(250);

		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		customerTable.getColumns().add(actionColumn);

		actionColumn.setCellFactory(param -> {
			return new TableCell<Customer, Customer>() {

				@Override
				protected void updateItem(final Customer entity, final boolean empty) {
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
						buildCustomerEdit(entity);
					});

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						CustomerOverviewController.this.customerService.delete(entity);
						loadTable();
					});
					setGraphic(new HBox(15, editButton, deleteButton));

				}
			};
		});
	}

	private void buildCustomerView(final Customer selectedCustomer) {
		final FXMLLoader customerViewLoader = new FXMLLoader();
		customerViewLoader.setLocation(getClass().getClassLoader().getResource("fxml/customerView.fxml"));
		Pane viewPane = null;
		try {
			viewPane = (Pane) customerViewLoader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final CustomerViewController customerViewController = customerViewLoader.getController();
		customerViewController.setCustomer(selectedCustomer);
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(viewPane);
	}

	private void buildCustomerEdit(final Customer customer) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/customerEdit.fxml"));
		Pane editPane = null;
		try {
			editPane = (Pane) loader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final CustomerEditController customerEditController = loader.getController();
		customerEditController.setCustomer(customer);
		customerEditController.getCancel().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			loadTable();
		});
		customerEditController.getSave().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			customerEditController.saveCustomer();
			loadTable();
			Notifications.create().darkStyle().text("Kunde erfolgreich gespeichert").hideAfter(Duration.seconds(5))
					.owner(this.entityPane).show();
		});
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(editPane);
	}

}
