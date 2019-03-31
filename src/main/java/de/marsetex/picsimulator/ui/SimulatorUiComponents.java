package de.marsetex.picsimulator.ui;

import java.io.File;

import javafx.stage.FileChooser;

public class SimulatorUiComponents {

	private FileChooser fileChooser;

	public SimulatorUiComponents() {
		initFileChooser();
	}

	private void initFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Select LST file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("LST", "*.LST"));
	}

	public FileChooser getFileChooser() {
		return fileChooser;
	}
}
