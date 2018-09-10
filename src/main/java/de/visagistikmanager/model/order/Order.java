package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.customer.Customer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"Order\"")
public class Order extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer receiptNumber;

	@ManyToOne
	private Customer customer;

	private LocalDate createdDate = LocalDate.now();

	@Enumerated(EnumType.STRING)
	private OrderState state;

	@ElementCollection
	private List<ProductRow> products;

	private BigDecimal discount;

	@Enumerated(EnumType.STRING)
	private PaymentState paymentState;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@OneToMany
	private Set<Payment> payments;

	private LocalDate deliveryDate;

	public Order() {
		this.products = new ArrayList<>();
		this.paymentState = PaymentState.NONE;
		this.createdDate = LocalDate.now();
		this.state = OrderState.OPEN;
	}

}
