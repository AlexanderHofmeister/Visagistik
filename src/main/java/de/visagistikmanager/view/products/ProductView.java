package de.visagistikmanager.view.products;

import de.visagistikmanager.model.Product;
import de.visagistikmanager.service.ProductService;
import de.visagistikmanager.view.BaseEntityView;
import lombok.Getter;

public class ProductView extends BaseEntityView<Product> {

	private ProductEditView editView;

	private ProductListView listView;

	@Getter
	private ProductService service = new ProductService();

	@Override
	protected ProductEditView getEditView() {
		if (this.editView == null) {
			this.editView = new ProductEditView();
		}
		return this.editView;
	}

	@Override
	protected ProductListView getListView() {
		if (listView == null) {
			listView = new ProductListView(editView, panel);
		}
		return listView;
	}

	//

	//
	// public ProductView() {
	// Button newProductButton = new Button("Produkt anlegen");
	// newProductButton.setOnAction(e -> {
	// final Product model = new Product();
	// this.productEditView.setModel(model);
	// getChildren().remove(productEditView);
	// add(productEditView, 1, 1, 1, 2);
	// });
	//
	// productEditView.setSaveAction(e -> {
	// this.productEditView.applyValuesToModel();
	//
	// Product product = productService.update(productEditView.getModel());
	// ObservableList<Product> items = productTable.getItems();
	// if (!items.contains(product)) {
	// items.add(product);
	// }
	// productTable.refresh();
	// getChildren().remove(productEditView);
	// });
	//
	// productEditView.setCancelAction(e -> {
	// getChildren().remove(productEditView);
	// });
	//
	// add(newProductButton, 0, 0);
	//
	// final TableColumn<Product, String> numberColumn = new
	// TableColumn<>("Produktnummer");
	// numberColumn.setMinWidth(100);
	// numberColumn.setCellValueFactory(new PropertyValueFactory<Product,
	// String>("number"));
	//
	// final TableColumn<Product, String> nameColumn = new
	// TableColumn<>("Name");
	// nameColumn.setMinWidth(100);
	// nameColumn.setCellValueFactory(new PropertyValueFactory<Product,
	// String>("name"));
	//
	// final TableColumn<Product, String> descriptionColumn = new
	// TableColumn<>("Beschreibung");
	// descriptionColumn.setMinWidth(100);
	// descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product,
	// String>("description"));
	//
	// final TableColumn<Product, String> priceColumn = new
	// TableColumn<>("Preis");
	// priceColumn.setMinWidth(100);
	// priceColumn.setCellValueFactory(new PropertyValueFactory<Product,
	// String>("price"));
	//
	// TableColumn<Product, Product> actionColumn = new
	// TableColumn<>("Aktionen");
	// actionColumn.setCellValueFactory(param -> new
	// ReadOnlyObjectWrapper<>(param.getValue()));
	//
	// actionColumn.setCellFactory(param -> new TableCell<Product, Product>() {
	// private final Button editButton = new Button("Editieren");
	//
	// @Override
	// protected void updateItem(Product Product, boolean empty) {
	// super.updateItem(Product, empty);
	//
	// if (Product == null) {
	// setGraphic(null);
	// return;
	// }
	//
	// setGraphic(editButton);
	// editButton.setOnAction(event -> {
	// productEditView.setModel(Product);
	// this.getChildren().remove(productEditView);
	// add(productEditView, 1, 1, 1, 2);
	// });
	// }
	// });
	//
	// this.productTable.getColumns().add(numberColumn);
	// this.productTable.getColumns().add(nameColumn);
	// this.productTable.getColumns().add(descriptionColumn);
	// this.productTable.getColumns().add(priceColumn);
	// this.productTable.getColumns().add(actionColumn);
	// add(productTable, 0, 1);
	//
	// this.productTable.setItems(FXCollections.observableArrayList(productService.listAll()));
	// }

}
