package de.visagistikmanager.view.controller;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;

public class OrderEditController {

	@FXML
	private OrderEditBasedataController basedataController;

	@FXML
	private OrderEditProductsController productsController;

	@FXML
	private OrderEditPaymentsController paymentsController;

	@FXML
	private OrderEditDeliveryController deliveryController;

	private Order order;

	@FXML
	private Label heading;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	private final OrderService orderService = new OrderService();

	public void setOrder(final Order order) {
		this.order = order;
		this.heading.setText(
				order.isNew() ? "Bestellung anlegen" : "Bestellung " + order.getReceiptNumber() + "  bearbeiten");
		this.basedataController.setValuesFromEntity(order);
		this.productsController.setValuesFromEntity(order);
		this.paymentsController.setValuesFromEntity(order);
		this.deliveryController.setValuesFromEntity(order);

	}

	public void saveOrder() {
		this.basedataController.applyValuesToEntity(this.order);
		this.productsController.applyValuesToEntity(this.order);
		this.paymentsController.applyValuesToEntity(this.order);
		this.deliveryController.applyValuesToEntity(this.order);
		this.orderService.update(this.order);
	}

}
