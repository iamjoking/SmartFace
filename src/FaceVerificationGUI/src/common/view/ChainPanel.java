/** This Panel is like a chain.
 * @author iamjoking
 */

package common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.LayoutManager.*;

public class ChainPanel extends JPanel {
	JPanel jp;
	
	public ChainPanel() {
		super();
		setLayout(new BorderLayout());
		jp = new JPanel(new BorderLayout(5,5));
		add(jp, BorderLayout.CENTER);
	}
	
	public Component add (Component c) {
		JPanel temp = new JPanel(new BorderLayout(5,5));
		jp.add(c, BorderLayout.NORTH);
		jp.add(temp, BorderLayout.CENTER);
		jp = temp;
		return null;
	}
	
	public void removeAll() {
		removeAll();
		setLayout(new BorderLayout());
		jp = new JPanel(new BorderLayout(5,5));
		add(jp, BorderLayout.CENTER);
	}
}