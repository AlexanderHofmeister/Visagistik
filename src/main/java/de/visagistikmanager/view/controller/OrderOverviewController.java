package de.visagistikmanager.view.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.controlsfx.control.Notifications;

import com.itextpdf.html2pdf.HtmlConverter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.customer.Customer;
import de.visagistikmanager.model.order.Order;
import de.visagistikmanager.model.order.ProductRow;
import de.visagistikmanager.model.property.TemplateFile;
import de.visagistikmanager.model.property.TemplateType;
import de.visagistikmanager.service.OrderService;
import de.visagistikmanager.service.TemplateFileService;
import de.visagistikmanager.service.UserService;
import de.visagistikmanager.util.DateUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class OrderOverviewController implements Initializable {

	@FXML
	private Pane entityPane;

	@FXML
	private Button createOrderButton;

	private final OrderService orderService = new OrderService();

	private final UserService userService = new UserService();

	private final TemplateFileService templateFileService = new TemplateFileService();

	public void createOrder() throws IOException {
		buildOrderEdit(new Order());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		loadTable();
	}

	private void loadTable() {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/orderTable.fxml"));
		Pane pane = null;
		try {
			pane = (Pane) loader.load();
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final OrderTableController controller = loader.getController();

		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(pane);

		final TableView<Order> orderTable = controller.getOrderTable();

		orderTable.setRowFactory(tv -> {
			final TableRow<Order> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					buildOrderView(row.getItem());
				}
			});
			return row;
		});

		final TableColumn<Order, Order> actionColumn = controller.getAction();

		actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		actionColumn.setCellFactory(param -> {
			return new TableCell<Order, Order>() {

				@Override
				protected void updateItem(final Order entity, final boolean empty) {
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
						buildOrderEdit(entity);
					});

					final Button deleteButton = new Button();
					final FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
					deleteIcon.setIcon(FontAwesomeIconName.TRASH);
					deleteButton.setGraphic(deleteIcon);
					deleteButton.getStyleClass().add("button");
					deleteButton.setOnAction(event -> {
						OrderOverviewController.this.orderService.delete(entity);
						loadTable();
					});

					final Button billButton = new Button();
					billButton.setTooltip(new Tooltip("Rechnung"));
					final FontAwesomeIcon billIcon = new FontAwesomeIcon();
					billIcon.setIcon(FontAwesomeIconName.FILE_PDF_ALT);
					billButton.setGraphic(billIcon);
					billButton.getStyleClass().add("button");
					billButton.setOnAction(event -> {

						final TemplateFile billTemplate = OrderOverviewController.this.templateFileService
								.findByType(TemplateType.BILL);

						if (billTemplate != null) {

							final VelocityContext context = new VelocityContext();
							final List<ProductRow> orders = entity.getProducts();
							final Customer customer = entity.getCustomer();
							context.put("orders", orders);
							context.put("customer", customer);
							context.put("user", OrderOverviewController.this.userService.findUser());
							context.put("today", DateUtil.formatDate(LocalDate.now()));
							context.put("totalAmount", entity.getTotal());

							final StringWriter stringWriter = new StringWriter();
							Velocity.evaluate(context, stringWriter, "PackageTemplatesVelocity",
									new String(billTemplate.getData()));

							try {
								HtmlConverter.convertToPdf(stringWriter.toString(),
										new FileOutputStream(new File("a.pdf")));
							} catch (final IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					setGraphic(new HBox(15, editButton, deleteButton, billButton));

				}
			};
		});
	}

	private void buildOrderView(final Order order) {
		final FXMLLoader orderViewLoader = new FXMLLoader();
		orderViewLoader.setLocation(getClass().getClassLoader().getResource("fxml/orderView.fxml"));
		Pane viewPane = null;
		try {
			viewPane = (Pane) orderViewLoader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final OrderViewController orderViewController = orderViewLoader.getController();
		orderViewController.setOrder(order);
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(viewPane);
	}

	private void buildOrderEdit(final Order Order) {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("fxml/orderEdit.fxml"));
		Pane editPane = null;
		try {
			editPane = (Pane) loader.load();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final OrderEditController orderEditController = loader.getController();
		orderEditController.setOrder(Order);
		orderEditController.getCancel().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			loadTable();
		});
		orderEditController.getSave().setOnAction(e -> {
			this.entityPane.getChildren().clear();
			orderEditController.saveOrder();
			loadTable();
			Notifications.create().darkStyle().text("Bestellung erfolgreich gespeichert").hideAfter(Duration.seconds(5))
					.owner(this.entityPane).show();
		});
		this.entityPane.getChildren().clear();
		this.entityPane.getChildren().add(editPane);
	}

}
