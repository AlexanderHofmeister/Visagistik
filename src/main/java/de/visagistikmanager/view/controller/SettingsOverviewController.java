package de.visagistikmanager.view.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.visagistikmanager.model.property.TemplateFile;
import de.visagistikmanager.model.property.TemplateType;
import de.visagistikmanager.service.TemplateFileService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import lombok.Getter;

public class SettingsOverviewController implements Initializable {

	@FXML
	private Pane settingsPane;

	@FXML
	private TableView<TemplateFile> templateFileTable;

	@FXML
	private ComboBox<TemplateType> templateTypes;

	@FXML
	private Button uploadFileButton;

	@FXML
	private Button saveButton;

	private File selectedFile;

	@FXML
	private Label selectedFileLabel;

	private List<TemplateFile> templateFiles;

	private final FileChooser fileChooser = new FileChooser();

	@FXML
	private TableColumn<TemplateFile, TemplateType> templateType;

	@FXML
	private TableColumn<TemplateFile, String> fileName;

	@FXML
	@Getter
	private TableColumn<TemplateFile, TemplateFile> action;

	private final TemplateFileService templateFileService = new TemplateFileService();

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {

		this.templateFileTable.getItems().setAll(this.templateFileService.listAll());

		this.templateType.setCellValueFactory(new PropertyValueFactory<TemplateFile, TemplateType>("type"));
		this.fileName.setCellValueFactory(new PropertyValueFactory<TemplateFile, String>("fileName"));

		this.action.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		this.action.setCellFactory(param -> {
			return new TableCell<TemplateFile, TemplateFile>() {

				@Override
				protected void updateItem(final TemplateFile entity, final boolean empty) {
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
						SettingsOverviewController.this.templateFileService.delete(entity);

					});

					setGraphic(deleteButton);

				}
			};
		});

		this.fileChooser.setTitle("Vorlage hochladen");

		this.templateFiles = this.templateFileService.listAll();
		final EnumSet<TemplateType> temp = this.templateFiles.stream().map(TemplateFile::getType)
				.collect(Collectors.toCollection(() -> EnumSet.noneOf(TemplateType.class)));
		this.templateTypes.setItems(FXCollections.observableArrayList(EnumSet.complementOf(temp)));

		this.settingsPane.setOnDragOver(event -> {
			if (event.getGestureSource() != SettingsOverviewController.this.settingsPane
					&& event.getDragboard().hasFiles()) {
				/* allow for both copying and moving, whatever user chooses */
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		// Dropping over surface
		this.settingsPane.setOnDragDropped(event -> {
			final Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;
				this.selectedFile = db.getFiles().get(0);
				this.selectedFileLabel.setText(this.selectedFile.getPath());
			}
			event.setDropCompleted(success);
			event.consume();
		});

	}

	public void uploadFile() {
		this.selectedFile = this.fileChooser.showOpenDialog(this.settingsPane.getScene().getWindow());
	}

	public void save() throws FileNotFoundException, IOException {
		final byte[] bytes = IOUtils.toByteArray(new FileInputStream(this.selectedFile));
		final TemplateFile templateFile = new TemplateFile(this.templateTypes.getValue(), bytes,
				this.selectedFile.getName());
		this.templateFileService.create(templateFile);
		this.templateTypes.getItems().remove(this.templateTypes.getValue());
		this.selectedFileLabel.setText("");
		this.templateTypes.setValue(null);
		this.selectedFile = null;

	}
}