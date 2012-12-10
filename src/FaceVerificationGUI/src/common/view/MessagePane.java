/** The message pane.
 * @author iamjoking
 */

package common.view;

import common.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.LayoutManager.*;

public class MessagePane extends JPanel implements CanShow{
	String titleString;
	Color titleColor;
	Color titleBackground;
	Color messageColor;
	Color messageBackground;
	JTextArea messageArea;
	
	public MessagePane (String title) {
		this(title,new Color(255,255,255), new Color(58,143,183), new Color(240,240,240), new Color(50,50,50));
	}
	
	public MessagePane (String title, Color titleColor, Color titleBackground,
			Color messageColor, Color messageBackground) {
		this.titleString = title;
		this.titleColor = titleColor;
		this.titleBackground = titleBackground;
		this.messageColor = messageColor;
		this.messageBackground = messageBackground;
		
		JPanel jpHead = new JPanel(new GridLayout());
		jpHead.setBackground(titleBackground);
		JLabel jlTitle = new JLabel(titleString);
		jlTitle.setForeground(titleColor);
		jpHead.add(jlTitle);
		
		JPanel jpBody = new JPanel(new BorderLayout());
		messageArea = new JTextArea();
		messageArea.setBackground(messageBackground);
		messageArea.setForeground(messageColor);
		messageArea.setEditable(false);
		JScrollPane jsp = new JScrollPane(messageArea);
		jpBody.add(jsp, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(jpHead, BorderLayout.NORTH);
		add(jpBody, BorderLayout.CENTER);
		setPreferredSize(new Dimension(500,150));
	}
	
	public void append (String content) {
		messageArea.append(content + System.getProperty("line.separator"));
		messageArea.paintImmediately(messageArea.getBounds());
	}
	
	public void clear() {
		messageArea.setText("");
		messageArea.paintImmediately(messageArea.getBounds());
	}
	
	public static void main (String[] args) {
		JFrame mainFrame = new JFrame("Test MessagePane");
		mainFrame.add(new MessagePane("Console"));
		mainFrame.setSize(600,400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}


}