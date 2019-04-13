package de.marsetex.pic16f84sim.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulatorUi extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = loadFXML();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("PIC16F84 Simulator");
		stage.setMaximized(true);
		stage.show();
	}

	private Parent loadFXML() throws IOException {
		return (Parent) FXMLLoader.load(getClass().getResource("/pic16f84-simulator-ui.fxml"));
	}
}
