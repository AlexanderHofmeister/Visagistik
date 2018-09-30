package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.OrderState;
import de.visagistikmanager.service.OrderService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

public class OrderTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Order> orderTable;

	@FXML
	@Setter
	private Pane customerTablePane;

	@FXML
	private TableColumn<Order, Integer> receiptNumber;

	@FXML
	private TableColumn<Order, String> customer;

	@FXML
	private TableColumn<Order, OrderState> state;

	@FXML
	private TableColumn<Order, String> paymentState;

	@FXML
	@Getter
	private TableColumn<Order, Order> action;

	private final OrderService orderService = new OrderService();

	@FXML
	private TextField filterField;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		final ObservableList<Order> allOrder = FXCollections.observableArrayList(this.orderService.listAll());

		final FilteredList<Order> filteredData = new FilteredList<Order>(allOrder);

		// 2. Set the filter Predicate whenever the filter changes.
		this.filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name field in your object with filter.
				final String lowerCaseFilter = newValue.toLowerCase();

				if (order.getCustomer() != null) {
					if (order.getCustomer().getForename().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}

					if (order.getCustomer().getSurname().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}

				}
				if (order.getReceiptNumber().toString().contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		final SortedList<Order> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(this.orderTable.comparatorProperty());
		// 5. Add sorted (and filtered) data to the table.
		this.orderTable.setItems(sortedData);

		this.receiptNumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("receiptNumber"));
		this.customer.setCellValueFactory(tableCell -> {
			if (tableCell.getValue().getCustomer() == null) {
				return new SimpleStringProperty("");
			}
			return new SimpleStringProperty(tableCell.getValue().getCustomer().getFullNameInverse());
		});

		this.state.setCellValueFactory(new PropertyValueFactory<Order, OrderState>("state"));
		this.paymentState.setCellValueFactory(new PropertyValueFactory<Order, String>("paymentState"));
		this.orderTable.maxHeightProperty()
				.bind(Bindings.size(this.orderTable.getItems()).multiply(this.orderTable.getFixedCellSize()).add(30));
	}

}
