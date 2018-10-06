package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

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
		final Integer number = prodcut.getNumber();
		if (number != null) {
			this.number.setText(String.valueOf(number));
		}
		this.name.setText(prodcut.getName());
		this.price.setText(prodcut.getPrice() == null ? "" : prodcut.getPrice().toString());

	}

	public void saveCustomer() {
		final String number = this.number.getText();
		if (StringUtils.isNotBlank(number)) {
			this.product.setNumber(Integer.parseInt(number));
		}
		this.product.setName(this.name.getText());
		final String price = this.price.getText();
		if (StringUtils.isNotBlank(price)) {
			this.product.setPrice(new BigDecimal(price));
		}
		this.productService.update(this.product);
	}

}
