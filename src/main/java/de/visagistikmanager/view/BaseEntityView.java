package de.visagistikmanager.view;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.Title;
import de.visagistikmanager.service.AbstractEntityService;
import de.visagistikmanager.service.ClassUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public abstract class BaseEntityView<E extends BaseEntity> extends GridPane {

	protected abstract AbstractEntityService<E> getService();

	protected abstract BaseEditView<E> getEditView();

	protected abstract BaseListView<E> getListView();

	protected GridPane panel = new GridPane();

	private Button createButton = new Button();

	public BaseEntityView() {
		Class<E> actualTypeBinding = ClassUtil.getActualTypeBinding(getClass(), BaseEntityView.class, 0);

		BaseEditView<E> editView = getEditView();
		createButton.setText(actualTypeBinding.getAnnotation(Title.class).value() + " anlegen");

		createButton.setOnAction(action -> {

			try {
				editView.setModel(actualTypeBinding.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO log
				e.printStackTrace();
			}
			panel.getChildren().clear();
			panel.add(getEditView(), 1, 1, 1, 2);
		});

		editView.setSaveAction(e -> {
			editView.applyValuesToModel();

			E model = getService().update(editView.getModel());
			TableView<E> table = getListView().getTable();
			ObservableList<E> items = table.getItems();
			if (!items.contains(model)) {
				items.add(model);
			}
			table.refresh();
			panel.getChildren().remove(editView);
		});

		editView.setCancelAction(e -> {
			panel.getChildren().remove(editView);
		});

		add(createButton, 0, 0);
		add(getListView(), 0, 1);
		add(panel, 1, 1);
	}

}
