/** This dialog is used to set face detection options.
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

public class FaceDetectionFrame extends OptionFrame {
	Section section = new Section("Face Detection");
	public final static String handler = "D:\\Study\\code\\GitHub\\SmartFace\\src\\FaceDetection\\Debug\\FaceDetection.exe";
	JTextField jtfImages;
	JTextField jtfOutput;
	JCheckBox jcbG;
	JCheckBox jcbE;
	JTextField jtfX;
	
	
	public FaceDetectionFrame () {
		super("Face Detection", "Set the face detection options", new ImageIcon("res/pic/facedetec.png"));
		int lineHeight = 25;
		JPanel ioPane = new JPanel(new GridLayout(0,1,10,10));
		JPanel jpImages = new JPanel(new FlowLayout());
		JLabel jlImages = new JLabel("Input images :");
		jtfImages = new JTextField(30);
		JButton jbImages = new JButton("Browse");
		jbImages.addActionListener(new ActionListener() {
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
					jtfImages.setText(sBuffer.toString());
				}
			}
		});
		
		JPanel jpOutput = new JPanel(new FlowLayout());
		JLabel jlOutput = new JLabel("Output path :");
		jtfOutput = new JTextField(30);
		JButton jbOutput = new JButton("Browse");
		jbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(InitialInformation.getCwd());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfOutput.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		
		jlImages.setPreferredSize(new Dimension(90,lineHeight));
		jlOutput.setPreferredSize(new Dimension(90,lineHeight));
		jtfImages.setPreferredSize(new Dimension(0,lineHeight));
		jtfOutput.setPreferredSize(new Dimension(0,lineHeight));
		jbImages.setPreferredSize(new Dimension(80,lineHeight + 2));
		jbOutput.setPreferredSize(new Dimension(80,lineHeight + 2));
		
		jpImages.add(jlImages); jpImages.add(jtfImages); jpImages.add(jbImages);
		jpOutput.add(jlOutput); jpOutput.add(jtfOutput); jpOutput.add(jbOutput);
		ioPane.add(jpImages); ioPane.add(jpOutput);
		
		addTitledOptionPane("IO",ioPane);
		
		JPanel jpOptions = new JPanel(new GridLayout(0,1,10,10));
		jcbG = new JCheckBox("g (gray scale)");
		jcbE = new JCheckBox("e ()");
		jtfX = new JTextField(10);
		jpOptions.add(OptionFrame.optionItem("File Extension :", jtfX)); 
		jpOptions.add(jcbG); jpOptions.add(jcbE);
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
		
		jtfImages.setEditable(false);
		jtfOutput.setEditable(false);
		pack();
	}
	
	private boolean isCovered() {
		if (jtfImages.getText().equals("") ||
			jtfOutput.getText().equals("") ||
			jtfX.getText().equals("")) {
			section.setCovered(false);
			return false;
		} else
			return true;
	}
	
	private void setSection() {
		section.setHandler(handler);
		section.setIoOption("i",jtfImages.getText());
		section.setIoOption("d",jtfOutput.getText() + System.getProperty("file.separator"));
		section.setOption("x",jtfX.getText());
		if (jcbG.isSelected()) section.setOption("g","");
		else section.removeOption("g");
		if (jcbE.isSelected()) section.setOption("e","");
		else section.removeOption("e");
		section.setCovered(true);
	}
	
	public Section getSection() {
		return section;
	}
	
	public static void main (String[] args) {
		LogFactory logFactory = new LogFactory();
		FaceDetectionFrame frame = new FaceDetectionFrame();
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