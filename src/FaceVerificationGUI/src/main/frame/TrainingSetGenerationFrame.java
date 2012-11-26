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

public class TrainingSetGenerationFrame extends OptionFrame {
	Section section = new Section("Training Set Generation");
	final static String[] programs = new String[] {"Strategy 1","Strategy 1","Browse for the program using other strategy..."};
	final static String[] handlers = new String[] {"D:\\Study\\code\\GitHub\\SmartFace\\bin\\tsGen1.exe","D:\\Study\\code\\GitHub\\SmartFace\\bin\\tsGen2.exe"};
	JTextField jtfInput = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JTextField jtfFileName = new JTextField(30);
	JComboBox jcbbProgram = new JComboBox();
	OptionPane pOptionPane;
	
	public TrainingSetGenerationFrame () {
		super("Training Set Generation", "Generate a training set", new ImageIcon("res/pic/trsgen.png"));
		ChainPanel ioPane = new ChainPanel();
		JButton jbInput = new JButton("Browse");
		JPanel jpInput = OptionFrame.optionItem("Input Features :",jtfInput,jbInput);
		jbInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(InitialInformation.getCwd());
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
				JFileChooser openFileChooser = new JFileChooser(InitialInformation.getCwd());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		
		JPanel jpOutputFileName = OptionFrame.optionItem("Output file name :", jtfFileName);
		ioPane.add(jpInput); ioPane.add(jpOutput); ioPane.add(jpOutputFileName);
		addTitledOptionPane("IO",ioPane);
		
		ChainPanel jpOptions = new ChainPanel();
		jcbbProgram.setEditable(true);
		jcbbProgram.setModel(new DefaultComboBoxModel(programs));
		jcbbProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (jcbbProgram.getSelectedIndex() == jcbbProgram.getItemCount() - 1) {
					JFileChooser chooser = new JFileChooser(InitialInformation.getCwd());
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.setMultiSelectionEnabled(false);
					int operation = chooser.showOpenDialog(null);
					if (operation == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						jcbbProgram.setSelectedItem(file.getPath());
					}
				}
            }
        });
		JPanel jpProgram = OptionFrame.optionItem("Program :", jcbbProgram);
		pOptionPane = new OptionPane("Program Options");
		jpOptions.add(jpProgram);
		jpOptions.add(pOptionPane);
		addTitledOptionPane("Options",jpOptions);

		JButton jbBuild = new JButton("Build");
		JButton jbNext = new JButton("Next");
		JButton jbCancel = new JButton("Cancel");
		addButton(jbBuild); addButton(jbCancel); addButton(jbNext);
		jbBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCovered())
					JOptionPane.showMessageDialog(null, "Some parameter is not formulated");
				else {
					setSection();
					getSection().build();
				}
			}
		});
		
		jbNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!getSection().isBuilt())
					JOptionPane.showMessageDialog(null, "This section has not been built.");
				else {
					setVisible(false);
				}				
			}
		});		
		
		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		jtfInput.setEditable(false);
		jtfOutput.setEditable(false);
		pack();
	}
	
	private boolean isCovered() {
		if (jtfInput.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jtfFileName.getText().equals("") ||
			jcbbProgram.getSelectedItem().toString().equals("")) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	private void setSection() {
		section.setIoOption("i",jtfInput.getText());
		section.setIoOption("d",jtfOutput.getText() + System.getProperty("file.separator"));
		section.setIoOption("o",jtfFileName.getText());

		if (jcbbProgram.getSelectedIndex() >= 0)
			section.setHandler(handlers[jcbbProgram.getSelectedIndex()]);
		else
			section.setHandler(jcbbProgram.getSelectedItem().toString());
		for (int i = 0; i < pOptionPane.numOfOptions(); i++) {
			section.setOption(pOptionPane.getOptionName(i),pOptionPane.getOptionValue(i));
		}
		section.setCovered(true);
	}
	
	public Section getSection() {
		return section;
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		TrainingSetGenerationFrame frame = new TrainingSetGenerationFrame();
		frame.setVisible(true);
		JFrame jframe = new JFrame();
		JTextArea jta = new JTextArea(frame.getSection().getCommandString());
		jta.setLineWrap(true);
		jframe.add(jta);
		jframe.setSize(600,200);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}


}