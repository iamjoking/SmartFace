/** This dialog is used to combine samples.
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

public class SampleCombinationFrame extends OptionFrame {
	Section section = new Section("Sample Combination");
	final static String[] programs = new String[] {"Strategy 1","Strategy 1","Browse for the program using other strategy..."};
	final static String[] handlers = new String[] {"samCom1.exe","samCom2.exe"};
	JTextField jtfInput = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JComboBox jcbbProgram = new JComboBox();
	OptionPane pOptionPane;
	
	public SampleCombinationFrame () {
		super("Sample Combination", "Combine samples", new ImageIcon("res/pic/samCom.png"));
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
		
		ioPane.add(jpInput); ioPane.add(jpOutput);
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
		JButton jbGenCommand = new JButton("Generate");
		addButton(jbBuild); addButton(jbCancel); addButton(jbNext); addButton(jbGenCommand);
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

		jbGenCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Command String");
				JTextArea jta = new JTextArea(getSection().getCommandString());
				jta.setEditable(false);
				jta.setLineWrap(true);
				frame.setLayout(new BorderLayout());
				frame.add(jta,BorderLayout.CENTER);
				frame.setSize(500,200);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		
		jtfInput.setEditable(false);
		jtfOutput.setEditable(false);
		pack();
	}
	
	private boolean isCovered() {
		if (jtfInput.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jcbbProgram.getSelectedItem().toString().equals("")) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	private void setSection() {
		section.setIoOption("i",jtfInput.getText());
		section.setIoOption("d",jtfOutput.getText() + System.getProperty("file.separator"));
		
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
		SampleCombinationFrame frame = new SampleCombinationFrame();
		frame.setVisible(true);
	}


}