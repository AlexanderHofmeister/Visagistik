package de.visagistikmanager.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Produktnummer", row = 0, column = 0)
	private int number;

	@ModelAttribute(placeholder = "Name", row = 0, column = 1)
	private String name;

	@ModelAttribute(placeholder = "Preis", row = 2, column = 0)
	private Integer price;

}
