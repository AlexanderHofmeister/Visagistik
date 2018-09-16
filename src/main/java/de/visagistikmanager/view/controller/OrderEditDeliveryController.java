package de.visagistikmanager.view.controller;

import de.visagistikmanager.model.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class OrderEditDeliveryController implements BaseEditController<Order> {

	@FXML
	public DatePicker deliveryDate;

	@Override
	public void applyValuesToEntity(final Order order) {
		order.setDeliveryDate(this.deliveryDate.getValue());

	}

	@Override
	public void setValuesFromEntity(final Order order) {
		this.deliveryDate.setValue(order.getDeliveryDate());
	}

}
