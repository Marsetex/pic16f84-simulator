package de.marsetex.pic16f84sim.ui.models;

import javafx.beans.property.SimpleStringProperty;

public class CodeModel {

    private SimpleStringProperty isExecuted;
    private SimpleStringProperty codeLine;

    public CodeModel(String isExecuted, String codeLine) {
        this.isExecuted = new SimpleStringProperty(isExecuted);
        this.codeLine = new SimpleStringProperty(codeLine);
    }

    public String getIsExecuted() {
        return isExecuted.get();
    }

    public void setIsExecuted(String x) {
        isExecuted = new SimpleStringProperty(x);
    }

    public String getCodeLine() {
        return codeLine.get();
    }
}
