package de.visagistikmanager.core;

import java.util.Properties;

import de.visagistikmanager.view.OrderView;
import de.visagistikmanager.view.UpdateView;
import de.visagistikmanager.view.customer.CustomerView;
import de.visagistikmanager.view.products.ProductView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Starter extends Application {

	private final GridPane root = new GridPane();

	private CustomerView customerView = new CustomerView();
	private OrderView orderView = new OrderView();
	private ProductView productView = new ProductView();

	private UpdateView updateView = new UpdateView();

	public static void main(final String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		final ToolBar toolbar = new ToolBar();

		GridPane toolbarGrid = new GridPane();
		GridPane mainPanel = new GridPane();
		mainPanel.add(updateView, 0, 0);

		ColumnConstraints toolbarColumn = new ColumnConstraints();
		toolbarColumn.setPercentWidth(100);
		root.getColumnConstraints().add(toolbarColumn);
		toolbarGrid.setAlignment(Pos.CENTER);
		toolbarGrid.add(toolbar, 0, 0);

		root.setAlignment(Pos.TOP_LEFT);

		Button customerViewButton = new Button("Kunden");
		customerViewButton.setOnAction(e -> {
			mainPanel.getChildren().clear();
			if (!mainPanel.getChildren().contains(customerView)) {
				mainPanel.add(customerView, 0, 1);
			}
		});

		Button orderViewButton = new Button("Bestellungen");
		orderViewButton.setOnAction(e -> {
			mainPanel.getChildren().clear();
			if (!mainPanel.getChildren().contains(orderView)) {
				mainPanel.add(orderView, 0, 1);
			}
		});

		Button productsViewButton = new Button("Produkte");
		productsViewButton.setOnAction(e -> {
			mainPanel.getChildren().clear();
			if (!mainPanel.getChildren().contains(productView)) {
				mainPanel.add(productView, 0, 1);
			}
		});

		toolbar.getItems().addAll(customerViewButton, orderViewButton, productsViewButton);
		root.add(toolbarGrid, 0, 0);
		root.add(mainPanel, 0, 1);

		root.setGridLinesVisible(true);
		final Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));

		final Scene scene = new Scene(this.root, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.setTitle(properties.getProperty("title") + " - " + properties.getProperty("version"));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
