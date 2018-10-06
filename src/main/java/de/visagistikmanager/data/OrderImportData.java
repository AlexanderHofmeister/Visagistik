package de.visagistikmanager.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.fastnate.data.AbstractDataProvider;

import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.OrderState;
import de.visagistikmanager.model.order.PaymentState;
import lombok.Getter;

public class OrderImportData extends AbstractDataProvider {

	/** A list that contains all the created data. */
	@Getter
	private final List<Order> entities = new ArrayList<>();

	/** Create the entities. */
	@Override
	public void buildEntities() throws IOException {
		for (int i = 0; i < 100; i++) {
			addOrder(i, PaymentState.values()[i % 3], OrderState.values()[i % 3], new BigDecimal(i));
		}
	}

	private void addOrder(final Integer receiptNumber, final PaymentState paymentState, final OrderState orderState,
			final BigDecimal discount) {
		final Order order = new Order();
		order.setReceiptNumber(receiptNumber);
		order.setCreatedDate(LocalDate.now());
		order.setDeliveryDate(LocalDate.now());
		order.setPaymentState(paymentState);
		order.setState(orderState);
		order.setDiscount(discount);
		this.entities.add(order);

	}
}