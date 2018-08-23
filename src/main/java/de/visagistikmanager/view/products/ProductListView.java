package de.visagistikmanager.view.products;

import de.visagistikmanager.model.Product;
import de.visagistikmanager.service.ProductService;
import de.visagistikmanager.view.BaseListView;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class ProductListView extends BaseListView<Product> {

	@Getter
	private ProductEditView editView;

	public ProductListView(ProductEditView editView, GridPane pane) {
		super(pane);
		this.editView = editView;
	}

	private ProductService service;

	@Override
	public ProductService getService() {
		if (this.service == null) {
			this.service = new ProductService();
		}
		return service;
	}

}
