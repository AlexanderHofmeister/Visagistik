package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.ProductService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

public class ProductTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Product> productTable;

	@FXML
	@Setter
	private Pane customerTablePane;

	@FXML
	private TableColumn<Product, Integer> number;

	@FXML
	private TableColumn<Product, String> name;

	@FXML
	private TableColumn<Product, BigDecimal> price;

	private final ProductService productService = new ProductService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.number.setCellValueFactory(new PropertyValueFactory<Product, Integer>("number"));
		this.name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		this.price.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
		this.productTable.maxHeightProperty().bind(
				Bindings.size(this.productTable.getItems()).multiply(this.productTable.getFixedCellSize()).add(30));
		this.productTable.getItems().addAll(this.productService.listAll());
	}

}
