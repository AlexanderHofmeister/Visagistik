package de.visagistikmanager.view.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.view.BaseEditView;
import javafx.scene.control.TextField;
import lombok.Setter;

public class OrderEditView extends BaseEditView<Order> {

	@Setter
	private List<Customer> customer = new ArrayList<>();
	private TextField inputCustomer;

	private CustomerService customerService = new CustomerService();

	public OrderEditView() {

		this.inputCustomer = new TextField();
		this.inputCustomer.setPromptText("Kunde");
		add(this.inputCustomer, 0, 0);

		TextFields.bindAutoCompletion(this.inputCustomer, t -> {
			List<String> collect = this.customer.stream().filter(elem -> {
				return elem.getSurname().toLowerCase().contains(t.getUserText().toLowerCase())
						|| elem.getForename().toLowerCase().contains(t.getUserText().toLowerCase());
			}).map(Customer::getFullNameInverse).collect(Collectors.toList());
			return collect;
		});

	}

	@Override
	public Order applyCustomValuesToModel(Order model) {

		if (inputCustomer.getText().isEmpty()) {
			return model;
		}

		String[] split = this.inputCustomer.getText().split(",");
		model.setCustomer(this.customerService.findBySurnameAndforename(split[0].trim(), split[1].trim()));
		return model;
	}

	@Override
	protected void setCustomModel(Order model) {
		Customer customer = model.getCustomer();
		if (customer != null) {
			this.inputCustomer.setText(customer.getFullNameInverse());
		}
	}

}
