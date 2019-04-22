package de.marsetex.pic16f84sim.ui.controller;

import de.marsetex.pic16f84sim.simulator.Simulator;
import de.marsetex.pic16f84sim.state.SimStateContMode;
import de.marsetex.pic16f84sim.state.SimStateIdle;
import de.marsetex.pic16f84sim.ui.components.AboutDialog;
import de.marsetex.pic16f84sim.ui.components.LstFileChooser;
import de.marsetex.pic16f84sim.ui.models.GprModel;
import de.marsetex.pic16f84sim.ui.models.SfrModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.List;

public class SimulatorUiController {

	private final Simulator simulator;

	@FXML
	private Menu openRecentMenuItem;

	@FXML
	private Slider quartzFrequencySlider;

	@FXML
	private Label quartzFrequencyLabel;

	@FXML
	private ListView<String> codeView;

	@FXML
	private Label wRegisterLabel;

	@FXML
	private Label progamCounterLabel;

	@FXML
	private TableView<GprModel> gprTable;

	@FXML
	private TableColumn<GprModel, String> gprTableAddress;

	@FXML
	private TableColumn<GprModel, String> gprTableHexValue;

	@FXML
	private TableColumn<GprModel, String> gprTableBinaryValue;

	@FXML
	private TableView<SfrModel> sfrTable;


	public SimulatorUiController() {
		simulator = Simulator.getInstance();

		simulator.getCodeLines().subscribe(codeLines -> outputLstFile(codeLines));
		simulator.getPicController().getWRegisterSubject().subscribe(w -> outputWRegister(w));
		simulator.getPicController().getPcSubject().subscribe(pc -> outputPc(pc));
		simulator.getPicController().getGprSubject().subscribe(gpr -> outputGpr(gpr));
		simulator.getPicController().getSfrSubject().subscribe(sfr -> outputSfr(sfr));
	}

	@FXML
	private void initialize() {
		quartzFrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			quartzFrequencyLabel.setText(newValue.intValue() + "000 Hz");
			simulator.setQuartzFrequency(newValue.longValue());
		});

		gprTableAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		gprTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
		gprTableBinaryValue.setCellValueFactory(new PropertyValueFactory<>("BinaryValue"));
	}

	@FXML
	private void openFileChooser() {
		File lstFile = LstFileChooser.getFileChooser().showOpenDialog(null);

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
	private void stepButtonClicked() {
	}

	@FXML
	private void resetButtonClicked() {
		simulator.stopSimulation();
		simulator.resetSimulation();
	}

	@FXML
	private void showAboutDialog() {
		AboutDialog.getAboutDialog().showAndWait();
	}

	@FXML
	private void exit() {
		simulator.stopSimulation();
		Platform.exit();
	}

	private void outputLstFile(List<String> codeLines) {
		codeView.getItems().clear();
		codeView.setItems(FXCollections.observableArrayList(codeLines));
	}

	private void outputWRegister(Byte w) {
		Platform.runLater(() -> wRegisterLabel.setText(String.format("0x%1$02X", w)));
	}

	private void outputPc(Integer pc) {
		Platform.runLater(() -> progamCounterLabel.setText(String.format("0x%1$04X", pc)));
	}

	private void outputGpr(byte[] gpr) {
		ObservableList<GprModel> gprTableContent = FXCollections.observableArrayList();
		for (int i = 0x0C; i < 0x50 ; i++) {
			gprTableContent.add(new GprModel(i, gpr[i]));
		}
		gprTable.setItems(gprTableContent);
	}

	private void outputSfr(byte[] sfr) {
		ObservableList<SfrModel> sfrTableContent = FXCollections.observableArrayList();
		// sfrTable.setItems(sfrTableContent);
	}
}
