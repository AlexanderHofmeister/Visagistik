package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderViewController implements Initializable {

	private Order order;

	@FXML
	public Label receiptNumber;

	@FXML
	public Label customer;

	@FXML
	public Label state;

	@FXML
	public Label discount;

	@FXML
	public Label paymentState;

	@FXML
	public Label paymentType;

	@FXML
	public Label deliveryDate;

	@FXML
	public Label createdDate;

	public OrderViewController(final Order customer) {
		setOrder(customer);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
	}

	public void setOrder(final Order order) {
		this.order = order;
		this.receiptNumber.setText(String.valueOf(this.order.getReceiptNumber()));
		this.customer.setText(this.order.getCustomer() == null ? "" : this.order.getCustomer().getFullNameInverse());
		this.state.setText(this.order.getState().getLabel());
		this.discount.setText(this.order.getDiscountPercentage() + " %");
		this.paymentState.setText(this.order.getPaymentState().getLabel());
		this.deliveryDate.setText(this.order.getDeliveryDate().toString());
		this.createdDate.setText(this.order.getCreatedDate().toString());
	}

}
