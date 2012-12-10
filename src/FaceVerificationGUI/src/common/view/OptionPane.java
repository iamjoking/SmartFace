/** The option pane.
 * @author iamjoking
 */

package common.view;

import javax.swing.*;
import javax.swing.BorderFactory.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;

import java.util.*;
import java.io.*;

public class OptionPane extends JPanel {
	ArrayList<JPanel> options = new ArrayList<JPanel>();
	Icon addIcon = new ImageIcon("res/pic/AddAnOption.png");
	Icon removeIcon = new ImageIcon("res/pic/RemoveAnOption.png");
	JPanel jpBody = new JPanel(new BorderLayout());
	
	public OptionPane() {
		this("Options");
	}
	
	public OptionPane (String title) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
		JPanel jpHead = new JPanel(new BorderLayout());
		JLabel jlName = new JLabel("Name");
		jlName.setPreferredSize(new Dimension(75,25));
		JLabel jlValue = new JLabel("Value");
		IconButton jlAddIcon = new IconButton(addIcon);
		jlAddIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlAddIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				addOption();
			}
		});
		jpHead.add(jlName, BorderLayout.WEST);
		jpHead.add(jlValue, BorderLayout.CENTER);
		jpHead.add(jlAddIcon, BorderLayout.EAST);
	
		JPanel jpMain = new JPanel(new BorderLayout());
		JScrollPane jsp = new JScrollPane(jpMain);
		jpMain.add(jpBody, BorderLayout.NORTH);
		add(jpHead,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		setPreferredSize(new Dimension(500,150));
	}
	
	public void addOption() {
		final JPanel newOption = new JPanel(new BorderLayout(10,10));
		JTextField jtfName = new JTextField(5);
		JTextField jtfValue = new JTextField(30);
		IconButton jlRemoveIcon = new IconButton(removeIcon);
		jlRemoveIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlRemoveIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				removeOption(newOption);
			}
		});	
		newOption.add(jtfName, BorderLayout.WEST);
		newOption.add(jtfValue, BorderLayout.CENTER);
		newOption.add(jlRemoveIcon, BorderLayout.EAST);
		options.add(newOption);
		jpBody.add(newOption,BorderLayout.NORTH);
		JPanel temp = new JPanel(new BorderLayout());
		jpBody.add(temp, BorderLayout.CENTER);
		jpBody = temp;
		updateUI();
	}
	
	public void removeOption(JPanel which) {
		which.removeAll();
		options.remove(which);
		updateUI();
	}
	
	private void removeEmptyOption() {
		ArrayList<JPanel> emptyOptions = new ArrayList<JPanel>();
		for (int i = 0; i < options.size(); i++) {
			if (((JTextField)options.get(i).getComponent(0)).getText().equals(""))
				emptyOptions.add(options.get(i));
		}
		for (int i = 0; i < emptyOptions.size(); i++) {
			removeOption(emptyOptions.get(i));
		}
	}
	
	public String getOptionName(int index) {
		if (index < 0 || index >= numOfOptions())
			return null;
		return ((JTextField)options.get(index).getComponent(0)).getText();
	}
	
	public String getOptionValue(int index) {
		if (index < 0 || index >= numOfOptions())
			return null;
		return ((JTextField)options.get(index).getComponent(1)).getText();	
	}
	
	public int numOfOptions() {
		removeEmptyOption();
		return options.size();
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.add(new OptionPane());
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


}