package de.visagistikmanager.view.order;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.view.BaseEditView;
import javafx.scene.control.Label;

public class OrderEditView extends BaseEditView<Order> {

	private Customer customer;

	public OrderEditView(Customer customer) {
		this.customer = customer;
		add(new Label(this.customer.getFullNameInverse()), 0, 0);
	}
}
	