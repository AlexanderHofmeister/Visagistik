package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.ProductService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

	@FXML
	@Getter
	private TableColumn<Product, Product> action;

	private final ProductService productService = new ProductService();

	@FXML
	private TextField filterField;

	private ObservableList<Product> allProducts;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.allProducts = FXCollections.observableArrayList(this.productService.listAll());

		final FilteredList<Product> filteredData = new FilteredList<>(this.allProducts);

		// 2. Set the filter Predicate whenever the filter changes.
		this.filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(product -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name field in your object with filter.
				final String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(product.getName()).toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		final SortedList<Product> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(this.productTable.comparatorProperty());
		// 5. Add sorted (and filtered) data to the table.
		this.productTable.setItems(sortedData);

		this.number.setCellValueFactory(new PropertyValueFactory<Product, Integer>("number"));
		this.name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		this.price.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
		this.productTable.maxHeightProperty().bind(
				Bindings.size(this.productTable.getItems()).multiply(this.productTable.getFixedCellSize()).add(30));

	}

}
