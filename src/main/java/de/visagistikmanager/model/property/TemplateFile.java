package de.visagistikmanager.model.property;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;

import de.visagistikmanager.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = TemplateFile.FIND_BY_TYPE, query = "SELECT f FROM TemplateFile f where f.type = :type")
public class TemplateFile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_TYPE = "FindByType";

	@Enumerated(EnumType.STRING)
	private TemplateType type;

	@Lob
	@Column(length = 100000)
	private byte[] data;

	private String fileName;

}
