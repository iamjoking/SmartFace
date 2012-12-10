/** This pane is used to display long text.
 * @author iamjoking
 */

package common.view;

import common.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.File;
import java.util.*;
import java.io.*;
 
public class LongTextPane extends JPanel {
	JTextArea jta;
	String buffer;
	ChainPanel cpMain = new ChainPanel(0,0);
	ChainPanel cpLeft = new ChainPanel(0,0);
	Icon expandIcon = new ImageIcon("res/pic/expand.png");
	Icon collapseIcon = new ImageIcon("res/pic/collapse.png");
	IconButton button = new IconButton(collapseIcon, oneLineHeight, oneLineHeight);
	boolean expanded;
	public final static int oneLineHeight = 20;
	
	public LongTextPane () {
		this("",true);
	}
	
	public LongTextPane (String content) {
		this(content,true);
	}
	
	public LongTextPane (boolean needIcon) {
		this("",needIcon);
	}
	
	public LongTextPane (String content, boolean needIcon) {
		super();
		
		buffer = content;
		setLayout(new BorderLayout());
		
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				rotate();
			}
		});
		
		expanded = true;
		jta = new JTextArea(buffer);
		jta.setEditable(false);
		jta.setLineWrap(true);
		cpMain.add(jta);
		showIcon();
		
		add(cpLeft, BorderLayout.WEST);
		add(cpMain, BorderLayout.CENTER);
		rotate();
	}
	
	private void showIcon() {
		if (expanded)
			button.setIcon(collapseIcon);
		else
			button.setIcon(expandIcon);
		cpLeft.removeAll();
		cpLeft.add(button);
		cpLeft.updateUI();
	}
	
	private void removeIcon() {
		cpLeft.removeAll();
		JPanel jpEmpty = new JPanel();
		jpEmpty.setPreferredSize(new Dimension(oneLineHeight,oneLineHeight));
		cpLeft.add(jpEmpty);
	}
	
	public void rotate () {
		int width = jta.getWidth();
		int height = oneLineHeight;
		jta = new JTextArea(buffer);
		jta.setMargin(new Insets(0,0,0,0));
		jta.setEditable(false);
		jta.setLineWrap(true);
		jta.setBackground(SystemColor.controlHighlight);

		if (expanded) {
			button.setIcon(expandIcon);
			jta.setPreferredSize(new Dimension(width, height));
			jta.updateUI();
		} else {
			button.setIcon(collapseIcon);
		}
		expanded = !expanded;
		cpMain.removeAll();
		cpMain.add(jta);
		cpMain.updateUI();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		jta.updateUI();
		Rectangle viewRect = jta.getVisibleRect();
		Point p = viewRect.getLocation();
		int startIndex = jta.viewToModel(p);
		p.x += viewRect.width;
		p.y += viewRect.height;
		int endIndex = jta.viewToModel(p);
		int spaceLength = endIndex - startIndex;
		if ((buffer.length() <= spaceLength) && !expanded)
			removeIcon();
		else
			showIcon();
	}
	
	public static void main (String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.setLayout(new BorderLayout());
		jFrame.add(new LongTextPane("This is a long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long long text."), BorderLayout.NORTH);
		jFrame.add(new LongTextPane("It is a very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very text."), BorderLayout.SOUTH);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(300,400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}

}