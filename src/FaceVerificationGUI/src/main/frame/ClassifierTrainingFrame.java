/** This dialog is used to train a classifier.
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

public class ClassifierTrainingFrame extends SectionOptionFrame {
	JTextField jtfInputPos = new JTextField(30);
	JTextField jtfInputNeg = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JTextField jtfName = new JTextField(30);
	
	public ClassifierTrainingFrame () {
		super(SectionOptionFrame.LAST_SECTION, "Classifier Training",
			"Train a classifier", new ImageIcon("res/pic/ClassifierGeneration.png"));

		ChainPanel ioPane = new ChainPanel();
		JButton jbInputPos = new JButton("Browse");
		JPanel jpInputPos = OptionFrame.optionItem("Positive Samples :",jtfInputPos,jbInputPos);
		jbInputPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					jtfInputPos.setText(openFileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	
		JButton jbInputNeg = new JButton("Browse");
		JPanel jpInputNeg = OptionFrame.optionItem("Negative Samples :",jtfInputNeg,jbInputNeg);
		jbInputNeg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					jtfInputNeg.setText(openFileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	
		JButton jbOutput = new JButton("Browse");
		JPanel jpOutput = OptionFrame.optionItem("Output Model Path :",jtfOutput,jbOutput);
		jbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		
		JPanel jpOutputName = OptionFrame.optionItem("Model file name :", jtfName);
		ioPane.add(jpInputPos); ioPane.add(jpInputNeg); ioPane.add(jpOutput); ioPane.add(jpOutputName);
		addTitledOptionPane("IO",ioPane);

//		jtfInputNeg.setEditable(false);
//		jtfInputPos.setEditable(false);
//		jtfOutput.setEditable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public boolean isCovered() {
		if (jtfInputPos.getText().equals("") ||
			jtfInputNeg.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jtfName.getText().equals("") ||
			!programPane.isCovered()) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	public void setSection() {
		section.setIoOption("p",jtfInputPos.getText());
		section.setIoOption("n",jtfInputNeg.getText());
		section.setIoOption("o",jtfOutput.getText() + System.getProperty("file.separator") + jtfName.getText());
		programPane.setSection(getSection());
		section.setCovered(true);
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		ClassifierTrainingFrame frame = new ClassifierTrainingFrame();
		frame.setVisible(true);
	}


}