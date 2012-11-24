/** The project pane.
 * @author iamjoking
 */

package main;

import common.util.*;
import common.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class ProjectPane extends JPanel{
	Project project;
	
	public ProjectPane(File currentWorkDirectory) {
	// New a project.
		int step = 0;
		OptionFrame[] optionFrames = new OptionFrame[6];
		File projFile;
		
		switch (step) {
			case 0 :		// Project Name & Path
				optionFrames[0] = new NewProjectFrame("Create a new project", "Enter a project name.",
					new ImageIcon("res/pic/newaproj.png"),currentWorkDirectory);
				optionFrames[0].setVisible(true);
				projFile = ((NewProjectFrame)(optionFrames[0])).getProjectFile();
				System.out.println("Project File is " + projFile.getPath());
				step++; break;
			case 1 :		// Face Detection
				
				step++; break;
			
			case 2 :		// Training Set Generation
			
				step++; break;
			
			
			default :
				break;
		}
		
		while(true);
	}
	
	public ProjectPane(Project project) {
		setLayout(new GridLayout(project.size(),1));
		setProject(project);
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		removeAll();
		this.project = project;
		setLayout(new GridLayout(project.size(),1));
		for (int i = 0; i < project.size(); i++) {
			JPanel sectionPanel = sectionPane(project.getSection(i));
			add(sectionPanel);
		}
		repaint();
	}
	
	private static JPanel sectionPane(Section section) {
		JPanel ret = new JPanel();
		ret.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), section.getTitle()));
		ret.add(new JLabel("Handler : " + section.getHandler()));
		ret.add(new JLabel("Options : " + section.getCommandString()));
		return ret;
	}
	
	/** Test
	 */
	public static void main (String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.add(new ProjectPane(new File(".")));
		mainFrame.setSize(600,500);
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}