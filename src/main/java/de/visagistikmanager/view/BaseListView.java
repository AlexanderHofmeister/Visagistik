package de.visagistikmanager.view;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.TableAttribute;
import de.visagistikmanager.service.AbstractEntityService;
import de.visagistikmanager.service.ClassUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
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

		Class<E> actualTypeBinding = ClassUtil.getActualTypeBinding(getClass(), BaseListView.class, 0);

		// Fields
		Stream.of(actualTypeBinding.getDeclaredFields()).filter(f -> f.isAnnotationPresent(TableAttribute.class))
				.forEach(field -> {
					createTableColumn(new PropertyValueFactory<E, String>(field.getName()),
							field.getAnnotation(TableAttribute.class));
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
		actionColumn.setMinWidth(150);

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

		add(table, 0, 1);
	}

	private void createTableColumn(Callback<CellDataFeatures<E, String>, ObservableValue<String>> value,
			TableAttribute annotation) {
		final TableColumn<E, String> column = new TableColumn<>(annotation.headerLabel());
		column.setMinWidth(annotation.width());
		column.setCellValueFactory(value);
		table.getColumns().add(column);
	}

}
