/** The option pane.
 * @author iamjoking
 */

package common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.LayoutManager.*;
import java.awt.image.*;

public class OptionFrame extends JDialog {
	JPanel jpBody = new JPanel();
	JPanel jpBottom = new JPanel();
	
	public OptionFrame (String title, String description, Icon icon) {
		setModal(true);
		JPanel jpHead = new JPanel(new BorderLayout());
		jpHead.setBackground(Color.white);
		JPanel jpHeadLeft = new JPanel(new BorderLayout(5,5));
		jpHeadLeft.setBackground(Color.white);
		JPanel jpHeadRight = new JPanel(new BorderLayout());
		jpHeadRight.setBackground(Color.white);
		JPanel jpEmpty1 = new JPanel();
		jpEmpty1.setBackground(Color.white);
		JPanel jpEmpty2 = new JPanel();
		jpEmpty2.setBackground(Color.white);
		jpHead.add(jpHeadLeft, BorderLayout.CENTER);
		jpHead.add(jpHeadRight, BorderLayout.EAST);
		jpHead.add(jpEmpty1, BorderLayout.NORTH);
		jpHead.add(jpEmpty2, BorderLayout.WEST);
		
		JLabel jlTitle = new JLabel(title);
		jlTitle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 14));
		JTextArea jtaDescription = new JTextArea(description);
		jtaDescription.setEditable(false);
		jtaDescription.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		jpHeadLeft.add(jlTitle, BorderLayout.NORTH);
		jpHeadLeft.add(jtaDescription, BorderLayout.CENTER);
		JPanel jpEmpty4 = new JPanel();
		jpEmpty4.setBackground(Color.white);
		jpHeadLeft.add(jpEmpty4, BorderLayout.WEST);
		
		JLabel jlIcon = new JLabel(fixShape(icon,66));
		jpHeadRight.add(jlIcon, BorderLayout.CENTER);
		
		jpBody.setLayout(new BorderLayout());
		JPanel jpBodyArea = new JPanel(new BorderLayout(20,20));
		jpBodyArea.add(new JSeparator(),BorderLayout.NORTH);
		jpBodyArea.add(jpBody,BorderLayout.CENTER);
		jpBodyArea.add(new JSeparator(),BorderLayout.SOUTH);
		jpBodyArea.add(new JPanel(), BorderLayout.WEST);
		jpBodyArea.add(new JPanel(), BorderLayout.EAST);
		JScrollPane jspBody = new JScrollPane(jpBodyArea);
		jspBody.setBorder(null);
		
		jpBottom.setLayout(new FlowLayout(FlowLayout.RIGHT,15,15));
		JPanel jpBottomArea = new JPanel(new BorderLayout());
		jpBottomArea.add(jpBottom, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(jpHead, BorderLayout.NORTH);
		add(jspBody, BorderLayout.CENTER);
		add(jpBottomArea, BorderLayout.SOUTH);
		setResizable(false);
		setMinimumSize(new Dimension(600,200));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private Icon fixShape (Icon icon, int width) {
		int w = icon.getIconWidth(), h = icon.getIconHeight();
		double scale = (double)width / w;
		Image bimg = ((ImageIcon)icon).getImage();
		Image image = bimg.getScaledInstance((int)(w*scale), (int)(h*scale), Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
	protected void setBody(JComponent jc) {
		jpBody.removeAll();
		jpBody.add(jc, BorderLayout.CENTER);
	}
	
	protected void addButton(JButton jb) {
		jpBottom.add(jb);
	}
	
	public static void main (String[] args) {
		OptionFrame frame = new OptionFrame("Create a new project",
			"Enter your project file's name.", new ImageIcon("res/pic/newaproj.png"));
		frame.setVisible(true);
	}


}