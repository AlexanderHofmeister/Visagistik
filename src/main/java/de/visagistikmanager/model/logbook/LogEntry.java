package de.visagistikmanager.model.logbook;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LogEntry extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int day;
	private LocalTime departure;
	private LocalTime arrival;
	private double km;

	private String start;
	private String destination;

	private String purpose;
	private String customer;

	@ManyToOne
	private Logbook logbook;

}
