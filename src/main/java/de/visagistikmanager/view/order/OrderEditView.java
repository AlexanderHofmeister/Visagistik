package de.visagistikmanager.view.order;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.view.BaseEditView;
import de.visagistikmanager.view.components.AutoCompleteTextField;
import lombok.Setter;

public class OrderEditView extends BaseEditView<Order> {

	@Setter
	private List<Customer> customer = new ArrayList<>();
	private AutoCompleteTextField inputCustomer;

	private CustomerService customerService = new CustomerService();

	public void fillAutoCompleteCustomer(List<Customer> customer) {
		this.customer = customer;
		inputCustomer.getEntries().addAll(
				new TreeSet<>(this.customer.stream().map(Customer::getFullNameInverse).collect(Collectors.toList())));
	}

	public OrderEditView() {
		this.inputCustomer = new AutoCompleteTextField(new TreeSet<>());
		customer = customerService.listAll();
		fillAutoCompleteCustomer(customer);
		this.inputCustomer.setPromptText("Kunde");
		add(this.inputCustomer, 0, 0);

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
		fillAutoCompleteCustomer(customer);
		Customer customer = model.getCustomer();
		if (customer != null) {
			this.inputCustomer.setText(customer.getFullNameInverse());
		}
	}

}
