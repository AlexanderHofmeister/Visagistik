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
		this.entities.add(addProduct(13045, "Kosmetikpinsel", 5.50));
		this.entities.add(addProduct(13046, "Lotion", 4.99));
		this.entities.add(addProduct(13047, "Creme", 3.09));
		this.entities.add(addProduct(13048, "Teure Seife", 10.50));
		this.entities.add(addProduct(13049, "Teures Deo", 10.50));
		this.entities.add(addProduct(13050, "Teurer Kosmetikpinsel", 21.50));
		this.entities.add(addProduct(13051, "Teure Lotion", 14.99));
		this.entities.add(addProduct(13052, "Teure Creme", 13.09));
		this.entities.add(addProduct(13053, "Roter Lippenstift", 6.50));
		this.entities.add(addProduct(13054, "Neutrales Deo", 5.50));
		this.entities.add(addProduct(13055, "Neutrale Bodylotion", 4.99));
		this.entities.add(addProduct(13056, "Deocreme", 3.09));
		this.entities.add(addProduct(13057, "Rasierklingen", 3.09));

	}

	private Product addProduct(int number, String name, double price) {
		return new Product(number, name, new BigDecimal(price));
	}
}