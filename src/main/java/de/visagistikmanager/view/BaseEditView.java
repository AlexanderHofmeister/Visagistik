package de.visagistikmanager.view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import de.visagistikmanager.model.BaseEntity;
import de.visagistikmanager.model.LabeledEnum;
import de.visagistikmanager.model.ListAttribute;
import de.visagistikmanager.model.ModelAttribute;
import de.visagistikmanager.service.ClassUtil;
import de.visagistikmanager.view.components.YesNoRadioButtonGroup;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lombok.Getter;

public class BaseEditView<E extends BaseEntity> extends GridPane {

	@Getter
	private E model;

	protected Button saveButton = new Button("Speichern");
	protected Button cancelButton = new Button("Abbrechen");

	public void setModel(final E model) {
		this.model = model;
		setCustomModel(model);
		setModel(model, getChildren());
	}

	protected void setCustomModel(E model) {
		// nothing to do, override it if you need
	}

	private void setModel(final E model, ObservableList<Node> children) {
		try {
			for (Field field : Stream.of(model.getClass().getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(ModelAttribute.class)).collect(Collectors.toList())) {
				for (Node node : children) {
					if (node.getId() != null && node.getId().equals(field.getName())) {
						String property = BeanUtils.getProperty(model, field.getName());
						if (node instanceof TextField) {
							if (property != null) {
								if (field.getType() == BigDecimal.class) {
									property = property.replace(".", ",");
								}
							}
							((TextField) node).setText(property);
						} else if (node instanceof DatePicker) {
							DatePicker datePicker = (DatePicker) node;
							datePicker.setValue(property == null ? null : LocalDate.parse(property));
						} else if (node instanceof YesNoRadioButtonGroup) {
							((YesNoRadioButtonGroup) node).setValue(property);
						} else if (node instanceof MenuButton) {
							ListAttribute annotation = field.getAnnotation(ListAttribute.class);
							if (annotation != null) {
								MenuButton menu = (MenuButton) node;
								menu.getItems().clear();
								menu.getItems().addAll(Arrays.stream(annotation.value()).map(label -> {
									CheckMenuItem menuItem = new CheckMenuItem(label);
									try {
										if (PropertyUtils.getProperty(model, field.getName()).toString()
												.contains(label)) {
											menuItem.setSelected(true);
										}
									} catch (IllegalAccessException | InvocationTargetException
											| NoSuchMethodException e) {
										e.printStackTrace();
									}
									return menuItem;
								}).collect(Collectors.toList()));
							}
						} else if (node instanceof ComboBox) {
							@SuppressWarnings("unchecked")
							ComboBox<Object> comboBox = (ComboBox<Object>) node;
							Object value = PropertyUtils.getProperty(model, field.getName());
							if (value != null) {
								comboBox.setValue(value);
							}
						} else if (Pane.class.isAssignableFrom(node.getClass())) {
							setModel(model, ((Pane) node).getChildren());
						} else {
							System.out.println("Missing case for " + node.getClass());
						}

					}
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public void applyValuesToModel() {
		applyValuesToModel(getChildren());
	}

	private void applyValuesToModel(ObservableList<Node> children) {
		Class<Object> actualTypeBinding = ClassUtil.getActualTypeBinding(getClass(), BaseEditView.class, 0);

		for (Node node : children) {

			for (Field field : actualTypeBinding.getDeclaredFields()) {

				if (field.getName().equals(node.getId())) {
					try {
						if (node instanceof TextField) {
							String text = ((TextField) node).getText();

							if (text != null) {
								if (field.getType() == BigDecimal.class) {
									text = text.replaceAll(",", ".");
								}
								BeanUtils.setProperty(model, field.getName(), text);
							}

						} else if (node instanceof DatePicker) {
							BeanUtils.setProperty(model, field.getName(), ((DatePicker) node).getValue());
						} else if (node instanceof YesNoRadioButtonGroup) {
							BeanUtils.setProperty(model, field.getName(), ((YesNoRadioButtonGroup) node).getValue());
						} else if (node instanceof MenuButton) {
							try {
								BeanUtils.setProperty(model, field.getName(),
										((MenuButton) node).getItems().stream().map(CheckMenuItem.class::cast)
												.filter(CheckMenuItem::isSelected).map(CheckMenuItem::getText)
												.collect(Collectors.toSet()));
							} catch (IllegalAccessException | InvocationTargetException e) {
								e.printStackTrace();
							}

						} else if (node instanceof ComboBox) {
							BeanUtils.setProperty(model, field.getName(), ((ComboBox<?>) node).getValue());
						} else if (Pane.class.isAssignableFrom(node.getClass())) {
							applyValuesToModel(((Pane) node).getChildren());
						} else {
							System.out.println(field.getClass() + " " + field.getName() + " missing. Case missing: "
									+ node.getClass());
						}

					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	public BaseEditView() {

		setHgap(20);
		setVgap(10);
		setPadding(new Insets(0, 10, 10, 10));

		Class<Object> actualTypeBinding = ClassUtil.getActualTypeBinding(getClass(), BaseEditView.class, 0);

		for (Field field : actualTypeBinding.getDeclaredFields()) {

			if (field.isAnnotationPresent(ModelAttribute.class)) {
				ModelAttribute annotation = field.getAnnotation(ModelAttribute.class);

				int column = annotation.column();
				int row = annotation.row();
				Class<?> type = field.getType();
				if (type == String.class || type == Integer.class || type == int.class) {
					TextField inputField = new TextField();
					inputField.setId(field.getName());
					inputField.setPromptText(annotation.placeholder());
					add(inputField, column, row);
				} else if (type == LocalDate.class) {
					DatePicker dateInputField = new DatePicker();
					dateInputField.setId(field.getName());
					dateInputField.setPromptText(annotation.placeholder());
					add(dateInputField, column, row);
				} else if (type == boolean.class) {
					YesNoRadioButtonGroup child = new YesNoRadioButtonGroup(annotation.placeholder());
					child.setId(field.getName());
					add(child, column, row);
				} else if (type == Set.class) {
					MenuButton child = new MenuButton(annotation.placeholder());
					child.setId(field.getName());
					add(child, column, row);
				} else if (LabeledEnum.class.isAssignableFrom(type)) {
					ComboBox<LabeledEnum> box = new ComboBox<>();
					box.setPromptText(annotation.placeholder());
					HBox container = new HBox(10, new Label(annotation.placeholder() + ": "), box);
					container.setId(field.getName());
					box.setId(field.getName());
					@SuppressWarnings("unchecked")
					Class<LabeledEnum> clazz = (Class<LabeledEnum>) type;
					box.getItems().addAll(Arrays.asList(clazz.getEnumConstants()));
					add(container, column, row);
				} else if (type == BigDecimal.class) {
					TextField inputField = new TextField();

					Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d{0,2}");
					TextFormatter<String> formatter = new TextFormatter<String>(
							(UnaryOperator<TextFormatter.Change>) change -> {
								return pattern.matcher(change.getControlNewText()).matches() ? change : null;
							});

					inputField.setTextFormatter(formatter);

					inputField.setId(field.getName());
					inputField.setPromptText(annotation.placeholder());

					add(inputField, column, row);
				} else {
					throw new IllegalArgumentException("No case defined for " + type);
				}

			}

		}

		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(cancelButton, saveButton);

		add(buttons, 0, getChildren().size());
	}

	public void setSaveAction(EventHandler<ActionEvent> event) {
		this.saveButton.setOnAction(event);
	}

	public void setCancelAction(EventHandler<ActionEvent> event) {
		this.cancelButton.setOnAction(event);
	}

	public E applyCustomValuesToModel(E model) {
		return model;
	}
}
