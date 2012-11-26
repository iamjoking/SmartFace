/** This dialog is used to new a project
 * @author iamjoking
 */

package main;

import common.view.*;
import common.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;
import java.io.*;

public class NewProjectFrame extends OptionFrame {
	JTextField jtfName = new JTextField(30);
	JTextField jtfPath = new JTextField(30);
	
	public NewProjectFrame () {
		super("Create a new project","Enter your project file's name.",
			new ImageIcon("res/pic/newaproj.png"));
		
		int lineHeight = 25;
		JPanel jpBody = new JPanel(new GridLayout(0,1,10,10));
		JPanel jpName = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		JPanel jpPath = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		JLabel jlName = new JLabel("Project Name :");
		JLabel jlPath = new JLabel("Project Path :");
		JButton jbBrowse = new JButton("Browse");
		jbBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser openFileChooser = new JFileChooser(InitialInformation.getCwd());
				openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					jtfPath.setText(openFileChooser.getSelectedFile().getPath());
			}
		});
		jpName.add(jlName);
		jpName.add(jtfName);
		jpPath.add(jlPath);
		jpPath.add(jtfPath);
		jpPath.add(jbBrowse);
		jpBody.add(jpName);
		jpBody.add(jpPath);
		
		jtfPath.setEditable(false);
		jlName.setPreferredSize(new Dimension(90,lineHeight));
		jlPath.setPreferredSize(new Dimension(90,lineHeight));
		jtfName.setPreferredSize(new Dimension(0,lineHeight));
		jtfPath.setPreferredSize(new Dimension(0,lineHeight));
		jbBrowse.setPreferredSize(new Dimension(80,lineHeight + 2));

		setBody(jpBody);

		JButton jbOk = new JButton("Next");
		jbOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getProjectName().equals("")) {
					JOptionPane.showMessageDialog(null, "The name must not be empty.");
				} else if (getProjectPath().equals("")) {
					JOptionPane.showMessageDialog(null, "Please choose a directory.");
				} else if (getProjectFile().exists()) {
					JOptionPane.showMessageDialog(null, "A project with this name already exists.");
				} else
					setVisible(false);
			}
		});
		addButton(jbOk);
		JButton jbCancel = new JButton("Cancel");
		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		addButton(jbCancel);
		pack();
	}
	
	public String getProjectPath() {
		return jtfPath.getText();
	}
	
	public String getProjectName() {
		return jtfName.getText();
	}
	
	public File getProjectFile() {
		return new File(getProjectPath() + System.getProperty("file.separator")
			+ getProjectName() + Project.PROJECT_FILE_EXTENSION);
	}
	
	public static void main (String[] args) {
		NewProjectFrame frame = new NewProjectFrame();
		frame.setVisible(true);
		System.out.println("Project path is " + frame.getProjectPath());
		System.out.println("Project name is " + frame.getProjectName());
	}


}