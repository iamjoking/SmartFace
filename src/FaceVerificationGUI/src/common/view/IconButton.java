/** The button with icon.
 * @author iamjoking
 */

package common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;

import java.util.*;
import java.io.*;

public class IconButton extends JPanel {
	boolean lightBuffered = false;
	boolean darkBuffered = false;
	Icon icon;
	Icon lightIcon = new ImageIcon("res/pic/LightShadow.png");
	Icon darkIcon = new ImageIcon("res/pic/DarkShadow.png");
	String toolTip;
	int width, height;
	
	public IconButton() {
		this("");
	}
	
	public IconButton(String imagePath) {
		this(new ImageIcon(imagePath));
	}
	
	public IconButton(String imagePath, int width, int height) {
		this(new ImageIcon(imagePath),width,height);
	}
	
	public IconButton(Icon icon) {
		this(icon,icon.getIconWidth(),icon.getIconHeight());
	}
	
	public IconButton(Icon icon, int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.icon = fixShape(icon,width,height);
		int w = this.icon.getIconWidth(), h = this.icon.getIconHeight();
		lightIcon = fixShape(lightIcon,w,h);
		darkIcon = fixShape(darkIcon,w,h);
		setPreferredSize(new Dimension(w,h));
	}
	
	private void setLightBuffered() {
		lightBuffered = true;
		repaint();
	}

	private void setNonLightBuffered() {
		lightBuffered = false;
		repaint();
	}

	private void setDarkBuffered() {
		darkBuffered = true;
		repaint();
	}

	private void setNonDarkBuffered() {
		darkBuffered = false;
		repaint();
	}
	
	public static Icon fixShape (Icon icon, int width, int height) {
		int w = icon.getIconWidth(), h = icon.getIconHeight();
		Image bimg = ((ImageIcon)icon).getImage();
		Image image;
		double scale;
		if (width > 0 && height > 0) {
			image = bimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} else if (width > 0 && height <= 0) {
			scale = (double)width / w;
			image = bimg.getScaledInstance((int)(w*scale), (int)(h*scale), Image.SCALE_SMOOTH);
		} else if (width <= 0 && height > 0) {
			scale = (double)height / h;
			image = bimg.getScaledInstance((int)(w*scale), (int)(h*scale), Image.SCALE_SMOOTH);
		} else
			return icon;
		return new ImageIcon(image);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		icon.paintIcon(this, g, 0, 0);
		if (lightBuffered)
			lightIcon.paintIcon(this, g, 0, 0);
		if (darkBuffered)
			darkIcon.paintIcon(this, g, 0, 0);
	}

	public void setIcon (Icon icon) {
		this.icon = fixShape(icon,width,height);
	}
	
	public void addMouseListener(final MouseListener I) {
		MouseListener wrap = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				I.mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				setDarkBuffered();
				I.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				I.mouseReleased(e);
				setNonDarkBuffered();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				setLightBuffered();
				I.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				I.mouseExited(e);
				setNonLightBuffered();
			}
		};
		super.addMouseListener(wrap);
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		JPanel jp = new JPanel(new FlowLayout());
		IconButton iButton = new IconButton("res/pic/newaproj.png",100,0);
		iButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("The button is clicked.");
			}
			public void mousePressed(MouseEvent e) {
				System.out.println("The button is pressed.");
			}
			public void mouseReleased(MouseEvent e) {
				System.out.println("The button is released.");
			}
			public void mouseEntered(MouseEvent e) {
				System.out.println("The button is entered.");
			}
			public void mouseExited(MouseEvent e) {
				System.out.println("The button is exited.");
			}
		});
		iButton.setToolTipText("This is a test.");
		jp.add(iButton);
		frame.add(jp);
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


}