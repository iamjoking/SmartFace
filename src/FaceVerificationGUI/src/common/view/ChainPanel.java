/** This Panel is like a chain.
 * @author iamjoking
 */

package common.view;

import javax.swing.*;
import java.awt.*;
import java.awt.LayoutManager.*;

public class ChainPanel extends JPanel {
	JPanel jp;
	int hgap, vgap;
	
	public ChainPanel() {
		this(5,5);
	}
	
	public ChainPanel(int vgap) {
		this(5,vgap);
	}

	public ChainPanel(int hgap, int vgap) {
		super();
		setLayout(new BorderLayout());
		jp = new JPanel(new BorderLayout(hgap,vgap));
		add(jp, BorderLayout.CENTER);
		this.hgap = hgap;
		this.vgap = vgap;
	}
	
	public Component add (Component c) {
		JPanel temp = new JPanel(new BorderLayout(hgap,vgap));
		jp.add(c, BorderLayout.NORTH);
		jp.add(temp, BorderLayout.CENTER);
		jp = temp;
		return null;
	}
	
	public void removeAll() {
		super.removeAll();
		setLayout(new BorderLayout());
		jp = new JPanel(new BorderLayout(hgap,vgap));
		add(jp, BorderLayout.CENTER);
	}
}