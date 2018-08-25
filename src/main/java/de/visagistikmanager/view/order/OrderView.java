package de.visagistikmanager.view.order;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.OrderService;
import de.visagistikmanager.view.BaseEntityView;
import lombok.Getter;

public class OrderView extends BaseEntityView<Order> {

	@Getter
	private OrderService service;

	private OrderEditView editView;

	private OrderListView listView;

	@Override
	protected OrderEditView getEditView() {
		if (editView == null) {
			editView = new OrderEditView();
		}
		return editView;
	}

	@Override
	protected OrderListView getListView() {
		if (listView == null) {
			service = new OrderService();
			listView = new OrderListView(getEditView(), panel);
		}
		return listView;
	}

}
