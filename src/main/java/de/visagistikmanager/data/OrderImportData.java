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
import de.visagistikmanager.model.order.PaymentType;
import lombok.Getter;

public class OrderImportData extends AbstractDataProvider {

	/** A list that contains all the created data. */
	@Getter
	private final List<Order> entities = new ArrayList<>();

	/** Create the entities. */
	@Override
	public void buildEntities() throws IOException {
		addOrder(5, PaymentState.COMPLETE, PaymentType.TRANSFER, OrderState.OPEN, new BigDecimal(20));
	}

	private void addOrder(final Integer receiptNumber, final PaymentState paymentState, final PaymentType paymentType,
			final OrderState orderState, final BigDecimal discount) {
		final Order Order = new Order();
		Order.setReceiptNumber(receiptNumber);
		Order.setCreatedDate(LocalDate.now());
		Order.setDeliveryDate(LocalDate.now());
		Order.setPaymentState(paymentState);
		Order.setPaymentType(paymentType);
		Order.setState(orderState);
		Order.setDiscount(discount);
		this.entities.add(Order);

	}
}