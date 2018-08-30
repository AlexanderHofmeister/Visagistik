package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.Heading;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.model.Title;
import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.product.Product;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Title("Bestellung")
@Table(name = "\"Order\"")
@Heading(value = "Stammdaten", row = 0)
@Heading(value = "Produkte", row = 3)
@Heading(value = "Zahlung", row = 6)
@Heading(value = "Lieferung", row = 9)
@Heading(value = "Erinnerung", row = 11)
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Quittungsnummmer", row = 1, column = 1)
	@TableAttribute(headerLabel = "Quittungsnummer")
	private Integer receiptNumber;

	@ManyToOne
	@TableAttribute(headerLabel = "Kunde")
	private Customer customer;

	@ModelAttribute(placeholder = "Erstellt am", row = 2, column = 0)
	private LocalDate createdDate = LocalDate.now();

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Status", row = 2, column = 1)
	@TableAttribute(headerLabel = "Bestellstatus")
	private OrderState state;

	@ElementCollection
	private Map<Product, Integer> products;

	@ModelAttribute(placeholder = "Rabatt", row = 5, column = 0)
	private BigDecimal discount;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Bezahlstatus", row = 7, column = 0)
	@TableAttribute(headerLabel = "Bezahlstatus")
	private PaymentState paymentState;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Bezahltyp", row = 7, column = 1)
	private PaymentType paymentType;

	@OneToMany
	private Set<Payment> payments;

	@ModelAttribute(placeholder = "Lieferdatum", row = 10, column = 0)
	private LocalDate deliveryDate;

	public Order() {
		this.products = new HashMap<>();
		this.paymentState = PaymentState.NONE;
		this.createdDate = LocalDate.now();
		this.state = OrderState.OPEN;
	}

	public String getZwischenSummeForProducts() {
		BigDecimal sum = BigDecimal.ZERO;
		for (final Entry<Product, Integer> product : this.products.entrySet()) {
			sum = sum.add(product.getKey().getPrice()
					.multiply(new BigDecimal(product.getValue())));
		}
		return "Zwischensumme " + sum.toString() + " €";

	}

}
