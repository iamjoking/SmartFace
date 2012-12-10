/** File Directory Tree
 * @author iamjoking
 */

package common.view;

import common.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.File;
import java.util.*;
import java.io.*;
 
public class FileDirectoryTree extends JPanel implements TreeExpansionListener {
	JPanel jpBody = new JPanel(new BorderLayout());
	JTree tree = null;
	DefaultTreeModel dtm = null;
	File root = null;
	FileOperation fdtOp = null;
	JScrollPane jsp = null;
	Icon backIcon = new ImageIcon("res/pic/BackToUpDirectory.png");
	Icon refreshIcon = new ImageIcon("res/pic/RefreshDirectory.png");
	
	public FileDirectoryTree (String rootString, final FileOperation fdtOp) {
		this(new File(rootString), fdtOp);
	}
	
	public FileDirectoryTree (File rootFile, final FileOperation fdtOp) {
		super();
		this.fdtOp = fdtOp;
		changeRoot(rootFile);
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		jpBody.add(tree,BorderLayout.CENTER);
		jsp = new JScrollPane(jpBody,v,h);

		setLayout(new BorderLayout());
		JPanel jpOperation = new JPanel(new BorderLayout());
		IconButton jlBack = new IconButton(backIcon,0,20);
		IconButton jlRefresh = new IconButton(refreshIcon,0,20);
		jlBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jlBack.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (root != null) {
					try {
						changeRoot(root.getCanonicalFile().getParentFile());
					} catch (IOException eee) {
						eee.printStackTrace();
					}
				}					
			}
		});	
		jlRefresh.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				refresh();
			}
		});	
		jpOperation.add(jlBack,BorderLayout.WEST); jpOperation.add(jlRefresh,BorderLayout.EAST);
		jlBack.setBackground(new Color(58,143,183));
		jlRefresh.setBackground(new Color(58,143,183));
		jpOperation.setBackground(new Color(58,143,183));
		JPanel jpHead = new JPanel(new BorderLayout());
		jpHead.setBackground(new Color(58,143,183));
		JLabel jlTitle = new JLabel("File Explorer");
		jlTitle.setForeground(Color.WHITE);
		jpHead.add(jlTitle,BorderLayout.CENTER);
		jpHead.add(jpOperation,BorderLayout.EAST);
		add(jpHead, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		setPreferredSize(new Dimension(180,300));
	}
	
	// ��ȡѡ�е��ļ�
	public File getSelectedFile () {
		TreePath tp = tree.getSelectionPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp
					.getLastPathComponent();
		
		String fullPath = "";
		Object[] obj = tp.getPath();
		if (root != null)
			fullPath += obj[0].toString() + System.getProperty("file.separator");
		for (int i = 1; i < obj.length; i++) {
			fullPath += obj[i].toString() + System.getProperty("file.separator");
		}
		return new File(fullPath.substring(
				0, fullPath.lastIndexOf(System.getProperty("file.separator"))));
	
	}
	
	// ��Ӧչ����״��ͼ
	public void treeExpanded(TreeExpansionEvent event) {
		// �õ���ǰչ�����ӽڵ�
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (event.getPath())
				.getLastPathComponent();

		// �ж��Ƿ������ӽڵ㣬���û�У������ļ���Ϣ������ӵ���ǰ�ӽڵ��£������¼��ӽڵ�
		if (node.getChildCount() == 0) {
			// ���ݵ�ǰ�ӽڵ�
			DefaultMutableTreeNode originalNode = node;

			// �õ���ǰ�ӽڵ����������Ӧ�ļ�������
			String fileName = node.toString();

			// ͨ���������ƽڵ㣬��ȡ�ڵ��Ӧ�ļ�������·��
			while (node.getParent() != null) {
				node = (DefaultMutableTreeNode) node.getParent();
				fileName = node.toString() + "\\" + fileName;
			}

			// ��������ǰ�ӽڵ�
			if (originalNode != null) {
				// ���ɵ�ǰ�ӽڵ��Ӧ�ļ��������
				File f = new File(fileName);

				// �����Ŀ¼�����������
				if (f.isDirectory()) {
					// �õ��¼��ļ��б�
					File files[] = f.listFiles();
					// ���Ŀ¼��Ϊ�գ�ѭ������ÿ���¼��ļ�
					if (files != null) {
						Arrays.sort(files,new FileSorter());
						for (int i = 0; i < files.length; i++) {
							// �����¼��ӽڵ㣬����Ϊ�ļ�����
							DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(
									files[i].getName());

							// �����Ŀ¼�����Ӧ���ӽڵ���������ӽڵ㣬��ʾ�Ӻű�ǣ�������ʾ
							if (files[i].isDirectory())
								dmtn.setAllowsChildren(true);
							else
								dmtn.setAllowsChildren(false);

							// ���½����ӽڵ�׷�ӵ��ϼ��ڵ㣬��Ϊ���һ���ڵ�
							dtm.insertNodeInto(dmtn, originalNode, originalNode
									.getChildCount());
						}
					}
				}
			}
		}
	}

	// ��Ӧ������״��ͼ
	public void treeCollapsed(TreeExpansionEvent event) {
	
	}

	private DefaultTreeModel genDmt(File root) {
		DefaultTreeModel ret;
		DefaultMutableTreeNode top;
		DefaultMutableTreeNode dmtn;
		File files[];
		if (root != null) {
			top = new DefaultMutableTreeNode(root.getPath());
			files = root.listFiles();
		} else {
			top = new DefaultMutableTreeNode("Root");
			files = File.listRoots();
		}
		if (files != null) {
			Arrays.sort(files,new FileSorter());
			for (int i = 0; i < files.length; i++) {
				if (root == null) {
					dmtn = new DefaultMutableTreeNode(files[i].getPath());
				} else {
					dmtn = new DefaultMutableTreeNode(files[i].getName());
				}
				if (files[i].isDirectory())
					dmtn.setAllowsChildren(true);
				else
					dmtn.setAllowsChildren(false);

				top.add(dmtn);
			}
		}
		ret = new DefaultTreeModel(top);
		ret.setAsksAllowsChildren(true);
		return ret;

	}

	private void changeRoot(File dir) {
		try {
			root = dir.getCanonicalFile();
		} catch (Exception e) {
			root = dir;
		}
		dtm = genDmt(root);
		tree = new JTree(dtm);
		tree.addTreeExpansionListener(this);
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow;
				// Right click.
				if (e.getButton() == MouseEvent.BUTTON3) {
					selRow = tree.getRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(selRow);
					if (selRow != -1 && e.getClickCount() == 1) {
						JPopupMenu pop = new JPopupMenu();
						if (getSelectedFile().isFile()) {
							JMenuItem item1 = new JMenuItem("Open");
							item1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									fdtOp.open(getSelectedFile());
								}
							});
							pop.add(item1);
						}

						JMenuItem item2 = new JMenuItem("Delete");
						item2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								fdtOp.delete(getSelectedFile());
								refresh();
							}
						});
						pop.add(item2);
						pop.show(tree, e.getX(), e.getY());
					}
				}
				
				// Double left click.
				if (e.getButton() == MouseEvent.BUTTON1) {
					selRow = tree.getRowForLocation(e.getX(), e.getY());
					tree.setSelectionRow(selRow);
					if (selRow != -1 && e.getClickCount() == 2) {
						if (getSelectedFile().isFile()) {
							fdtOp.open(getSelectedFile());
						}
						else {
							changeRoot(getSelectedFile());
						}
					}
				}			
			}
		});
		
		jpBody.removeAll();
		jpBody.add(tree,BorderLayout.CENTER);
		jpBody.updateUI();
	}
	
	public void refresh() {
		changeRoot(root);
	}
	
	private class FileSorter implements Comparator <File> {
		public int compare(File f1, File f2) {
			if (f1.isDirectory() && f2.isFile())
				return -1;
			else if (f1.isFile() && f2.isDirectory())
				return 1;
			else
				return java.text.Collator.getInstance(java.util.Locale.CHINA).compare(f1.getName(),f2.getName());
		}
	}
	
	public static void main (String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.add(new FileDirectoryTree(".", new FileOperation() {
			public boolean open(File file) {
				System.out.println(file.getPath());
				return true;
			}
		}));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(300,400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}

}