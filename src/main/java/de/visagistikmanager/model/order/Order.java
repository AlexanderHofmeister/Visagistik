package de.visagistikmanager.model.order;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.model.customer.Customer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Customer customer;

	@ModelAttribute(placeholder = "Quittungsnummmer", row = 0, column = 1)
	private int receiptNumber;

	@ModelAttribute(placeholder = "Erstellt am", row = 1, column = 0)
	private LocalDate createdDate;

	@Enumerated(EnumType.STRING)
	@ModelAttribute(placeholder = "Status", row = 1, column = 1)
	private OrderState state;

	@ElementCollection
	private Map<Order, Integer> products;

	@ModelAttribute(placeholder = "Lieferdatum", row = 3, column = 1)
	private LocalDate deliveryDate;

	@Enumerated(EnumType.STRING)
	private PaymentState paymentState;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@OneToMany
	private Set<Payment> payments;

}
