package de.visagistikmanager.view.controller;

public interface BaseEditController<E> {

	public void applyValuesToEntity(final E entity);

	public void setValuesFromEntity(final E entity);
}
