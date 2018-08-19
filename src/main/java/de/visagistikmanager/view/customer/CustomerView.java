package de.visagistikmanager.view.customer;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class CustomerView extends GridPane {

	@Getter
	private final TableView<Customer> customerTable = new TableView<>();

	private CustomerService customerService = new CustomerService();

	private CustomerEditView customerEditView = new CustomerEditView();

	public CustomerView() {
		Button newCustomerButton = new Button("Kunde anlegen");
		newCustomerButton.setOnAction(e -> {
			final Customer model = new Customer();
			this.customerEditView.setModel(model);
			getChildren().remove(customerEditView);
			add(customerEditView, 1, 1, 1, 2);
		});

		customerEditView.setSaveAction(e -> {
			this.customerEditView.applyValuesToModel();

			Customer customer = customerService.update(customerEditView.getModel());
			ObservableList<Customer> items = customerTable.getItems();
			if (!items.contains(customer)) {
				items.add(customer);
			}
			customerTable.refresh();
			getChildren().remove(customerEditView);
		});

		customerEditView.setCancelAction(e -> {
			getChildren().remove(customerEditView);
		});

		add(newCustomerButton, 0, 0);

		final TableColumn<Customer, String> surenameColumn = new TableColumn<>("Nachname");
		surenameColumn.setMinWidth(100);
		surenameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));

		final TableColumn<Customer, String> forenameColumn = new TableColumn<>("Vorname");
		forenameColumn.setMinWidth(100);
		forenameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("forename"));

		final TableColumn<Customer, String> birthdayColumn = new TableColumn<>("Geburtstag");
		birthdayColumn.setMinWidth(100);
		birthdayColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("birthday"));

		final TableColumn<Customer, String> addressColumn = new TableColumn<>("Adresse");
		addressColumn.setMinWidth(100);
		addressColumn.setCellValueFactory(tableCell -> new ReadOnlyObjectWrapper<>(tableCell.getValue().getAdress()));

		TableColumn<Customer, Customer> actionColumn = new TableColumn<>("Aktionen");
		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		actionColumn.setCellFactory(param -> new TableCell<Customer, Customer>() {
			private final Button editButton = new Button("Editieren");

			@Override
			protected void updateItem(Customer customer, boolean empty) {
				super.updateItem(customer, empty);

				if (customer == null) {
					setGraphic(null);
					return;
				}

				setGraphic(editButton);
				editButton.setOnAction(event -> {
					customerEditView.setModel(customer);
					this.getChildren().remove(customerEditView);
					add(customerEditView, 1, 1, 1, 2);
				});
			}
		});

		this.customerTable.getColumns().add(surenameColumn);
		this.customerTable.getColumns().add(forenameColumn);
		this.customerTable.getColumns().add(addressColumn);
		this.customerTable.getColumns().add(birthdayColumn);
		this.customerTable.getColumns().add(actionColumn);
		add(customerTable, 0, 1);

		this.customerTable.setItems(FXCollections.observableArrayList(customerService.listAll()));
	}

}
