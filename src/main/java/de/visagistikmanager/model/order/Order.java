package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
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

	@ElementCollection
	private List<Payment> payments;

	private LocalDate deliveryDate;

	public Order() {
		this.products = new ArrayList<>();
		this.paymentState = PaymentState.NONE;
		this.createdDate = LocalDate.now();
		this.state = OrderState.OPEN;
	}

	@PreUpdate
	private void preUpdate() {
		System.out.println("aa");
		System.out.println("Before: " + this.paymentState);
		calcPaymentState();
		System.out.println("After: " + this.paymentState);
	}

	public void calcPaymentState() {

		final BigDecimal paymentSum = this.payments.stream().map(Payment::getValue).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		final BigDecimal productSum = this.products.stream().map(ProductRow::getPrice).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		if (paymentSum.compareTo(BigDecimal.ZERO) == 0) {
			this.paymentState = PaymentState.NONE;
		} else if (paymentSum.compareTo(productSum) > 0) {
			this.paymentState = PaymentState.COMPLETE;
		} else if (paymentSum.compareTo(productSum) < 0) {
			this.paymentState = PaymentState.PARTIALLY;
		}

	}

}
