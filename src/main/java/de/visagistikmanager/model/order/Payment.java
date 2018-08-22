package de.visagistikmanager.model.order;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private BigDecimal value;

	private LocalDate date;

	private PaymentType type;

}
