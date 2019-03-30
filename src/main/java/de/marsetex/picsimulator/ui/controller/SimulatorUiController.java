package de.marsetex.picsimulator.ui.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.marsetex.picsimulator.parser.LSTParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

public class SimulatorUiController {

	@FXML
	private ListView<String> codeView;
	
	private FileChooser fileChooser;	
	private ObservableList<String> lines;
	
	public SimulatorUiController() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select LST file");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );                 
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("LST", "*.LST")
        );
        
        lines = FXCollections.observableArrayList();
	}
	
	@FXML
	private void openFileChooser() {
		File lstFile = fileChooser.showOpenDialog(null);
		
		codeView.setStyle("-fx-font: 12 consolas;");
		
		if(lstFile != null) {
			new LSTParser(lstFile);
			
			try (BufferedReader br = new BufferedReader(new FileReader(lstFile))) {

				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					System.out.println(sCurrentLine);
					lines.add(sCurrentLine);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		codeView.setItems(lines);
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
