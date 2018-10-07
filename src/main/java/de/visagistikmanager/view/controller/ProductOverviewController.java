package de.visagistikmanager.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.ProductService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ProductOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private Button createProductButton;

	private final ProductService productService = new ProductService();

	public void createProduct() throws IOException {
		buildProductEdit(new Product());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		loadTable();
	}

	private void loadTable() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/productTable.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final ProductTableController controller = loader.getController();

		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(pane);

		final TableView<Product> productTable = controller.getProductTable();

		productTable.setRowFactory(tv -> {
			final TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					buildProductEdit(row.getItem());
				}
			});
			return row;
		});

		final TableColumn<Product, Product> actionColumn = controller.getAction();
		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		actionColumn.setCellFactory(param -> {
			return new TableCell<Product, Product>() {

				@Override
				protected void updateItem(final Product entity, final boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}
					final Button editButton = new Button();
					final FontAwesomeIcon editIcon = new FontAwesomeIcon();
					editIcon.setIcon(FontAwesomeIconName.EDIT);
					editButton.setGraphic(editIcon);
					editButton.getStyleClass().add("button");
					editButton.setOnAction(event -> {
						buildProductEdit(entity);
					});

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						ProductOverviewController.this.productService.delete(entity);
						loadTable();
					});

					setGraphic(new HBox(15, editButton, deleteButton));

				}
			};
		});
	}

	private void buildProductEdit(final Product product) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/productEdit.fxml"));
		Pane editPane = null;
		try {
			editPane = (Pane) loader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final ProductEditController productEditController = loader.getController();
		productEditController.setProduct(product);
		productEditController.getCancel().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			loadTable();
		});
		productEditController.getSave().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			productEditController.saveCustomer();
			loadTable();
			Notifications.create().darkStyle().text("Produkt erfolgreich gespeichert").hideAfter(Duration.seconds(5))
					.owner(this.entityPane).show();
		});
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(editPane);
	}

}
