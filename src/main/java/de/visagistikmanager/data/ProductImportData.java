package de.visagistikmanager.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.fastnate.data.AbstractDataProvider;

import de.visagistikmanager.model.Product;
import lombok.Getter;

public class ProductImportData extends AbstractDataProvider {

	/** A list that contains all the created data. */
	@Getter
	private final List<Product> entities = new ArrayList<>();

	/** Create the entities. */
	@Override
	public void buildEntities() throws IOException {
		this.entities.add(addProduct(13040, "Seife", 5.50));
		this.entities.add(addProduct(13041, "Duschgel", 3.99));
		this.entities.add(addProduct(13022, "Handtuch", 15.50));
		this.entities.add(addProduct(13033, "Lippenstift", 6.50));
		this.entities.add(addProduct(13044, "Deo", 5.50));
		this.entities.add(addProduct(13045, "Kosmetikpinsel", 21.50));
		this.entities.add(addProduct(13046, "Lotion", 4.99));
		this.entities.add(addProduct(13047, "Creme", 3.09));
	}

	private Product addProduct(int number, String name, double price) {
		return new Product(number, name, new BigDecimal(price));
	}
}