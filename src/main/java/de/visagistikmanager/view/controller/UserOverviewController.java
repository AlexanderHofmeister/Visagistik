package de.visagistikmanager.view.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;

import de.visagistikmanager.data.User;
import de.visagistikmanager.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private GridPane userContent;

	@FXML
	private final Button saveButton = new Button("Speichern");

	private final List<TextField> inputFields = new ArrayList<>();

	private final UserService userService = new UserService();

	private User user;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.user = this.userService.findUser();
		if (this.user == null) {
			final User entity = new User();
			this.user = this.userService.create(entity);
		}
		final Field[] declaredFields = this.user.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			final Field field = declaredFields[i];
			if (!Modifier.isStatic(field.getModifiers())) {
				final String fieldName = field.getName();
				final TextField textfield = new TextField();
				textfield.setId(fieldName);

				try {
					final String property = BeanUtils.getProperty(this.user, fieldName);
					if (property != null) {
						textfield.setText(property.toString());
					}
				} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					log.error("Can't create input field for " + fieldName);
				}

				this.userContent.add(new Label(field.getName()), 0, i);
				this.userContent.add(textfield, 1, i);
				this.inputFields.add(textfield);
			}
		}
		this.userContent.add(this.saveButton, 0, this.userContent.getChildren().size() / 2 + 1);
		this.saveButton.setOnAction(this::save);
	}

	public void save(final ActionEvent event) {
		for (final TextField textField : this.inputFields) {
			final String text = textField.getText();
			try {
				BeanUtils.setProperty(this.user, textField.getId(), text);
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error("Can't save input field " + textField.getId() + " with " + text);
			}

		}
		this.userService.update(this.user);
	}

}
