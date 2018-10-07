package de.visagistikmanager.view.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogOverviewController implements Initializable {

	@FXML
	private TextArea logMessages;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		try {
			this.logMessages.setText(FileUtils.readFileToString(new File("vm.log")));
		} catch (final IOException e) {
			log.error("Could not read from file: ", e);
		}

	}
}