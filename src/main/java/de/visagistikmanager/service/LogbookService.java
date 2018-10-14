package de.visagistikmanager.service;

import de.visagistikmanager.model.logbook.Logbook;

public class LogbookService extends AbstractEntityService<Logbook> {

	public Logbook findByMonth(final String month) {
		return findSingleWithNamedQuery(Logbook.NQ_FIND_BY_MONTH, QueryParameter.with("month", month).parameters());
	}

}
