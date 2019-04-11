package de.marsetex.picsimulator.ui.controller;

import java.io.File;
import java.util.List;

import de.marsetex.picsimulator.Simulator;
import de.marsetex.picsimulator.state.SimStateContMode;
import de.marsetex.picsimulator.state.SimStateIdle;
import de.marsetex.picsimulator.ui.SimulatorUiComponents;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class SimulatorUiController {

	@FXML
	private ListView<String> codeView;

	@FXML
	private Menu openRecentMenuItem;

	private final Simulator simulator;
	private final SimulatorUiComponents uiComponents;

	public SimulatorUiController() {
		simulator = Simulator.getInstance();
		simulator.getCodeLines().subscribe(codeLines -> outputLstFile(codeLines));

		uiComponents = new SimulatorUiComponents();
	}

	@FXML
	private void openFileChooser() {
		File lstFile = uiComponents.getFileChooser().showOpenDialog(null);

		if (lstFile != null) {
			openRecentMenuItem.getItems().add(new MenuItem(lstFile.getPath()));
			simulator.changeState(new SimStateIdle(lstFile));
		}
	}

	@FXML
	private void runButtonClicked() {
		simulator.changeState(new SimStateContMode());
	}

	@FXML
	private void showAboutDialog() {
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

	public void outputLstFile(List<String> codeLines) {
		codeView.getItems().clear();
		codeView.setItems(FXCollections.observableArrayList(codeLines));
	}
}
