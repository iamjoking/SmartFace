/*
 * FaceDetectionFrame.java

 *
 * Created on __DATE__, __TIME__
 */

package facedetection;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.LogFactory;

import command.ExecCommand;
import command.LoggedCommand;

/**
 *
 * @author  __USER__
 */
public class FaceDetectionFrame extends javax.swing.JFrame {

	/** Creates new form FaceDetectionFrame */
	public FaceDetectionFrame() {
		initComponents();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jFileChooser1 = new javax.swing.JFileChooser();
		buttonGroup1 = new javax.swing.ButtonGroup();
		jFileChooser2 = new javax.swing.JFileChooser();
		jButton2 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jLayeredPane1 = new javax.swing.JLayeredPane();
		jLabel2 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jCheckBox1 = new javax.swing.JCheckBox();
		jCheckBox2 = new javax.swing.JCheckBox();
		jRadioButton1 = new javax.swing.JRadioButton();
		jRadioButton2 = new javax.swing.JRadioButton();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jRadioButton3 = new javax.swing.JRadioButton();
		jLayeredPane2 = new javax.swing.JLayeredPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		commandOutputWindow = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();

		jFileChooser1.setCurrentDirectory(new java.io.File(
				"C:\\Users\\Momo\\AppData\\Local\\MyEclipse\\MyEclipse 10"));
		jFileChooser1.setMultiSelectionEnabled(true);

		jFileChooser2
				.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setResizable(false);

		jButton2.setText("Start");
		jButton2.setMaximumSize(new java.awt.Dimension(100, 25));
		jButton2.setMinimumSize(new java.awt.Dimension(100, 25));
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton5.setText("Close");
		jButton5.setMaximumSize(new java.awt.Dimension(100, 25));
		jButton5.setMinimumSize(new java.awt.Dimension(100, 25));

		jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEtchedBorder(), "Options"));

		jLabel2.setText("Original Images : ");
		jLabel2.setBounds(10, 30, 110, 25);
		jLayeredPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
		jTextField1.setBounds(130, 30, 360, 25);
		jLayeredPane1.add(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jLabel3.setText("Other options :");
		jLabel3.setBounds(10, 150, 110, 25);
		jLayeredPane1.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
		jTextField2.setBounds(130, 70, 360, 25);
		jLayeredPane1.add(jTextField2, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jButton3.setText("Browse");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jButton3.setBounds(510, 30, 75, 25);
		jLayeredPane1.add(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jButton4.setText("Select");
		jButton4.setMaximumSize(new java.awt.Dimension(75, 25));
		jButton4.setMinimumSize(new java.awt.Dimension(75, 25));
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jButton4.setBounds(510, 70, 75, 25);
		jLayeredPane1.add(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jCheckBox1.setText("equal the hist");
		jCheckBox1.setBounds(230, 150, 110, 25);
		jLayeredPane1.add(jCheckBox1, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jCheckBox2.setText("grayscale");
		jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox2ActionPerformed(evt);
			}
		});
		jCheckBox2.setBounds(130, 150, 81, 25);
		jLayeredPane1.add(jCheckBox2, javax.swing.JLayeredPane.DEFAULT_LAYER);

		buttonGroup1.add(jRadioButton1);
		jRadioButton1.setSelected(true);
		jRadioButton1.setText("jpg");
		jRadioButton1.setBounds(130, 110, 45, 25);
		jLayeredPane1
				.add(jRadioButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

		buttonGroup1.add(jRadioButton2);
		jRadioButton2.setText("bmp");
		jRadioButton2.setBounds(270, 110, 53, 25);
		jLayeredPane1
				.add(jRadioButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jLabel4.setText("Output path :");
		jLabel4.setBounds(10, 70, 110, 25);
		jLayeredPane1.add(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jLabel5.setText("Output format :");
		jLabel5.setBounds(10, 110, 110, 25);
		jLayeredPane1.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

		buttonGroup1.add(jRadioButton3);
		jRadioButton3.setText("png");
		jRadioButton3.setBounds(200, 110, 49, 25);
		jLayeredPane1
				.add(jRadioButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);

		jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEtchedBorder(), "Details"));

		commandOutputWindow.setBackground(new java.awt.Color(0, 0, 0));
		commandOutputWindow.setColumns(20);
		commandOutputWindow.setEditable(false);
		commandOutputWindow.setForeground(new java.awt.Color(204, 204, 204));
		commandOutputWindow.setLineWrap(true);
		commandOutputWindow.setRows(5);
		commandOutputWindow.setSelectedTextColor(new java.awt.Color(0, 0, 0));
		commandOutputWindow
				.setSelectionColor(new java.awt.Color(204, 204, 204));
		jScrollPane1.setViewportView(commandOutputWindow);

		jScrollPane1.setBounds(10, 20, 590, 150);
		jLayeredPane2.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLayeredPane1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																612,
																Short.MAX_VALUE)
														.addComponent(
																jLayeredPane2,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																612,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jLayeredPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												186,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jLayeredPane2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												180,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));

		jLabel1.setFont(new java.awt.Font("Î¢ÈíÑÅºÚ", 0, 24));
		jLabel1.setText("Face Detection");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel1)
						.addContainerGap(453, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel1)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(391, Short.MAX_VALUE)
								.addComponent(jButton2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										106,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButton5,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										99,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(22, 22, 22)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														jButton5,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jButton2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		if (images == null || images.length == 0)
			JOptionPane.showMessageDialog(null,
					"Please select the original image(s)!");
		if (outputPath == null)
			JOptionPane
					.showMessageDialog(null, "Please select the outputPath!");
		int failCount = 0;
		commandOutputWindow.setText("");
		
		LoggedCommand loggedCommand = new LoggedCommand("Face Detection Module");
		for (int i = 0; i < images.length; i++) {
			try {
				loggedCommand.exec(generateCommand(images[i], outputPath),null,workPath);
				commandOutputWindow.append("[" + (i+1) + "/" + images.length + "]\t");
				commandOutputWindow.append(loggedCommand.getCommand() + System.getProperty("line.separator"));
				commandOutputWindow.append(loggedCommand.getResult() + System.getProperty("line.separator"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				commandOutputWindow.append("[" + i + "/" + images.length
						+ "]\tFailed!" + System.getProperty("line.separator"));
				failCount++;
				e.printStackTrace();
			}
		}
		commandOutputWindow.append("==================== success : "
				+ (images.length - failCount) + ", failed : " + failCount
				+ " ===================="
				+ System.getProperty("line.separator"));
	}

	private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		jFileChooser2.setVisible(true);
		int status = jFileChooser2.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			File dir = jFileChooser2.getSelectedFile();
			outputPath = dir.getAbsolutePath();
			jTextField2.setText(outputPath);
		}
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		jFileChooser1.setVisible(true);
		int status = jFileChooser1.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			File[] files = jFileChooser1.getSelectedFiles();
			StringBuffer sBuffer = new StringBuffer();
			images = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				images[i] = files[i].getAbsolutePath();
				sBuffer.append(images[i]).append(
						System.getProperty("path.separator"));
			}
			jTextField1.setText(sBuffer.toString());
		}
	}
	
	private String getFileExtensionName() {
		String fileExtensionName = "JPG";
		Enumeration<AbstractButton> eab = buttonGroup1.getElements();
		JRadioButton current;
		while (eab.hasMoreElements()) {
			current = (JRadioButton)eab.nextElement();
			if (current.isSelected()) {
				fileExtensionName = current.getText();
				break;
			}
		}
		return fileExtensionName;
	}

	private String generateCommand(String image, String output) {
		String command = new String();
		command = workPath
				+ commandPattern.replaceFirst("inputImagePath", image.replaceAll("\\\\", "\\\\\\\\"));
		System.out.println(output.replaceAll("\\\\", "\\\\\\\\"));
		System.out.println(output.replaceAll("\\\\", "\\\\\\\\").concat(System.getProperty("file.separator")));
		command = command.replaceFirst("outputDirectoryPath", output.replaceAll("\\\\", "\\\\\\\\") + (System.getProperty("file.separator")) + (System.getProperty("file.separator")));
		command = command.replaceFirst("outputImageFilenamePrefix",image.substring(image.lastIndexOf("\\"), image.lastIndexOf(".")) + "_");
		if (!jCheckBox2.isSelected())
			command = command.replaceFirst(" -g", "");
		if (!jCheckBox1.isSelected())
			command = command.replaceFirst(" -e", "");
		
		System.out.println("file extension is : "
				+ getFileExtensionName());

		command = command.replaceFirst("FileExtensionName",getFileExtensionName());
		return command.replaceAll("\\\\", "\\\\\\\\");
	}
	
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

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		useSystemLookAndFeel();
		LogFactory logFactory = new LogFactory();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FaceDetectionFrame().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JTextArea commandOutputWindow;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JFileChooser jFileChooser1;
	private javax.swing.JFileChooser jFileChooser2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JLayeredPane jLayeredPane2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	// End of variables declaration//GEN-END:variables

	private String[] images;
	private String outputPath;
	private final static String workPath = "src" + System.getProperty("file.separator") + "C++"
			+ System.getProperty("file.separator") + "FaceDetection"
			+ System.getProperty("file.separator"); // + System.getProperty("file.separator");
	private final static String commandPattern = "FaceDetection -i inputImagePath -d outputDirectoryPath -x FileExtensionName -n outputImageFilenamePrefix -g -e";

}