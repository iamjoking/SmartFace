/** The program pane.
 * @author iamjoking
 */

package common.view;

import common.util.*;
import main.*;

import javax.swing.*;
import javax.swing.BorderFactory.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;

import java.util.*;
import java.io.*;

public class ProgramPane extends JPanel {
	Window parent;
	ArrayList<Program> programs = new ArrayList<Program>();
	ArrayList<SimpleOptionPane> optionPanes = new ArrayList<SimpleOptionPane>();
	JPanel body = new JPanel(new BorderLayout());
	OptionPane optionPane = new OptionPane("Program Options");
	JComboBox jcbbProgram = new JComboBox();
	
	public ProgramPane(Window parent) {
		this.parent = parent;
		JPanel jpMain = new JPanel(new BorderLayout());
		JPanel jpHead = OptionFrame.optionItem("Program :",jcbbProgram);
		body.setPreferredSize(optionPane.getPreferredSize());
		JScrollPane jcBody = new JScrollPane(body);
		jcBody.setBorder(null);
		jpMain.add(jpHead, BorderLayout.NORTH);
		jpMain.add(jcBody, BorderLayout.CENTER);
		
		jcbbProgram.setEditable(true);
		jcbbProgram.setSelectedIndex(-1);
		jcbbProgram.setModel(new DefaultComboBoxModel<String>());
		jcbbProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
				int index = jcbbProgram.getSelectedIndex();
				if (jcbbProgram.getItemCount() == 0)
					return ;
				if (index == jcbbProgram.getItemCount() - 1) {
					JFileChooser chooser = new JFileChooser(Config.getPresentWorkDirectory());
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.setMultiSelectionEnabled(false);
					int operation = chooser.showOpenDialog(null);
					if (operation == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						jcbbProgram.setSelectedItem(file.getPath());
						body.removeAll();
						body.add(optionPane ,BorderLayout.CENTER);
						body.setPreferredSize(optionPane.getPreferredSize());
						updateUI();
						getParentWindow().pack();
					}
				} else {
					if (index >= 0 && index < programs.size()) {
						body.removeAll();
						body.add(optionPanes.get(index) ,BorderLayout.CENTER);
						body.setPreferredSize(optionPanes.get(index).getPreferredSize());
						updateUI();
						getParentWindow().pack();
					}
				}
            }
        });
		
		setLayout(new BorderLayout());
		add(jpMain, BorderLayout.CENTER);
		refresh();
		parent.pack();
	}

	private void refresh() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel<String>();
		jcbbProgram.setSelectedIndex(-1);
		int size = programs.size();
		dcbm.removeAllElements();
		for (int i = 0; i < size; i++) {
			dcbm.addElement(programs.get(i).getName());
		}
		dcbm.addElement("Browse for other programs...");
		jcbbProgram.setModel(dcbm);
		
		if (jcbbProgram.getItemCount() <= 1)
			jcbbProgram.setSelectedIndex(-1);
		else
			jcbbProgram.setSelectedIndex(0);
		updateUI();

		if (jcbbProgram.getItemCount() <= 1)
			jcbbProgram.setSelectedIndex(-1);
		else
			jcbbProgram.setSelectedIndex(0);
	}

	public Window getParentWindow() {
		return parent;
	}
	
	public void addProgram(Program program) {
		programs.add(program);
		optionPanes.add(new SimpleOptionPane(program));
		refresh();
	}
	
	public boolean isCovered() {
		int index = jcbbProgram.getSelectedIndex();
		if (index < 0) {
			File exe = new File((String)(jcbbProgram.getSelectedItem()));
			if (exe.exists())
				return true;
			else
				return false;
		} else {
			return optionPanes.get(index).isCovered();
		}
	}
	
	public void setSection(Section section) {
		if (!isCovered())
			return ;
		
		int index = jcbbProgram.getSelectedIndex();
		if (index < 0) {
			section.setHandler((String)(jcbbProgram.getSelectedItem()));
			int size = optionPane.numOfOptions();
			for (int i = 0; i < size; i++)
				section.setOption(optionPane.getOptionName(i),optionPane.getOptionValue(i));
		} else {
			section.setHandler(programs.get(index).getPath());
			optionPanes.get(index).setSection(section);
		}
	}
	
	private class SimpleOptionPane extends JPanel{
		ArrayList<JCheckBox> nonvalueOpt = new ArrayList<JCheckBox>();
		ArrayList<JTextField> valueOpt = new ArrayList<JTextField>();
		ArrayList<JTextField> optionalValueOpt = new ArrayList<JTextField>();
		
		public SimpleOptionPane(Program program) {
			super();
			ChainPanel cpMain = new ChainPanel(0);
			JTextArea jta = new JTextArea(program.getDescription());
			jta.setFont(new java.awt.Font("Monospaced", 2, 13));
			jta.setEditable(false);
			jta.setLineWrap(true);
			jta.setWrapStyleWord(true);
			jta.setBackground(java.awt.SystemColor.control);
			ChainPanel A = new ChainPanel(0);
			ChainPanel B = new ChainPanel(0);
			ChainPanel C = new ChainPanel(0);
			cpMain.add(jta); cpMain.add(A); cpMain.add(B); cpMain.add(C);
			
			int size = program.getNonvalueOptionsSize();
			for (int i = 0; i < size; i++) {
			
				JPanel jpTemp = new JPanel(new BorderLayout());
				String name = program.getNonvalueOptionName(i);
				JCheckBox jcbTemp = new JCheckBox(name);
				jcbTemp.setHorizontalTextPosition(SwingConstants.LEADING);
				nonvalueOpt.add(jcbTemp);
				jpTemp.add(jcbTemp,BorderLayout.EAST);
				jpTemp.add(new JLabel(program.getDescription(name)),BorderLayout.WEST);
				A.add(jpTemp);
			}
			
			size = program.getValueOptionsSize();
			for (int i = 0; i < size; i++) {
				JPanel jpTemp = new JPanel(new BorderLayout());
				String name = program.getValueOptionName(i);
				JLabel jpDes = new JLabel(program.getDescription(name));
				JPanel jpNameAndValue = new JPanel(new BorderLayout(10,0));
				JPanel jpName = new JPanel(new BorderLayout(0,0));
				JLabel jlName = new JLabel(name);
				JLabel jlRequired = new JLabel("*");
				jpName.add(jlRequired,BorderLayout.WEST);
				jpName.add(jlName,BorderLayout.EAST);
				jlRequired.setForeground(Color.RED);
				JTextField jtfTemp = new JTextField(10);
				jtfTemp.setName(name);
				valueOpt.add(jtfTemp);
				
				jpNameAndValue.add(jpName,BorderLayout.WEST);
				jpNameAndValue.add(jtfTemp,BorderLayout.CENTER);
				jpTemp.add(jpDes,BorderLayout.WEST);
				jpTemp.add(jpNameAndValue,BorderLayout.EAST);
				B.add(jpTemp);
			}
			
			size = program.getOptionalValueOptionsSize();
			for (int i = 0; i < size; i++) {
				JPanel jpTemp = new JPanel(new BorderLayout());
				String name = program.getOptionalValueOptionName(i);
				JLabel jpDes = new JLabel(program.getDescription(name));
				JPanel jpNameAndValue = new JPanel(new BorderLayout(10,0));
				JLabel jlName = new JLabel(name);
				JTextField jtfTemp = new JTextField(10);
				jtfTemp.setName(name);
				optionalValueOpt.add(jtfTemp);
				
				jpNameAndValue.add(jlName,BorderLayout.WEST);
				jpNameAndValue.add(jtfTemp,BorderLayout.CENTER);
				jpTemp.add(jpDes,BorderLayout.WEST);
				jpTemp.add(jpNameAndValue,BorderLayout.EAST);
				C.add(jpTemp);
			}
			
			setLayout(new BorderLayout());
			add(cpMain,BorderLayout.CENTER);
		}
		
		public boolean isCovered () {
			int size = valueOpt.size();
			for (int i = 0; i < size; i++)
				if (valueOpt.get(i).getText().equals(""))
					return false;
			return true;
		}

		public void setSection(Section section) {
			if (!isCovered())
				return ;
			
			int size = nonvalueOpt.size();
			for (int i = 0; i < size; i++) {
				JCheckBox temp = nonvalueOpt.get(i);
				if (temp.isSelected())
					section.setOption(temp.getText(),"");
			}
			
			size = valueOpt.size();
			for (int i = 0; i < size; i++) {
				JTextField jtfTemp = valueOpt.get(i);
				section.setOption(jtfTemp.getName(),jtfTemp.getText());
			}
			
			size = optionalValueOpt.size();
			for (int i = 0; i < size; i++) {
				JTextField jtfTemp = optionalValueOpt.get(i);
				if (jtfTemp.getText().equals(""))
					continue ;
				section.setOption(jtfTemp.getName(),jtfTemp.getText());
			}
		}
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		ProgramPane pp = new ProgramPane(frame);
		Program p1 = new Program("Local Binary Pattern","D:\\Study\\code\\GitHub\\SmartFace\\bin\\lbp.exe","Description");
		p1.addNonvalueOption("g","Gray scale");
		p1.addNonvalueOption("E","Equal");
		p1.addValueOption("x","Extension(JPG, PNG, BMP and so on)");
		p1.addOptionalValueOption("n","Output name");
		pp.addProgram(p1);
		frame.add(pp);
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}