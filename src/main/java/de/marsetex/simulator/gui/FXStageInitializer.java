package de.marsetex.simulator.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXStageInitializer extends Application {

	public void initUserInterface(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = loadFXML();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("PIC16F84-Simulator");
		stage.show();
	}

	private Parent loadFXML() throws IOException {
		return (Parent) FXMLLoader.load(getClass().getResource("/PIC-Simulator.fxml"));
	}
}
