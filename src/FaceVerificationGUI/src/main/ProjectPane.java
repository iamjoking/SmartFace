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
	Project project = null;
	
	public ProjectPane() {
	// New a project.
		File projFile;
		Project project;
		Section section;
		NewProjectFrame newProjFrame = new NewProjectFrame();
		newProjFrame.setVisible(true);
		
		if (!newProjFrame.isDisplayable())
			return ;

		projFile = newProjFrame.getProjectFile();
		project = new Project(projFile);
		File lastWorkDirectory = Config.getPresentWorkDirectory();
		Config.changePresentWorkDirectory(projFile.getParentFile());
		SectionOptionFrame[] secOptionFrames = {
			new PreprocessFrame(),
			new FeatureExtractionFrame(),
			new TrainingSetGenerationFrame(),
			new SampleCombinationFrame(),
			new ClassifierTrainingFrame()};
		
		if (sectionFrameSwitcher(secOptionFrames,project)) {
			project.save();
			setProject(project);
		}
		Config.changePresentWorkDirectory(lastWorkDirectory);
	}
	
	boolean sectionFrameSwitcher(SectionOptionFrame[] secOptionFrames, Project project) {
		int step = 0;
		int returnValue;
		while (step < secOptionFrames.length) {
			if (step < 0)
				return false;
			
			secOptionFrames[step].setVisible(true);
			returnValue = secOptionFrames[step].getStatus();
			switch (returnValue) {
				case SectionOptionFrame.STATE_NEXT :
					project.setSection(step,secOptionFrames[step].getSection());
					step++; break;
					
				case SectionOptionFrame.STATE_PREVIOUS :
					step--; break;
					
				case SectionOptionFrame.STATE_FINISH :
					project.setSection(step,secOptionFrames[step].getSection());
					step++; break;
					
				case SectionOptionFrame.STATE_CANCEL :
					return false;
					
				default :
					return false;
			}
		}

		return true;
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
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		for (int i = 0; i < project.size(); i++) {
			JPanel sectionPanel = sectionPane(project.getSection(i));
			cp.add(sectionPanel);
		}
		add(jsp, BorderLayout.CENTER);
		updateUI();
	}
	
	private static JPanel sectionPane(Section section) {
		ChainPanel ret = new ChainPanel();
		ret.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), section.getTitle(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Serif", 3, 16)));
		JTextField jtfHandler = new JTextField(section.getHandler());
		jtfHandler.setEditable(false);
		ret.add(OptionFrame.optionItem("Handler :",jtfHandler));
		ret.add(new JLabel("IO Options :"));
		for (int i = 0; i < section.getIoOptionSize(); i++) {
			JPanel jp = new JPanel(new BorderLayout());
			JLabel jlName = new JLabel(section.getIoOptionName(i));
			jlName.setPreferredSize(new Dimension(100,20));
			jlName.setHorizontalAlignment(SwingConstants.CENTER);
			ChainPanel cp = new ChainPanel(0,0);
			cp.add(jlName);
			jp.add(cp,BorderLayout.WEST);
			if (!section.getIoOptionValue(i).equals("")) {
				LongTextPane ltpValue = new LongTextPane(section.getIoOptionValue(i));
				jp.add(ltpValue,BorderLayout.CENTER);
			}
			ret.add(jp);
		}
		ret.add(new JLabel("Options : "));
		for (int i = 0; i < section.getOptionSize(); i++) {
			JPanel jp = new JPanel(new BorderLayout());
			JLabel jlName = new JLabel(section.getOptionName(i));
			jlName.setPreferredSize(new Dimension(100,20));
			jlName.setHorizontalAlignment(SwingConstants.CENTER);
			ChainPanel cp = new ChainPanel(0,0);
			cp.add(jlName);
			jp.add(cp,BorderLayout.WEST);
			if (!section.getOptionValue(i).equals("")) {			
				LongTextPane ltpValue = new LongTextPane(section.getOptionValue(i));
				jp.add(ltpValue,BorderLayout.CENTER);
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