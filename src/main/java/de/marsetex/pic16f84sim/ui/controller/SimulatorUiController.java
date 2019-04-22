package de.marsetex.pic16f84sim.ui.controller;

import de.marsetex.pic16f84sim.simulator.Simulator;
import de.marsetex.pic16f84sim.state.SimStateContMode;
import de.marsetex.pic16f84sim.state.SimStateIdle;
import de.marsetex.pic16f84sim.ui.components.AboutDialog;
import de.marsetex.pic16f84sim.ui.components.LstFileChooser;
import de.marsetex.pic16f84sim.ui.models.CodeModel;
import de.marsetex.pic16f84sim.ui.models.GprModel;
import de.marsetex.pic16f84sim.ui.models.SfrModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	private TableView<CodeModel> codeTable;

	@FXML
	private TableColumn<CodeModel, String> codeTableCurrentLine;

	@FXML
	private TableColumn<CodeModel, String> codeTableLine;

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

	@FXML
	private TextArea debugConsole;

	private List<String> codeLines;

	public SimulatorUiController() {
		simulator = Simulator.getInstance();

		simulator.getDebugConsole().subscribe(msg -> outputToDebugConsole(msg));

		simulator.getCodeLines().subscribe(codeLines -> outputLstFile(codeLines));
		simulator.getCurrentExecutedCode().subscribe(currentExecCode -> outputCurrentExecutedCode(currentExecCode));

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

		codeTableCurrentLine.setCellValueFactory(new PropertyValueFactory<>("IsExecuted"));
		codeTableLine.setCellValueFactory(new PropertyValueFactory<>("CodeLine"));
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
		ObservableList<CodeModel> o = FXCollections.observableArrayList();

		for(String codeLine : codeLines) {
			o.add(new CodeModel("", codeLine));
		}

		codeTable.setItems(o);
		this.codeLines = codeLines;
	}

	private void outputCurrentExecutedCode(Integer currentExecCode) {
		Platform.runLater(() -> {
			String pcAsString = String.format("%1$04X", currentExecCode);

			ObservableList<CodeModel> o = FXCollections.observableArrayList();
			for(CodeModel model : codeTable.getItems()) {
				if(model.getCodeLine().substring(0, 4).equals(pcAsString)) {
					model.setIsExecuted("-->");
				} else {
					model.setIsExecuted("");
				}
				o.add(model);
			}

			codeTable.getItems().remove(0, codeTable.getItems().size());
			codeTable.setItems(o);
		});
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

	private void outputToDebugConsole(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		debugConsole.appendText(dateFormat.format(new Date()) + ": " + msg + "\n");
	}
}
