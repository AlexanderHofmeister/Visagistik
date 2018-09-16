package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.ProductRow;
import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.ProductService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class OrderEditProductsController implements Initializable, BaseEditController<Order> {

	@FXML
	private TextField product;

	@FXML
	private TableView<ProductRow> products;

	@FXML
	public TextField discount;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	@FXML
	private Spinner<Integer> amount;

	@FXML
	private Button addProduct;

	private final ProductService productService = new ProductService();

	@FXML
	private TableColumn<ProductRow, String> productColumn;

	@FXML
	private TableColumn<ProductRow, Integer> amountColumn;

	@FXML
	private TableColumn<ProductRow, BigDecimal> priceColumn;

	@FXML
	private TableColumn<ProductRow, ProductRow> productActionColumn;

	@FXML
	private Label subTotal;

	@FXML
	private Label total;

	@FXML
	private OrderEditPaymentsController editPaymentsController;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		calculateAndSetSums();

		this.discount.textProperty().addListener((observable, oldValue, newValue) -> {
			calculateAndSetSums();
		});

		this.amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 0));

		TextFields.bindAutoCompletion(this.product,
				this.productService.listAll().stream().map(Product::getName).collect(Collectors.toList()));

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

		this.productActionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.productActionColumn.setPrefWidth(100);
		this.productActionColumn.setCellFactory(param -> {
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
						OrderEditProductsController.this.products.getItems().remove(entity);
						calculateAndSetSums();
					});

					setGraphic(new HBox(15, deleteButton));

				}
			};
		});

	}

	public void calculateAndSetSums() {
		final BigDecimal subTotalSum = this.products.getItems().stream().map(ProductRow::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.subTotal.setText(subTotalSum + " €");
		final String currentDiscount = this.discount.getText();
		this.total.setText(subTotalSum
				.subtract(subTotalSum.divide(new BigDecimal(100))
						.multiply(currentDiscount.isEmpty() ? BigDecimal.ZERO : new BigDecimal(currentDiscount)))
				.setScale(2, RoundingMode.CEILING) + " €");
	}

	@Override
	public void applyValuesToEntity(final Order order) {
		order.setDiscount(new BigDecimal(this.discount.getText()));
		order.setProducts(this.products.getItems());
	}

	@Override
	public void setValuesFromEntity(final Order order) {
		this.discount.setText(order.getDiscount() == null ? "" : order.getDiscount().toString());
		this.products.getItems().setAll(order.getProducts());
	}

}
