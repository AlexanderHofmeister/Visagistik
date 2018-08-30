package de.visagistikmanager.view.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.service.ProductService;
import de.visagistikmanager.view.BaseEditView;
import de.visagistikmanager.view.components.AutoCompleteTextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

public class OrderEditView extends BaseEditView<Order> {

	@Getter
	private class ProductRow {

		private final AutoCompleteTextField product;
		private final Spinner<Integer> count = new Spinner<>();
		private final Button removeButton = new Button("-");

		public ProductRow(final List<Product> products) {
			this.product = new AutoCompleteTextField(new TreeSet<>());
			this.product.setPromptText("Produktname");
			final SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
					1, 99, 0);
			this.count.setValueFactory(valueFactory);
			this.removeButton.getStyleClass().add("danger");
		}

		public void fillAutoComplete(final List<Product> products) {
			this.product.getEntries().addAll(new TreeSet<>(products.stream()
					.map(Product::getName).collect(Collectors.toSet())));
		}
	}

	@Setter
	private List<Customer> customer = new ArrayList<>();
	private List<Product> products = new ArrayList<>();

	private final AutoCompleteTextField inputCustomer;

	private final CustomerService customerService = new CustomerService();
	private final ProductService productService = new ProductService();

	private final List<ProductRow> productRows = new ArrayList<>();
	private final GridPane productGrid = new GridPane();

	private final GridPane productListGrid = new GridPane();

	private final Label subtotal = new Label();

	public void fillAutoCompleteCustomer(final List<Customer> customer) {
		this.customer = customer;
		this.inputCustomer.getEntries()
				.addAll(new TreeSet<>(
						this.customer.stream().map(Customer::getFullNameInverse)
								.collect(Collectors.toList())));
	}

	public OrderEditView() {

		this.inputCustomer = new AutoCompleteTextField(new TreeSet<>());
		this.customer = this.customerService.listAll();
		this.products = this.productService.listAll();
		fillAutoCompleteCustomer(this.customer);
		this.inputCustomer.setPromptText("Kunde");
		add(this.inputCustomer, 0, 1);

		this.productGrid.setVgap(10);
		this.productGrid.setHgap(15);
		final Button createProduct = new Button("Neues Produkt");
		createProduct.getStyleClass().add("primary");
		createProduct.setOnAction(e -> {
			createProductRow();
		});

		this.productGrid.add(this.subtotal, 0, 5);
		this.productGrid.add(createProduct, 0, 0);
		this.productGrid.add(this.productListGrid, 0, 1);

		add(this.productGrid, 0, 4);

	}

	private void createProductRow() {
		final ProductRow pr = new ProductRow(this.products);
		this.productRows.add(pr);
		this.productGrid.add(pr.getProduct(), 0, this.productRows.size());
		final Spinner<Integer> count = pr.getCount();
		this.productGrid.add(count, 1, this.productRows.size());
		this.productGrid.add(pr.getRemoveButton(), 2, this.productRows.size());
		pr.fillAutoComplete(this.products);
		setListenerForCount(count);
	}

	private void setListenerForCount(final Spinner<Integer> count) {
		count.valueProperty().addListener((observable, oldValue, newValue) -> {
			this.subtotal.setText(getModel().getZwischenSummeForProducts());
		});
	}

	@Override
	public Order applyCustomValuesToModel(final Order model) {

		if (!this.inputCustomer.getText().isEmpty()) {
			final String[] split = this.inputCustomer.getText().split(",");
			model.setCustomer(this.customerService.findBySurnameAndforename(
					split[0].trim(), split[1].trim()));
		}

		final HashMap<Product, Integer> products = new HashMap<>();

		this.productRows.forEach(productRow -> {
			final Product product = this.productService
					.findByName(productRow.getProduct().getText());
			final Integer count = productRow.getCount().getValue();
			products.put(product, count);
		});
		model.getProducts().clear();
		model.getProducts().putAll(products);

		return model;
	}

	@Override
	protected void setCustomModel(final Order model) {
		fillAutoCompleteCustomer(this.customer);
		fillAutoCompleteProduct(this.products);
		final Customer customer = model.getCustomer();
		if (customer != null) {
			this.inputCustomer.setText(customer.getFullNameInverse());
		}

		final Map<Product, Integer> products = model.getProducts();
		if (products != null) {
			this.productRows.clear();
			this.productListGrid.getChildren().clear();
			products.entrySet().forEach(entry -> {
				final ProductRow pr = new ProductRow(this.products);
				this.productRows.add(pr);
				final AutoCompleteTextField product = pr.getProduct();
				product.setText(entry.getKey().getName());
				this.productGrid.add(product, 0, this.productRows.size());

				final Spinner<Integer> count = pr.getCount();
				setListenerForCount(count);
				count.getValueFactory().setValue(entry.getValue());
				this.productGrid.add(count, 1, this.productRows.size());
				this.productGrid.add(pr.getRemoveButton(), 2,
						this.productRows.size());
				pr.fillAutoComplete(this.products);

			});
		}
	}

	private void fillAutoCompleteProduct(final List<Product> products) {
		this.productRows.forEach(pr -> pr.fillAutoComplete(products));
	}

}
