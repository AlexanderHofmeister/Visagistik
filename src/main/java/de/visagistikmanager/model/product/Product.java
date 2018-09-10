package de.visagistikmanager.model.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import de.visagistikmanager.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = Product.NQ_FIND_BY_NAME, query = "SELECT p FROM Product p where p.name = :name")
public class Product extends BaseEntity {

	public static final String NQ_FIND_BY_NAME = "FindByName";

	private static final long serialVersionUID = 1L;

	private Integer number;

	private String name;

	private BigDecimal price;

}
