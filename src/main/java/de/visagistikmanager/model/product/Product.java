package de.visagistikmanager.model.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.Heading;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.model.Title;
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
@NamedQuery(name = Product.NQ_FIND_BY_NAME, query = "SELECT p FROM Product p where p.name = :name")
public class Product extends BaseEntity {

	public static final String NQ_FIND_BY_NAME = "FindByName";

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
