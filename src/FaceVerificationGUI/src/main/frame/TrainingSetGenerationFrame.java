/** This dialog is used to generate a training set.
 * @author iamjoking
 */

package main.frame;

import main.*;
import common.view.*;
import common.util.*;
import common.util.logging.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;
import java.io.*;

public class TrainingSetGenerationFrame extends SectionOptionFrame {
	JTextField jtfInput = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JTextField jtfFileName = new JTextField(30);
	
	public TrainingSetGenerationFrame () {
		super(SectionOptionFrame.INTER_SECTION, "Training Set Generation",
			"Generate a training set", new ImageIcon("res/pic/TrainingSetGeneration.png"));
		ChainPanel ioPane = new ChainPanel();
		JButton jbInput = new JButton("Browse");
		JPanel jpInput = OptionFrame.optionItem("Input Features :",jtfInput,jbInput);
		jbInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				openFileChooser.setMultiSelectionEnabled(true);
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File[] files = openFileChooser.getSelectedFiles();
					StringBuffer sBuffer = new StringBuffer();
					for (int i = 0; i < files.length; i++) {
						sBuffer.append(files[i].getAbsolutePath() + 
								System.getProperty("path.separator"));
					}
					jtfInput.setText(sBuffer.toString());
				}
			}
		});
		
		JButton jbOutput = new JButton("Browse");
		JPanel jpOutput = OptionFrame.optionItem("Output path :",jtfOutput,jbOutput);
		jbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		
		JPanel jpOutputFileName = OptionFrame.optionItem("Output file name :", jtfFileName);
		ioPane.add(jpInput); ioPane.add(jpOutput); ioPane.add(jpOutputFileName);
		addTitledOptionPane("IO",ioPane);

//		jtfInput.setEditable(false);
//		jtfOutput.setEditable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public boolean isCovered() {
		if (jtfInput.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jtfFileName.getText().equals("") ||
			!programPane.isCovered()) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	public void setSection() {
		section.setIoOption("i",jtfInput.getText());
		section.setIoOption("d",jtfOutput.getText() + System.getProperty("file.separator"));
		section.setIoOption("o",jtfFileName.getText());
		programPane.setSection(getSection());		
		section.setCovered(true);
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		TrainingSetGenerationFrame frame = new TrainingSetGenerationFrame();
		frame.setVisible(true);
	}


}