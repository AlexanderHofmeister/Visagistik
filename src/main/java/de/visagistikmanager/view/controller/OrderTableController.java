package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.OrderService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private TableColumn<Order, String> state;

	@FXML
	private TableColumn<Order, String> paymentState;

	private final OrderService orderService = new OrderService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.receiptNumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("receiptNumber"));
		this.customer.setCellValueFactory(tableCell -> {
			if (tableCell.getValue().getCustomer() == null) {
				return new SimpleStringProperty("");
			}
			return new SimpleStringProperty(tableCell.getValue().getCustomer().getFullNameInverse());
		});

		this.state.setCellValueFactory(new PropertyValueFactory<Order, String>("state"));
		this.paymentState.setCellValueFactory(new PropertyValueFactory<Order, String>("paymentState"));
		this.orderTable.maxHeightProperty()
				.bind(Bindings.size(this.orderTable.getItems()).multiply(this.orderTable.getFixedCellSize()).add(30));
		this.orderTable.getItems().addAll(this.orderService.listAll());
	}

}
