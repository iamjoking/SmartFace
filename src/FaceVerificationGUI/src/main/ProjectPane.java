/** The project pane.
 * @author iamjoking
 */

package main;

import main.frame.*;
import common.util.*;
import common.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class ProjectPane extends JPanel{
	Project project;
	
	public ProjectPane() {
	// New a project.
		int step = 0;
		int secNo = 0;
		OptionFrame[] optionFrames = new OptionFrame[6];
		File projFile;
		Section section;
		
		while (step >= 0) {
			switch (step) {
				case 0 :		// Project Name & Path
					if (optionFrames[step] == null)
						optionFrames[step] = new NewProjectFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						projFile = ((NewProjectFrame)(optionFrames[0])).getProjectFile();
						project = new Project(projFile);
						secNo = 0; step++; 
					}
					else
						step = -1;
					break;
				case 1 :		// Face Detection
					if (optionFrames[step] == null)
						optionFrames[step] = new FaceDetectionFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						section = ((FaceDetectionFrame)optionFrames[step]).getSection();
						project.setSection(secNo,section);
						secNo++; step++;
					} else 
						step = -1;
					break;
				case 2 :		// Training Set Generation
				
					step++; break;
				
				
				default :
					step = -1;
					break;
			}
		}
		
		while(true);
	}
	
	public ProjectPane(File file) {
		this(new Project(file));
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
		mainFrame.add(new ProjectPane());
		mainFrame.setSize(600,500);
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}