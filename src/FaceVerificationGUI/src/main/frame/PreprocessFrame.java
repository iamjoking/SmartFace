/** This dialog is used to set preprocessing options.
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

public class PreprocessFrame extends SectionOptionFrame {
	JTextField jtfImages;
	JTextField jtfOutput;
	
	public PreprocessFrame () {
		super(SectionOptionFrame.FIRST_SECTION,"Preprocess",
			"Carry out some pretreament operations", new ImageIcon("res/pic/Preprocess.png"));
		
		JPanel ioPane = new ChainPanel();
		
		jtfImages = new JTextField(30);
		JButton jbImages = new JButton("Browse");
		jbImages.addActionListener(new ActionListener() {
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
					jtfImages.setText(sBuffer.toString());
				}
			}
		});
		JPanel jpImages = OptionFrame.optionItem("Input images :",jtfImages,jbImages);
		
		jtfOutput = new JTextField(30);
		JButton jbOutput = new JButton("Browse");
		jbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(Config.getPresentWorkDirectory());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		JPanel jpOutput = OptionFrame.optionItem("Output path :",jtfOutput,jbOutput);

		ioPane.add(jpImages); ioPane.add(jpOutput);
		addTitledOptionPane("IO",ioPane);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public boolean isCovered() {
		if (jtfImages.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			!programPane.isCovered()) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	public void setSection() {
		section.setIoOption("i",jtfImages.getText());
		section.setIoOption("d",jtfOutput.getText() + System.getProperty("file.separator"));
		programPane.setSection(section);
		section.setCovered(true);
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		PreprocessFrame frame = new PreprocessFrame();
		frame.setVisible(true);
	}


}