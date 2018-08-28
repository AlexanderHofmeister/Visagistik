package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.model.Title;
import de.visagistikmanager.model.customer.Customer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Title("Bestellung")
@Table(name = "\"Order\"")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ModelAttribute(placeholder = "Quittungsnummmer", row = 0, column = 1)
	@TableAttribute(headerLabel = "Quittungsnummer")
	private Integer receiptNumber;

	@ManyToOne
	@TableAttribute(headerLabel = "Kunde")
	private Customer customer;

	@ModelAttribute(placeholder = "Erstellt am", row = 1, column = 0)
	private LocalDate createdDate = LocalDate.now();

	@ElementCollection
	private Map<Order, Integer> products;

	@ModelAttribute(placeholder = "Lieferdatum", row = 3, column = 0)
	private LocalDate deliveryDate;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Bezahlstatus", row = 4, column = 0)
	@TableAttribute(headerLabel = "Bezahlstatus")
	private PaymentState paymentState;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Status", row = 1, column = 1)
	@TableAttribute(headerLabel = "Bestellstatus")
	private OrderState state;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Bezahltyp", row = 4, column = 1)
	private PaymentType paymentType;

	@OneToMany
	private Set<Payment> payments;

	@ModelAttribute(placeholder = "Rabatt", row = 5, column = 0)
	private BigDecimal discount;

	public Order() {
		this.paymentState = PaymentState.NONE;
		this.createdDate = LocalDate.now();
		this.state = OrderState.OPEN;
	}

}
