package de.marsetex.pic16f84sim.ui.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AboutDialog {

    public static Alert getAboutDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("PIC16F84 Simulator - About");
        alert.setHeaderText("PIC16F84 Simulator");
        alert.setContentText("Developed by: Marcel Gruessinger \n" +
                "Version: 1.0.0");

        return alert;
    }
}
