package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.OrderState;
import de.visagistikmanager.model.order.PaymentState;
import de.visagistikmanager.model.order.PaymentType;
import de.visagistikmanager.model.order.ProductRow;
import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.CustomerService;
import de.visagistikmanager.service.OrderService;
import de.visagistikmanager.service.ProductService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class OrderEditController implements Initializable {

	private Order order;

	@FXML
	private Label heading;

	@FXML
	public TextField receiptNumber;

	@FXML
	public TextField customer;

	@FXML
	public DatePicker createdDate;

	@FXML
	public ComboBox<OrderState> state;

	@FXML
	private TableView<ProductRow> products;

	@FXML
	public TextField discount;

	@FXML
	public ComboBox<PaymentState> paymentState;

	@FXML
	public ComboBox<PaymentType> paymentType;

	@FXML
	public DatePicker deliveryDate;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	@FXML
	private TextField product;

	@FXML
	private Spinner<Integer> amount;

	@FXML
	private Button addProduct;

	private final OrderService orderService = new OrderService();

	private final ProductService productService = new ProductService();

	private final CustomerService customerService = new CustomerService();

	@FXML
	private TableColumn<ProductRow, String> productColumn;

	@FXML
	private TableColumn<ProductRow, Integer> amountColumn;

	@FXML
	private TableColumn<ProductRow, BigDecimal> priceColumn;

	@FXML
	private TableColumn<ProductRow, ProductRow> actionColumn;

	@FXML
	private Label subTotal;

	@FXML
	private Label total;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		calculateAndSetSums();

		this.actionColumn.setPrefWidth(100);

		this.discount.textProperty().addListener((observable, oldValue, newValue) -> {
			calculateAndSetSums();
		});

		this.amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 0));

		TextFields.bindAutoCompletion(this.product,
				this.productService.listAll().stream().map(Product::getName).collect(Collectors.toList()));

		TextFields.bindAutoCompletion(this.customer,
				this.customerService.listAll().stream().map(Customer::getFullNameInverse).collect(Collectors.toList()));

		this.addProduct.setOnAction(e -> {
			final Product foundProduct = this.productService.findByName(this.product.getText());
			if (foundProduct != null) {
				this.products.getItems().add(new ProductRow(foundProduct, this.amount.getValue()));
				calculateAndSetSums();
			}
		});

		this.productColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProduct().getName()));
		this.amountColumn.setCellValueFactory(new PropertyValueFactory<ProductRow, Integer>("amount"));
		this.priceColumn.setCellValueFactory(new PropertyValueFactory<ProductRow, BigDecimal>("price"));
		this.actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.actionColumn.setCellFactory(param -> {
			return new TableCell<ProductRow, ProductRow>() {

				@Override
				protected void updateItem(final ProductRow entity, final boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						OrderEditController.this.products.getItems().remove(entity);
						calculateAndSetSums();
					});

					setGraphic(new HBox(15, deleteButton));

				}
			};
		});

		this.state.setItems(FXCollections.observableArrayList(OrderState.values()));
		this.paymentState.setItems(FXCollections.observableArrayList(PaymentState.values()));
		this.paymentType.setItems(FXCollections.observableArrayList(PaymentType.values()));
	}

	public void calculateAndSetSums() {
		final BigDecimal subTotalSum = OrderEditController.this.products.getItems().stream().map(ProductRow::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		OrderEditController.this.subTotal.setText(subTotalSum + " €");

		final String currentDiscount = OrderEditController.this.discount.getText();
		final BigDecimal totalSum = subTotalSum.subtract(subTotalSum.divide(new BigDecimal(100))
				.multiply(currentDiscount.isEmpty() ? BigDecimal.ZERO : new BigDecimal(currentDiscount)));

		OrderEditController.this.total.setText(totalSum.setScale(2, RoundingMode.CEILING) + " €");
	}

	public void setOrder(final Order order) {
		this.order = order;
		this.customer.setText(order.getCustomer() == null ? "" : order.getCustomer().getFullNameInverse());
		this.heading.setText(
				order.isNew() ? "Bestellung anlegen" : "Bestellung " + order.getReceiptNumber() + "  bearbeiten");
		this.receiptNumber.setText(String.valueOf(order.getReceiptNumber()));
		this.createdDate.setValue(order.getCreatedDate());
		this.state.setValue(order.getState());
		this.discount.setText(order.getDiscount() == null ? "" : order.getDiscount().toString());
		this.paymentState.setValue(order.getPaymentState());
		this.paymentType.setValue(order.getPaymentType());
		this.deliveryDate.setValue(order.getDeliveryDate());
		this.products.getItems().setAll(order.getProducts());

	}

	public void saveOrder() {
		final String[] customerInputText = this.customer.getText().split(",");
		this.order.setCustomer(this.customerService.findBySurnameAndforename(customerInputText[0].trim(),
				customerInputText[1].trim()));
		this.order.setReceiptNumber(Integer.valueOf(this.receiptNumber.getText()));
		this.order.setCreatedDate(this.createdDate.getValue());
		this.order.setState(this.state.getValue());
		this.order.setDiscount(new BigDecimal(this.discount.getText()));
		this.order.setPaymentState(this.paymentState.getValue());
		this.order.setPaymentType(this.paymentType.getValue());
		this.order.setDeliveryDate(this.deliveryDate.getValue());
		this.order.setProducts(this.products.getItems());
		this.orderService.update(this.order);
	}

}
