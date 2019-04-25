package de.marsetex.pic16f84sim.ui.controller;

import de.marsetex.pic16f84sim.microcontroller.memory.DataMemory;
import de.marsetex.pic16f84sim.microcontroller.register.helper.StatusRegisterHelper;
import de.marsetex.pic16f84sim.simulator.Simulator;
import de.marsetex.pic16f84sim.state.SimStateContMode;
import de.marsetex.pic16f84sim.state.SimStateIdle;
import de.marsetex.pic16f84sim.ui.components.AboutDialog;
import de.marsetex.pic16f84sim.ui.components.LstFileChooser;
import de.marsetex.pic16f84sim.ui.models.CodeModel;
import de.marsetex.pic16f84sim.ui.models.GprModel;
import de.marsetex.pic16f84sim.ui.models.SfrModel;
import de.marsetex.pic16f84sim.ui.models.StackModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	private Label runtimeCounterLabel;

	@FXML
	private TableView<CodeModel> codeTable;

	@FXML
	private TableColumn<CodeModel, String> codeTableCurrentLine;

	@FXML
	private TableColumn<CodeModel, String> codeTableLine;

	@FXML
	private Label wLabel;

	@FXML
	private Label pcLabel;

	@FXML
	private Label pclLabel;

	@FXML
	private Label pclathLabel;

	@FXML
	private Label cLabel;

	@FXML
	private Label dcLabel;

	@FXML
	private Label zLabel;

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
	private TableColumn<SfrModel, String> sfrTableAddress;

	@FXML
	private TableColumn<SfrModel, String> sfrTableHexValue;

	@FXML
	private TableColumn<SfrModel, String> sfrTableBinaryValue;

	@FXML
	private Label stackPointerLabel;

	@FXML
	private TableView<StackModel> stackTable;

	@FXML
	private TableColumn<StackModel, Integer> stackTablePosition;

	@FXML
	private TableColumn<StackModel, String> stackTableHexValue;

	@FXML
	ToggleGroup PortA0;

	@FXML
	ToggleGroup PortA1;

	@FXML
	ToggleGroup PortA2;

	@FXML
	ToggleGroup PortA3;

	@FXML
	ToggleGroup PortA4;

	@FXML
	ToggleGroup PortB0;

	@FXML
	ToggleGroup PortB1;

	@FXML
	ToggleGroup PortB2;

	@FXML
	ToggleGroup PortB3;

	@FXML
	ToggleGroup PortB4;

	@FXML
	ToggleGroup PortB5;

	@FXML
	ToggleGroup PortB6;

	@FXML
	ToggleGroup PortB7;

	@FXML
	private TextArea debugConsole;

	public SimulatorUiController() {
		simulator = Simulator.getInstance();

		simulator.getDebugConsole().subscribe(msg -> outputToDebugConsole(msg));

		simulator.getCodeLines().subscribe(codeLines -> outputLstFile(codeLines));
		simulator.getCurrentExecutedCode().subscribe(currentExecCode -> outputCurrentExecutedCode(currentExecCode));
		simulator.getRuntimeCounterSubject().subscribe(runtimeCounter -> outputRuntimeCounter(runtimeCounter));

		simulator.getPicController().getWRegisterSubject().subscribe(w -> outputWRegister(w));
		simulator.getPicController().getPcSubject().subscribe(pc -> outputPc(pc));

		simulator.getPicController().getGprSubject().subscribe(gpr -> outputGpr(gpr));
		simulator.getPicController().getSfrSubject().subscribe(sfr -> outputSfr(sfr));
		simulator.getPicController().getStackSubject().subscribe(stack -> outputStack(stack));
		simulator.getPicController().getStackPointerSubject().subscribe(stackPointer -> outputStackPointer(stackPointer));
	}

	@FXML
	private void initialize() {
		quartzFrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			quartzFrequencyLabel.setText(newValue.intValue() + "000 Hz");
			simulator.setQuartzFrequency(newValue.longValue() * 1000);
		});

		PortA0.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 0, ((RadioButton) newValue));
		});
		PortA1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 1, ((RadioButton) newValue));
		});
		PortA2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 2, ((RadioButton) newValue));
		});
		PortA3.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 3, ((RadioButton) newValue));
		});
		PortA4.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x85, 4, ((RadioButton) newValue));
		});
		PortB0.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 0, ((RadioButton) newValue));
		});
		PortB1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 1, ((RadioButton) newValue));
		});
		PortB2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 2, ((RadioButton) newValue));
		});
		PortB3.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 3, ((RadioButton) newValue));
		});
		PortB4.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 4, ((RadioButton) newValue));
		});
		PortB5.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 5, ((RadioButton) newValue));
		});
		PortB6.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 6, ((RadioButton) newValue));
		});
		PortB7.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			updateTrisRegister(0x86, 7, ((RadioButton) newValue));
		});

		codeTableCurrentLine.setCellValueFactory(new PropertyValueFactory<>("IsExecuted"));
		codeTableLine.setCellValueFactory(new PropertyValueFactory<>("CodeLine"));

		gprTableAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		gprTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
		gprTableBinaryValue.setCellValueFactory(new PropertyValueFactory<>("BinaryValue"));

		sfrTableAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
		sfrTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
		sfrTableBinaryValue.setCellValueFactory(new PropertyValueFactory<>("BinaryValue"));

		stackTablePosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
		stackTableHexValue.setCellValueFactory(new PropertyValueFactory<>("HexValue"));
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
		outputToDebugConsole("Simulation reset");
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

	private void updateTrisRegister(int fileRegisterAddress, int bitPosition, RadioButton radioButton) {
		DataMemory dataMemory = simulator.getPicController().getDataMemory();
		byte trisValue = dataMemory.load((byte) fileRegisterAddress);
		byte newTrisValue = (byte) (0x1 << bitPosition);

		if("Input".equals(radioButton.getText())) {
			newTrisValue = (byte) (trisValue | newTrisValue);
		} else {
			newTrisValue = (byte) (trisValue & (~newTrisValue));
		}

		dataMemory.store((byte) fileRegisterAddress, newTrisValue);
	}

	private void outputLstFile(List<String> codeLines) {
		ObservableList<CodeModel> o = FXCollections.observableArrayList();

		for(String codeLine : codeLines) {
			o.add(new CodeModel("", codeLine));
		}

		codeTable.setItems(o);
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

	private void outputRuntimeCounter(Double runtimeCounter) {
		Platform.runLater(() -> {
			runtimeCounterLabel.setText(String.valueOf((Math.round(runtimeCounter * 100.0) / 100.0)) + " µs");
		});
	}

	private void outputWRegister(Byte w) {
		Platform.runLater(() -> wLabel.setText(String.format("0x%1$02X", w)));
	}

	private void outputPc(Integer pc) {
		Platform.runLater(() -> pcLabel.setText(String.format("0x%1$04X", pc)));
	}

	private void outputGpr(byte[] gpr) {
		ObservableList<GprModel> gprTableContent = FXCollections.observableArrayList();
		for (int i = 0x0C; i < 0x50 ; i++) {
			gprTableContent.add(new GprModel(i, gpr[i]));
		}
		gprTable.setItems(gprTableContent);
	}

	private void outputSfr(byte[] sfr) {
		outputSfrAsTable(sfr);
		outputSfrToQuickAccess(sfr);
	}

	private void outputSfrAsTable(byte[] sfr) {
		ObservableList<SfrModel> sfrTableContent = FXCollections.observableArrayList();
		sfrTableContent.add(new SfrModel("INDR", 0x00, sfr[0x00]));
		sfrTableContent.add(new SfrModel("TMR0", 0x01, sfr[0x01]));
		sfrTableContent.add(new SfrModel("PCL", 0x02, sfr[0x02]));
		sfrTableContent.add(new SfrModel("STATUS", 0x03, sfr[0x03]));
		sfrTableContent.add(new SfrModel("FSR", 0x04, sfr[0x04]));
		sfrTableContent.add(new SfrModel("PORTA", 0x05, sfr[0x05]));
		sfrTableContent.add(new SfrModel("PORTB", 0x06, sfr[0x06]));
		sfrTableContent.add(new SfrModel("undefined", 0x07, sfr[0x07]));
		sfrTableContent.add(new SfrModel("EEDATA", 0x08, sfr[0x08]));
		sfrTableContent.add(new SfrModel("EEADR", 0x09, sfr[0x09]));
		sfrTableContent.add(new SfrModel("PCLATH", 0x0A, sfr[0x0A]));
		sfrTableContent.add(new SfrModel("INTCON", 0x0B, sfr[0x0B]));
		sfrTableContent.add(new SfrModel("INDR", 0x80, sfr[0x80]));
		sfrTableContent.add(new SfrModel("OPTION", 0x81, sfr[0x81]));
		sfrTableContent.add(new SfrModel("TRISA", 0x85, sfr[0x85]));
		sfrTableContent.add(new SfrModel("TRISB", 0x86, sfr[0x86]));
		sfrTableContent.add(new SfrModel("undefined", 0x87, sfr[0x87]));
		sfrTableContent.add(new SfrModel("EECON1", 0x88, sfr[0x88]));
		sfrTableContent.add(new SfrModel("EECON2", 0x89, sfr[0x89]));
		sfrTable.setItems(sfrTableContent);
	}

	private void outputSfrToQuickAccess(byte[] sfr) {
		Platform.runLater(() -> {
			pclLabel.setText(String.valueOf(sfr[0x02]));
			pclathLabel.setText(String.valueOf(sfr[0x0A]));
			cLabel.setText(String.valueOf(StatusRegisterHelper.getCFlag()));
			dcLabel.setText(String.valueOf(StatusRegisterHelper.getDCFlag()));
			zLabel.setText(String.valueOf(StatusRegisterHelper.getZFlag()));
		});
	}

	private void outputStack(short[] stack) {
		ObservableList<StackModel> stackTableContent = FXCollections.observableArrayList();
		for (int i = 0; i < 8 ; i++) {
			stackTableContent.add(new StackModel(i, stack[i]));
		}
		stackTable.setItems(stackTableContent);
	}

	private void outputStackPointer(Integer stackPointer) {
		Platform.runLater(() -> {
			stackPointerLabel.setText(String.valueOf(stackPointer));
		});
	}

	private void outputToDebugConsole(String msg) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		debugConsole.appendText(dateFormat.format(new Date()) + ": " + msg + "\n");
	}
}
