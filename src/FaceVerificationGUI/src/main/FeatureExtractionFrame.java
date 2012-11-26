/** This dialog is used to extract features.
 * @author iamjoking
 */

package main;

import common.view.*;
import common.util.*;
import common.util.logging.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;
import java.io.*;

public class FeatureExtractionFrame extends OptionFrame {
	Section section = new Section("Feature Extraction");
	final static String[] programs = new String[] {"Local Binary Pattern","Browse for other feature extractor"};
	final static String[] handlers = new String[] {"D:\\Study\\code\\GitHub\\SmartFace\\bin\\lbp.exe"};
	JTextField jtfInput = new JTextField(30);
	JTextField jtfOutput = new JTextField(30);
	JComboBox jcbbProgram = new JComboBox();
	JPanel jpOtherOptions;
	
	public FeatureExtractionFrame () {
		super("Feature Extraction", "Set the feature extraction options", new ImageIcon("res/pic/featureextr.png"));
		JPanel ioPane = new JPanel(new GridLayout(0,1,10,10));
		JButton jbInut = new JButton("Browse");
		JPanel jpInput = OptionFrame.optionItem("Input images :",jtfInput,jbInut);
		jbInut.addActionListener(new ActionListener() {
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
		JPanel jpProgram = OptionFrame.optionItem("Extractor Program :", jcbbProgram);
		OptionPane pOptionPane = new OptionPane("Program Options");
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
			jcbbProgram.getSelectedItem().toString().equals("")) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	private void setSection() {
		section.setIoOption("i",jtfInput.getText());
		section.setIoOption("d",jtfOutput.getText());
		if (jcbbProgram.getSelectedIndex() >= 0)
			section.setHandler(handlers[jcbbProgram.getSelectedIndex()]);
		else
			section.setHandler(jcbbProgram.getSelectedItem().toString());
		section.setCovered(true);
	}
	
	public Section getSection() {
		return section;
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		FeatureExtractionFrame frame = new FeatureExtractionFrame();
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