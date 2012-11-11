/**
 * 
 */
package mainframe;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.LogFactory;
import facedetection.FaceDetectionFrame;

/**
 * @author Momo
 *
 */
public class MainFrame extends JFrame {


	/* Set the "system look and feel".
	 */
	private static void useSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Initialize.
	 * Return 0 if everything is OK;
	 * Return an error code when it comes with problems. 
	 */
	private static int initial() {
		useSystemLookAndFeel();
		LogFactory logFactory = new LogFactory();
		
		return 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int initialCode = initial();
		
		if (initialCode != 0) {
			JOptionPane.showMessageDialog(null,
					"Initial error!\nThe error code is " + initialCode + ".");
			return ;
		}
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

}
