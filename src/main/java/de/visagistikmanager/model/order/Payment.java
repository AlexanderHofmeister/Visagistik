package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	private LocalDate date;

	private BigDecimal value;

	private PaymentType type;

}
