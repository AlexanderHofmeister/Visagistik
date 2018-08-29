package de.visagistikmanager.view.products;

import de.visagistikmanager.model.product.Product;
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

}
