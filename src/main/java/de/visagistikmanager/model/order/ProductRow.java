package de.visagistikmanager.model.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.visagistikmanager.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ProductRow {

	@Column(name = "product", columnDefinition = "LONGVARBINARY")
	private Product product;

	private int amount;

	private BigDecimal price;

	public ProductRow(final Product product, final int amount) {
		this.product = product;
		this.amount = amount;
		this.price = new BigDecimal(amount).multiply(product.getPrice());
	}

}
