package de.visagistikmanager.model.logbook;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@NamedQuery(name = Logbook.NQ_FIND_BY_MONTH, query = "SELECT l FROM Logbook l where l.month = :month")
public class Logbook extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String NQ_FIND_BY_MONTH = "Logbook.FindByMonth";

	private String month;

	private Integer year;

	@OneToMany(cascade = CascadeType.ALL)
	private List<LogEntry> entries = new ArrayList<>();

}
