package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.product.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductViewController implements Initializable {

	private Product product;

	@FXML
	public Label number;

	@FXML
	public Label name;

	@FXML
	public Label price;

	public ProductViewController(final Product product) {
		setProduct(product);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
	}

	public void setProduct(final Product product) {
		this.product = product;
		this.name.setText(this.product.getName());
		this.price.setText(this.product.getPrice().toString());
		this.number.setText(String.valueOf(this.product.getNumber()));
	}

}
