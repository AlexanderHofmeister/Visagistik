package de.visagistikmanager.view.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainController {

	@FXML
	private Pane mainPane;

	private void loadEntityOverview(final String name) throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/" + name + ".fxml"));
		final Pane pane = (Pane) loader.load();
		this.mainPane.getChildren().clear();
		this.mainPane.getChildren().add(pane);
	}

	public void switchToCustomerPane() throws IOException {
		loadEntityOverview("customerOverview");
	}

	public void switchToLogPane() throws IOException {
		loadEntityOverview("logOverview");
	}

	public void switchToStart() throws IOException {
		loadEntityOverview("start" + "");
	}

	public void switchToOrderPane() throws IOException {
		loadEntityOverview("orderOverview");
	}

	public void switchToProductPane() throws IOException {
		loadEntityOverview("productOverview");
	}

	public void switchToTemplatesPane() throws IOException {
		loadEntityOverview("templatesOverview");
	}

	public void switchToUserPane() throws IOException {
		loadEntityOverview("userOverview");
	}

	public void exitApplication() {
		Platform.exit();
	}

}
