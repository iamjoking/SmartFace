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
				case 2 :		// Feature Extraction
					if (optionFrames[step] == null)
						optionFrames[step] = new FeatureExtractionFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						section = ((FeatureExtractionFrame)optionFrames[step]).getSection();
						project.setSection(secNo,section);
						secNo++; step++;
					} else 
						step = -1;
					break;
				case 3 :		// Training Set Generation
					if (optionFrames[step] == null)
						optionFrames[step] = new TrainingSetGenerationFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						section = ((TrainingSetGenerationFrame)optionFrames[step]).getSection();
						project.setSection(secNo,section);
						secNo++; step++;
					} else 
						step = -1;
					break;
				case 4 :		// Sample Combination
					if (optionFrames[step] == null)
						optionFrames[step] = new SampleCombinationFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						section = ((SampleCombinationFrame)optionFrames[step]).getSection();
						project.setSection(secNo,section);
						secNo++; step++;
					} else 
						step = -1;
					break;
				case 5 :		// Classifier Training
					if (optionFrames[step] == null)
						optionFrames[step] = new ClassifierTrainingFrame();
					optionFrames[step].setVisible(true);
					if (optionFrames[step].isDisplayable()) { 
						section = ((ClassifierTrainingFrame)optionFrames[step]).getSection();
						project.setSection(secNo,section);
						secNo++; step++;
					} else 
						step = -1;
					break;
				default :
					step = -1;
					break;
			}
		}
		
		project.save();
		setProject(project);
	}
	
	public ProjectPane(File file) {
		this(new Project(file));
	}
	
	public ProjectPane(Project project) {
		setProject(project);
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		removeAll();
		this.project = project;
		setLayout(new BorderLayout());
		ChainPanel cp = new ChainPanel();
		JScrollPane jsp = new JScrollPane(cp);
		for (int i = 0; i < project.size(); i++) {
			JPanel sectionPanel = sectionPane(project.getSection(i));
			cp.add(sectionPanel);
		}
		add(jsp, BorderLayout.CENTER);
		updateUI();
	}
	
	private static JPanel sectionPane(Section section) {
		ChainPanel ret = new ChainPanel();
		ret.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), section.getTitle()));
		JTextField jtfHandler = new JTextField(section.getHandler());
		jtfHandler.setEditable(false);
		ret.add(OptionFrame.optionItem("Handler :",jtfHandler));
		ret.add(new JLabel("IO Options :"));
		for (int i = 0; i < section.getIoOptionSize(); i++) {
			JPanel jp = new JPanel(new BorderLayout());
			JLabel jlName = new JLabel(section.getIoOptionName(i));
			jlName.setPreferredSize(new Dimension(100,20));
			jlName.setHorizontalAlignment(SwingConstants.CENTER);
			jp.add(jlName,BorderLayout.WEST);
			if (!section.getIoOptionValue(i).equals("")) {
				JTextArea jtaValue = new JTextArea(section.getIoOptionValue(i));
				jtaValue.setEditable(false);
				jtaValue.setLineWrap(true);
				jp.add(jtaValue,BorderLayout.CENTER);
			}
			ret.add(jp);
		}
		ret.add(new JLabel("Options : "));
		for (int i = 0; i < section.getOptionSize(); i++) {
			JPanel jp = new JPanel(new BorderLayout());
			JLabel jlName = new JLabel(section.getOptionName(i));
			jlName.setPreferredSize(new Dimension(100,20));
			jlName.setHorizontalAlignment(SwingConstants.CENTER);
			jp.add(jlName,BorderLayout.WEST);
			if (!section.getOptionValue(i).equals("")) {			
				JTextArea jtaValue = new JTextArea(section.getOptionValue(i));
				jtaValue.setEditable(false);
				jtaValue.setLineWrap(true);
				jp.add(jtaValue,BorderLayout.CENTER);
			}
			ret.add(jp);
		}		
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