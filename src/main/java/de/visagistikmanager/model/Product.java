package de.visagistikmanager.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Title("Produkt")
@AllArgsConstructor
@NoArgsConstructor
@Heading(value = "Stammdaten", row = 0)
public class Product extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Produktnummer", row = 1, column = 0)
	@TableAttribute(headerLabel = "Produktnummer")
	private Integer number;

	@ModelAttribute(placeholder = "Name", row = 2, column = 0)
	@TableAttribute(headerLabel = "Name")
	private String name;

	@ModelAttribute(placeholder = "Preis", row = 2, column = 1)
	@TableAttribute(headerLabel = "Preis")
	private BigDecimal price;

}
