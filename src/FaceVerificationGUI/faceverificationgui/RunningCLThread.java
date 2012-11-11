/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faceverificationgui;

import java.io.IOException;

/**
 *
 * @author bsidb
 */
public class RunningCLThread {

    String commandLine;
    String stateString = "Ready to Run!";
    javax.swing.JLabel indicator;

    public RunningCLThread(String commandLine, javax.swing.JLabel indicator) {
        this.commandLine = commandLine;
        this.indicator = indicator;
        if (indicator != null) {
            indicator.setText("Ready to Run!");
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                indicator.setText("Running...");
                stateString = "Finished.";
                try {
                    Process currentProcess = Runtime.getRuntime().exec(commandLine);
                    currentProcess.waitFor();
                } catch (Exception ex) {
                    stateString = ex.toString();
                }
                indicator.setText(stateString);
                indicator.setToolTipText(stateString);
            }
            
        }).start();

    }
}
