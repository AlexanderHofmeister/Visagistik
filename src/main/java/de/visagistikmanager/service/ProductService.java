package de.visagistikmanager.service;

import de.visagistikmanager.model.product.Product;

public class ProductService extends AbstractEntityService<Product> {

	public Product findByName(final String name) {
		return findWithNamedQuery(Product.NQ_FIND_BY_NAME,
				QueryParameter.with("name", name).parameters()).stream()
						.findFirst().orElse(null);
	}

}
