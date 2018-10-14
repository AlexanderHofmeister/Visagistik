package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

public class CustomerTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Customer> customerTable;

	@FXML
	private TableColumn<Customer, String> surname;

	@FXML
	private TableColumn<Customer, String> forename;

	@FXML
	private TableColumn<Customer, String> adress;

	@FXML
	@Getter
	private TableColumn<Customer, Customer> action;

	private final CustomerService customerService = new CustomerService();

	@FXML
	private TextField filterField;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		final ObservableList<Customer> allCustomer = FXCollections.observableArrayList(this.customerService.listAll());

		final FilteredList<Customer> filteredData = new FilteredList<Customer>(allCustomer);

		// 2. Set the filter Predicate whenever the filter changes.
		this.filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(myObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name field in your object with filter.
				final String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(myObject.getSurname()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
					// Filter matches first name.

				} else if (String.valueOf(myObject.getForename()).toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}

				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		final SortedList<Customer> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(this.customerTable.comparatorProperty());
		// 5. Add sorted (and filtered) data to the table.
		this.customerTable.setItems(sortedData);

		this.surname.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
		this.forename.setCellValueFactory(new PropertyValueFactory<Customer, String>("forename"));
		this.adress.setCellValueFactory(new PropertyValueFactory<Customer, String>("adress"));
		this.customerTable.maxHeightProperty().bind(
				Bindings.size(this.customerTable.getItems()).multiply(this.customerTable.getFixedCellSize()).add(30));
	}

}
