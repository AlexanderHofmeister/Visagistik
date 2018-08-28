package de.visagistikmanager.view;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.service.AbstractEntityService;
import de.visagistikmanager.service.ClassUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lombok.Getter;

public abstract class BaseListView<E extends BaseEntity> extends GridPane {

	public abstract AbstractEntityService<E> getService();

	public abstract BaseEditView<E> getEditView();

	@Getter
	private final TableView<E> table = new TableView<>();

	public BaseListView(GridPane pane) {

		table.setPlaceholder(new Label("Keine Daten vorhanden!"));
		table.setPrefHeight(500);
		Class<E> actualTypeBinding = ClassUtil.getActualTypeBinding(getClass(), BaseListView.class, 0);

		// Fields
		Stream.of(actualTypeBinding.getDeclaredFields()).filter(f -> f.isAnnotationPresent(TableAttribute.class))
				.forEach(field -> {

					Callback<CellDataFeatures<E, String>, ObservableValue<String>> value;
					if (field.getType() == BigDecimal.class) {
						value = tableCell -> {
							try {
								return new SimpleStringProperty(String.format("%,.2f",
										new BigDecimal(BeanUtils.getProperty(tableCell.getValue(), field.getName()))
												.setScale(2, RoundingMode.DOWN))
										+ " €");
							} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
								e.printStackTrace();
							}
							return null;
						};
					} else if (BaseEntity.class.isAssignableFrom(field.getType())) {
						value = tableCell -> {
							try {

								Object property = PropertyUtils.getProperty(tableCell.getValue(), field.getName());

								if (property == null) {
									return new SimpleStringProperty("");
								}
								return new SimpleStringProperty(property.toString());
							} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
								e.printStackTrace();
							}
							return null;
						};
					} else {
						value = new PropertyValueFactory<E, String>(field.getName());
					}
					createTableColumn(value, field.getAnnotation(TableAttribute.class));
				});

		// Getter (Strings only)
		Stream.of(actualTypeBinding.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(TableAttribute.class))
				.forEach(method -> {
					createTableColumn(tableCell -> {
						try {
							return new ReadOnlyObjectWrapper<>(method.invoke(tableCell.getValue()).toString());
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						return null;
					}, method.getAnnotation(TableAttribute.class));

				});

		this.table.setItems(FXCollections.observableArrayList(getService().listAll()));

		TableColumn<E, E> actionColumn = new TableColumn<>("Aktionen");
		actionColumn.setMinWidth(250);

		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		actionColumn.setCellFactory(param -> {
			return new TableCell<E, E>() {

				private final Button editButton = new Button("Editieren");

				private final Button deleteButton = new Button("Löschen");

				@Override
				protected void updateItem(E entity, boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}
					editButton.setOnAction(event -> {
						getEditView().setModel(entity);
						pane.getChildren().clear();
						pane.add(getEditView(), 1, 1, 1, 2);
					});

					deleteButton.setOnAction(event -> {
						getTable().getItems().remove(entity);
						getService().delete(entity);
					});

					setGraphic(new HBox(15, editButton, deleteButton));

				}
			};
		});

		table.getColumns().add(actionColumn);

		add(table, 0, 1, 1, 2);
		add(new Label(table.getItems().size() + " Datensätze vorhanden"), 0, 3, 1, 1);
	}

	private void createTableColumn(Callback<CellDataFeatures<E, String>, ObservableValue<String>> value,
			TableAttribute annotation) {
		final TableColumn<E, String> column = new TableColumn<>(annotation.headerLabel());
		column.setMinWidth(annotation.width());
		column.setCellValueFactory(value);
		table.getColumns().add(column);
	}

}
