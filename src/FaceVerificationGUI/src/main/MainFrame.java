/** The main frame.
 * @author iamjoking
 */

package main;

import common.util.*;
import common.util.logging.*;
import common.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MainFrame extends JFrame {
	JTabbedPane jTabbedPane;
	MessagePane messagePane;
	ArrayList<File> openedFile = new ArrayList<File>();
	public static String VERSION = "1.0";
	
	
	public MainFrame() {
		openedFile.add(new File(""));
		initComponents();
	}

	/** Initialize the components.
	 */
	private void initComponents() {
	
	// Start of the menu bar.	
		JMenuBar jMenuBar = new JMenuBar();		// Menu bar
		
		// "File" menu
		JMenu jMenuFile = new JMenu("File");
		JMenuItem jMenuItemOpen = new JMenuItem("Open");	// "Open" Item.
		jMenuItemOpen.addActionListener(new OpenActionListener());
		jMenuItemOpen.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_O, InputEvent.CTRL_MASK)); // Set Accelerator(ctrl + o)
		JMenuItem jMenuItemNew = new JMenuItem("New");	// "New" Item.
		jMenuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});
		jMenuItemNew.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Set Accelerator(ctrl + n)
		JMenu jMenuRecentFiles = new JMenu("Recent");		// "Recent Project(s)" sub menu.
		JMenuItem jMenuItemExit = new JMenuItem("Exit");	// "Exit" Item.
		jMenuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.save();
				System.exit(0);
			}
		});
		jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_F4, InputEvent.ALT_MASK)); // Set Accelerator(alt + F4)	
		jMenuFile.add(jMenuItemOpen);
		jMenuFile.add(jMenuItemNew);
		jMenuFile.add(jMenuRecentFiles);
		jMenuFile.add(jMenuItemExit);
		
		// "Recent Project(s)" sub menu.
		ArrayList<File> recentFiles = Config.getRecentFiles();
		for (int i = 0; i < recentFiles.size(); i++) {
			final File file = recentFiles.get(i);
			JMenuItem jMenuItem = new JMenuItem(file.getPath());
			jMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openFile(file);
				}
			});
			jMenuRecentFiles.add(jMenuItem);
		}
		
		// "Run" menu
		JMenu jMenuRun = new JMenu("Run");
		JMenuItem jMenuItemBuild = new JMenuItem("Build");	// "Build" Item.
		jMenuItemBuild.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F5, 0)); // Set Accelerator(F5)
		JMenuItem jMenuItemRun = new JMenuItem("Run");		// "Run" Item.
		jMenuItemRun.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F7, 0)); // Set Accelerator(F7)		
		jMenuRun.add(jMenuItemBuild);
		jMenuRun.add(jMenuItemRun);		
		
		// "Help" menu
		JMenu jMenuHelp = new JMenu("Help");
		JMenuItem jMenuItemHelp = new JMenuItem("Help");	// "Help" Item.
		jMenuItemHelp.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_F1, 0)); // Set Accelerator(F1)
		JMenuItem jMenuItemAbout = new JMenuItem("About");	// "About" Item.
		jMenuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Copy Right (c) SmartFace Group\nVersion " + VERSION,
					"About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("res/pic/logotiny.png"));
			}
		});
		jMenuHelp.add(jMenuItemHelp);
		jMenuHelp.add(jMenuItemAbout);	
		
		jMenuBar.add(jMenuFile);
		jMenuBar.add(jMenuRun);
		jMenuBar.add(jMenuHelp);
		setJMenuBar(jMenuBar);
	// End of the menu bar.	
	
	// Start of the tool bar.
		JPanel jpToolBar = new JPanel();
	// End of the tool bar.
	
	// Start of the file explorer
		JPanel jpFileExplorer = new JPanel();
		jpFileExplorer.setLayout(new BorderLayout());
		FileDirectoryTree fdt = new FileDirectoryTree(Config.getWorkstationDirectory(), new FileOperation() {
			public boolean open (File file) {
				openFile(file);
				return true;
			}
		});
		jpFileExplorer.add(fdt,BorderLayout.CENTER);
	// End of the file explorer
	
	// Start of the multi-tab area.
		JPanel mtArea = new JPanel(new BorderLayout());
		jTabbedPane = new JTabbedPane();
		jTabbedPane.addMouseListener(new TabbedPaneMouseListener());
		// Draw the start page.
		JPanel jpStartHere = new JPanel(new BorderLayout());
		JPanel jpStartPage = new JPanel(new BorderLayout());
		jpStartPage.add(new JLabel(new ImageIcon("res/pic/Logo.png")),BorderLayout.CENTER);
		JScrollPane jspStartPage = new JScrollPane(jpStartPage);
		jpStartHere.add(jspStartPage,BorderLayout.CENTER);
		jTabbedPane.addTab("Start Here", jpStartHere);
		// End of the start page.
		JPanel jpMtHead = new JPanel(new GridLayout());
		jpMtHead.setBackground(new Color(58,143,183));
		JLabel jlMtTitle = new JLabel("Center");
		jlMtTitle.setForeground(Color.WHITE);
		jpMtHead.add(jlMtTitle);
		mtArea.add(jTabbedPane,BorderLayout.CENTER);
		mtArea.add(jpMtHead,BorderLayout.NORTH);
	// End of the multi-tab area.
	
	// Start of the Console area.
		JPanel consArea = new JPanel(new BorderLayout());
		messagePane = new MessagePane("Console");
		consArea.add(messagePane, BorderLayout.CENTER);
	// End of the Console area.
		
		
	// Combination of the JPanels above.
		jpFileExplorer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		mtArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		consArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		JPanel mainPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(mtArea,BorderLayout.CENTER);
		rightPanel.add(consArea,BorderLayout.SOUTH);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(jpToolBar,BorderLayout.NORTH);
		mainPanel.add(jpFileExplorer,BorderLayout.WEST);
		mainPanel.add(rightPanel,BorderLayout.CENTER);
		
		add(mainPanel);
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				Config.save();
				return ;
			}
		});
	}
	
	public int openFile(File file) {
		messagePane.append("open file : " + file.getPath());
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null,
				"The file \"" + file.getPath() + "\" does not exist!");
			return -1;
		}
		
		if (!file.getPath().endsWith(Project.PROJECT_FILE_EXTENSION)) {
			JOptionPane.showMessageDialog(null,
				"The file \"" + file.getPath() + "\" is not a project file!\nA project file should end with \""
				+ Project.PROJECT_FILE_EXTENSION + "\".");
			return -1;
		}
		
		Config.addRecentFile(file);
		if (!openedFile.contains(file)) {			
			openedFile.add(file);
			jTabbedPane.addTab(file.getPath(), new ProjectPane(new Project(file)));
		}
		jTabbedPane.setSelectedIndex(openedFile.indexOf(file));
		
		return openedFile.indexOf(file);
	}
	
	public void closeFile(int index) {
		messagePane.append("close tab " + index);
		openedFile.remove(index);
		return ;
	}
	
	public int newFile() {
		messagePane.append("new a file");
		ProjectPane newProjPane = new ProjectPane();
		Project newProj = newProjPane.getProject();
		
		if (newProj != null) {
			File projFile = newProj.getProjectFile();
			Config.addRecentFile(projFile);
			return 0;
		}
		else
			return -1;
	}
	
	
	

	/** Set the GUI style.
	 */
	private static void setGUI() {
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

		UIManager.put("Label.font",new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		UIManager.put("Button.font",new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		UIManager.put("TextField.font",new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
	}

	/** Initialize.
	 * Set the GUI style.
	 */
	private static void initial() {
		setGUI();
		LogFactory logFactory = new LogFactory();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initial();
		int errorCode = Config.check();
		if (errorCode != 0) {
			JOptionPane.showMessageDialog(null,
					"Initial error!\n" +
					"[Error Code] : " + errorCode +
					"[Description]: " + Config.getErrorString());
			return ;
		}
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}
	
	private class OpenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser openFileChooser = new JFileChooser(Config.getWorkstationDirectory());
			openFileChooser.setAcceptAllFileFilterUsed(false);
			openFileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory() | f.getPath().endsWith(Project.PROJECT_FILE_EXTENSION);
				}
				public String getDescription() {
					return "Project File (*.proj)";
				}
			});
			
			if (openFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				openFile(openFileChooser.getSelectedFile());
		}
	}
	
	private class TabbedPaneMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			final int index = jTabbedPane.indexAtLocation(e.getX(), e.getY());
			if (index < 0) return ;
			// Right click.
			if (e.getButton() == MouseEvent.BUTTON3) {
				if (e.getClickCount() == 1) {
					JPopupMenu pop = new JPopupMenu();
					JMenuItem item1 = new JMenuItem("Close");
					item1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							closeFile(index);
							jTabbedPane.remove(index);
						}
					});
					pop.add(item1);
					pop.show(jTabbedPane, e.getX(), e.getY());
				}
			}
			
			// Double left click.
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				closeFile(index);
				jTabbedPane.remove(index);
			}
		}
	}

}