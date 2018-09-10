package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.product.Product;
import de.visagistikmanager.service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductEditController implements Initializable {

	@FXML
	private Label heading;

	@FXML
	public TextField number;

	@FXML
	public TextField name;

	@FXML
	public TextField price;

	private Product product;

	@FXML
	@Getter
	private Button cancel;

	@FXML
	@Getter
	private Button save;

	private final ProductService productService = new ProductService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
	}

	public void setProduct(final Product prodcut) {
		this.product = prodcut;
		this.heading.setText(prodcut.isNew() ? "Produkt anlegen" : "Produkt " + prodcut.getName() + " bearbeiten");
		this.number.setText(String.valueOf(prodcut.getNumber()));
		this.name.setText(prodcut.getName());
		this.price.setText(prodcut.getPrice() == null ? "" : prodcut.getPrice().toString());

	}

	public void saveCustomer() {
		this.product.setNumber(Integer.parseInt(this.number.getText()));
		this.product.setName(this.name.getText());
		this.product.setPrice(new BigDecimal(this.price.getText()));
		this.productService.update(this.product);
	}

}
