package de.visagistikmanager.view.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.Payment;
import de.visagistikmanager.model.order.PaymentType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class OrderEditPaymentsController implements Initializable, BaseEditController<Order> {

	@FXML
	public Label paymentState;

	@FXML
	private TableView<Payment> payments;

	@FXML
	private DatePicker date;

	@FXML
	private TextField amount;

	@FXML
	private ComboBox<PaymentType> type;

	@FXML
	private Button addPayment;

	@FXML
	private TableColumn<Payment, LocalDate> dateColumn;

	@FXML
	private TableColumn<Payment, BigDecimal> amountColumn;

	@FXML
	private TableColumn<Payment, PaymentType> typeColumn;

	@FXML
	private TableColumn<Payment, Payment> actionColumn;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.dateColumn.setCellValueFactory(new PropertyValueFactory<Payment, LocalDate>("date"));
		this.amountColumn.setCellValueFactory(new PropertyValueFactory<Payment, BigDecimal>("value"));
		this.typeColumn.setCellValueFactory(new PropertyValueFactory<Payment, PaymentType>("type"));
		this.actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.actionColumn.setPrefWidth(100);
		this.actionColumn.setCellFactory(param -> {
			return new TableCell<Payment, Payment>() {

				@Override
				protected void updateItem(final Payment entity, final boolean empty) {
					super.updateItem(entity, empty);

					if (entity == null) {
						setGraphic(null);
						return;
					}

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						OrderEditPaymentsController.this.payments.getItems().remove(entity);
					});

					setGraphic(new HBox(15, deleteButton));

				}
			};

		});

		this.addPayment.setOnAction(e -> {
			this.payments.getItems().add(
					new Payment(this.date.getValue(), new BigDecimal(this.amount.getText()), this.type.getValue()));
		});

		this.type.setItems(FXCollections.observableArrayList(PaymentType.values()));

	}

	@Override
	public void applyValuesToEntity(final Order order) {
		order.setPayments(this.payments.getItems());
	}

	@Override
	public void setValuesFromEntity(final Order order) {
		this.payments.getItems().setAll(order.getPayments());

	}

}
