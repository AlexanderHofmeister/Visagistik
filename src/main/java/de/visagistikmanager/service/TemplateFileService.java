package de.visagistikmanager.service;

import de.visagistikmanager.model.property.TemplateFile;
import de.visagistikmanager.model.property.TemplateType;

public class TemplateFileService extends AbstractEntityService<TemplateFile> {

	public TemplateFile findByType(final TemplateType type) {
		return findSingleWithNamedQuery(TemplateFile.FIND_BY_TYPE, QueryParameter.with("type", type).parameters());
	}

}
