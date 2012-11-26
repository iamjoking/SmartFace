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

public class ClassifierTrainingFrame extends OptionFrame {
	Section section = new Section("Classifier Training");
	final static String[] programs = new String[] {"Strategy 1","Strategy 1","Browse for other classifier..."};
	final static String[] handlers = new String[] {"classifier1.exe","classifier2.exe"};
	JTextField jtfInputPos = new JTextField(30);
	JTextField jtfInputNeg = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JComboBox jcbbProgram = new JComboBox();
	OptionPane pOptionPane;
	
	public ClassifierTrainingFrame () {
		super("Classifier Training", "Train a classifier", new ImageIcon("res/pic/traincla.png"));
		ChainPanel ioPane = new ChainPanel();
		JButton jbInputPos = new JButton("Browse");
		JPanel jpInputPos = OptionFrame.optionItem("Positive Samples :",jtfInputPos,jbInputPos);
		jbInputPos.addActionListener(new ActionListener() {
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
					jtfInputPos.setText(sBuffer.toString());
				}
			}
		});
	
		JButton jbInputNeg = new JButton("Browse");
		JPanel jpInputNeg = OptionFrame.optionItem("Negative Samples :",jtfInputNeg,jbInputNeg);
		jbInputNeg.addActionListener(new ActionListener() {
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
					jtfInputNeg.setText(sBuffer.toString());
				}
			}
		});
	
		JButton jbOutput = new JButton("Browse");
		JPanel jpOutput = OptionFrame.optionItem("Output Model Path :",jtfOutput,jbOutput);
		jbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(InitialInformation.getCwd());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		
		ioPane.add(jpInputPos); ioPane.add(jpInputNeg); ioPane.add(jpOutput);
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
		JPanel jpProgram = OptionFrame.optionItem("Classifier Trainer :", jcbbProgram);
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
		
		jtfInputNeg.setEditable(false);
		jtfInputPos.setEditable(false);
		jtfOutput.setEditable(false);
		pack();
	}
	
	private boolean isCovered() {
		if (jtfInputPos.getText().equals("") ||
			jtfInputNeg.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jcbbProgram.getSelectedItem().toString().equals("")) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	private void setSection() {
		section.setIoOption("p",jtfInputPos.getText());
		section.setIoOption("n",jtfInputNeg.getText());
		section.setIoOption("o",jtfOutput.getText() + System.getProperty("file.separator"));

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
		ClassifierTrainingFrame frame = new ClassifierTrainingFrame();
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