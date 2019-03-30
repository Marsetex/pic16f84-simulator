package de.marsetex.simulator.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class UIController {

	@FXML
	private void openFileChooser() {
		FileChooser s = new FileChooser();
		s.showOpenDialog(null);
	}

	@FXML
	public void showAboutDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("PIC - About");
		alert.setHeaderText("PIC by Marcel");
		alert.setContentText("Developed by: Marcel Gruessinger \nVersion: 1.0.0");
		alert.showAndWait();
	}

	@FXML
	private void exit() {
		Platform.exit();
	}
}
