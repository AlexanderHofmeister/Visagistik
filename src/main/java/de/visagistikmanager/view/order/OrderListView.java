package de.visagistikmanager.view.order;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.service.OrderService;
import de.visagistikmanager.view.BaseEditView;
import de.visagistikmanager.view.BaseListView;
import javafx.scene.layout.GridPane;

public class OrderListView extends BaseListView<Order> {

	private OrderEditView editView;

	public OrderListView(OrderEditView editView, GridPane pane) {
		super(pane);
		this.editView = editView;
	}

	private OrderService service;

	private CustomerService customerService = new CustomerService();

	@Override
	public OrderService getService() {
		if (this.service == null) {
			this.service = new OrderService();
		}
		return service;
	}

	@Override
	public BaseEditView<Order> getEditView() {
		editView.setCustomer(customerService.listAll());
		return editView;

	}

}
