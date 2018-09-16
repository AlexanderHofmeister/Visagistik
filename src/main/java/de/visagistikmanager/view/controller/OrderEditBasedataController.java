package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.OrderState;
import de.visagistikmanager.service.CustomerService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class OrderEditBasedataController implements BaseEditController<Order>, Initializable {

	@FXML
	public TextField receiptNumber;

	@FXML
	public TextField customer;

	@FXML
	public DatePicker createdDate;

	@FXML
	public ComboBox<OrderState> state;

	private final CustomerService customerService = new CustomerService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.state.setItems(FXCollections.observableArrayList(OrderState.values()));
		TextFields.bindAutoCompletion(this.customer,
				this.customerService.listAll().stream().map(Customer::getFullNameInverse).collect(Collectors.toList()));

	}

	@Override
	public void applyValuesToEntity(final Order order) {
		this.receiptNumber.setText(String.valueOf(order.getReceiptNumber()));
		this.customer.setText(order.getCustomer() == null ? "" : order.getCustomer().getFullNameInverse());
		this.createdDate.setValue(order.getCreatedDate());
		this.state.setValue(order.getState());

	}

	@Override
	public void setValuesFromEntity(final Order order) {
		order.setReceiptNumber(
				this.receiptNumber.getText().isEmpty() ? null : Integer.valueOf(this.receiptNumber.getText()));
		final String[] customerInputText = this.customer.getText().split(",");
		if (customerInputText.length == 2) {
			order.setCustomer(this.customerService.findBySurnameAndforename(customerInputText[0].trim(),
					customerInputText[1].trim()));
		}
		order.setCreatedDate(this.createdDate.getValue());
		order.setState(this.state.getValue());
	}

}
