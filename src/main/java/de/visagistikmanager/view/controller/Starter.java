package de.visagistikmanager.view.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Starter extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		final Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml/Main.fxml"));
		final Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);

	}

}
