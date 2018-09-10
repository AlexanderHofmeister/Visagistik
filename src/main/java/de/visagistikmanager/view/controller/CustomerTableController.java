package de.visagistikmanager.view.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.service.CustomerService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

public class CustomerTableController implements Initializable {

	@FXML
	@Getter
	private TableView<Customer> customerTable;

	@FXML
	@Setter
	private Pane customerTablePane;

	@FXML
	private TableColumn<Customer, String> surname;

	@FXML
	private TableColumn<Customer, String> forename;

	@FXML
	private TableColumn<Customer, String> birthday;

	private final CustomerService customerService = new CustomerService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.surname.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
		this.forename.setCellValueFactory(new PropertyValueFactory<Customer, String>("forename"));
		this.birthday.setCellValueFactory(new PropertyValueFactory<Customer, String>("adress"));
		this.customerTable.maxHeightProperty().bind(
				Bindings.size(this.customerTable.getItems()).multiply(this.customerTable.getFixedCellSize()).add(30));
		this.customerTable.getItems().addAll(this.customerService.listAll());
	}

}
