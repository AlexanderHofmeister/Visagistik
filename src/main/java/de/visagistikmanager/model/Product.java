package de.visagistikmanager.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Title("Produkt")
public class Product extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Produktnummer", row = 0, column = 0)
	@TableAttribute(headerLabel = "Produktnummer", index = 0)
	private Integer number;

	@ModelAttribute(placeholder = "Name", row = 1, column = 0)
	@TableAttribute(headerLabel = "Name", index = 0)
	private String name;

	@ModelAttribute(placeholder = "Preis", row = 1, column = 1)
	@TableAttribute(headerLabel = "Preis", index = 2)
	private BigDecimal price;

}
