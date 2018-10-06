package de.visagistikmanager.data;

import javax.persistence.Entity;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String company;

	private String street;

	private String streetNumber;

	private Integer zip;

	private String city;

	private String telephone;

	private String email;

	private String url;

	private String secondUrl;

	private String surname;

	private String forename;

	private String bank;

	private String iban;

}
