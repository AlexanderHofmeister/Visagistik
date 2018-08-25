package de.visagistikmanager.view.order;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.OrderService;
import de.visagistikmanager.view.BaseListView;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class OrderListView extends BaseListView<Order> {

	@Getter
	private OrderEditView editView;

	public OrderListView(OrderEditView editView, GridPane pane) {
		super(pane);
		this.editView = editView;
	}

	private OrderService service;

	@Override
	public OrderService getService() {
		if (this.service == null) {
			this.service = new OrderService();
		}
		return service;
	}

}
