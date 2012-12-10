/** This section option frame.
 * @author iamjoking
 */

package main.frame;

import main.*;
import common.view.*;
import common.util.*;
import common.util.logging.*;
import common.util.command.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;
import java.io.*;

public abstract class SectionOptionFrame extends OptionFrame {
	public final static int STATE_NEXT 		= 0;
	public final static int STATE_PREVIOUS 	= 1;
	public final static int STATE_FINISH 	= 2;
	public final static int STATE_CANCEL 	= 3;
	private final static int STATE_NONE		= 4;
	private final static int STATE_BUSY		= 5;
	private final static int STATE_DONE		= 6;
	
	public final static int FIRST_SECTION 	= 0;
	public final static int INTER_SECTION 	= 1;
	public final static int LAST_SECTION 	= 2;
	
	private int status;
	final private int type;
	
	protected Section section;
	
	protected MessagePane resultPane = new MessagePane("Result");
	protected ProgramPane programPane = new ProgramPane((Window)this);
	protected NoticeCommand noticeCommand;
	
	JButton jbBuild 	= new JButton("Build");
	JButton jbPrevious 	= new JButton("Previous");
	JButton jbNext 		= new JButton("Next");
	JButton jbGenerate 	= new JButton("Generate");
	JButton jbFinish 	= new JButton("Finish");
	JButton jbCancel 	= new JButton("Cancel");

	public SectionOptionFrame (int type, String title, String description, Icon icon) {
		super(title, description, icon);
		section = new Section(title);
		noticeCommand = new NoticeCommand(title,resultPane);
		this.type = type;
		
		addOptionPane(resultPane,false);
		addTitledOptionPane("Options",programPane,false);
		
		switch (type) {
			case FIRST_SECTION :
				addButton(jbGenerate); addButton(jbBuild);
				addButton(jbNext); addButton(jbCancel);
				jbNext.setEnabled(false);
				break;
			case INTER_SECTION :
				addButton(jbGenerate); addButton(jbBuild);
				addButton(jbPrevious); addButton(jbNext); addButton(jbCancel);
				jbNext.setEnabled(false);
				break;
			case LAST_SECTION :
				addButton(jbGenerate); addButton(jbBuild);
				addButton(jbPrevious); addButton(jbFinish); addButton(jbCancel);
				jbFinish.setEnabled(false);
				break;
			default :
				addButton(jbGenerate); addButton(jbBuild);
				addButton(jbNext); addButton(jbCancel);
				jbNext.setEnabled(false);
				break;				
		}
		
		jbBuild.addActionListener(new BuildActionListener());
		
		jbPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(STATE_PREVIOUS);
			}
		});
		
		jbNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(STATE_NEXT);
			}
		});
		
		jbGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCovered())
					JOptionPane.showMessageDialog(null, "Some parameter is not formulated");
				else {
					setSection();
					new CommandStringFrame(getSection().getCommandString()).setVisible(true);
				}
			}
		});
		
		jbFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(STATE_FINISH);
			}
		});
		
		jbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setStatus(STATE_CANCEL);
			}
		});
		
		java.util.List<Element> list = Config.getProgramElements(title.replaceAll(" ",""));
		for (int i = 0; i < list.size(); i++) {
			programPane.addProgram(new Program(list.get(i)));
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				setStatus(STATE_CANCEL);
				return ;
			}
		});		
		pack();
	}
	
	private void setStatus(int s) {
		switch (s) {
			case STATE_NEXT:
				setVisible(false);
				break;
			case STATE_PREVIOUS :
				setVisible(false);
				break;
			case STATE_FINISH :
				setVisible(false);
				break;				
			case STATE_CANCEL :
				dispose();
				break;
			case STATE_NONE :
				super.setStatusString("");
				jbNext.setEnabled(false);
				break;
			case STATE_BUSY :
				jbBuild.setEnabled(false);
				jbNext.setEnabled(false);
				jbFinish.setEnabled(false);
				super.setStatusString("Busy");
				break;				
			case STATE_DONE :
				super.setStatusString("Done");
				jbBuild.setEnabled(true);
				jbNext.setEnabled(true);
				jbFinish.setEnabled(true);
				break;
			default :
				return;
		}
		status = s;
	}
	
	public int getStatus() {
		return status;
	}
	
	public abstract boolean isCovered();
	public abstract void setSection();
	
	public Section getSection() {
		return section;
	}
	
	public void addProgram(Program program) {
		programPane.addProgram(program);
	}
	
	public void reShow() {
		setStatus(STATE_NONE);
		setVisible(true);
	}
	
	private class CommandStringFrame extends JDialog {
		String command;
		
		public CommandStringFrame(String cmd) {
			setModal(true);
			
			command = cmd;
			final JTextArea jta = new JTextArea(cmd);
			jta.setLineWrap(true);
			jta.setEditable(false);
			jta.setBackground(SystemColor.control);
			jta.setFont(new Font("Î¢ÈíÑÅºÚ", 0, 13));
			JScrollPane jsp = new JScrollPane(jta);
			
			JButton jbCopy = new JButton("Copy to clipboard");
			jbCopy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jta.selectAll();
					jta.copy();
				}
			});
			setLayout(new BorderLayout());
			add(jsp, BorderLayout.CENTER);
			add(jbCopy, BorderLayout.SOUTH);
			setSize(400,200);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
		}
	}
	
	private class BuildActionListener implements Runnable, ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (!isCovered())
				JOptionPane.showMessageDialog(null, "Some parameter is not formulated");
			else {
				setStatus(STATE_BUSY);
				setSection();
				resultPane.clear();
				new Thread(this).start();
			}
		}
		
		public void run() {
			Section section = getSection();
			try {
				noticeCommand.exec(section.getCommandString(), section.getWorkDirectory());
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				setStatus(STATE_DONE);
			}
		}
	}
}