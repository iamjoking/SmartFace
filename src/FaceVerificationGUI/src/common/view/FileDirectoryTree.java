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
	
	// 获取选中的文件
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
	
	// 响应展开树状视图
	public void treeExpanded(TreeExpansionEvent event) {
		// 得到当前展开的子节点
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (event.getPath())
				.getLastPathComponent();

		// 判断是否已有子节点，如果没有，遍历文件信息将其添加到当前子节点下，生成下级子节点
		if (node.getChildCount() == 0) {
			// 备份当前子节点
			DefaultMutableTreeNode originalNode = node;

			// 得到当前子节点的名，即对应文件的名称
			String fileName = node.toString();

			// 通过倒序上移节点，获取节点对应文件的完整路径
			while (node.getParent() != null) {
				node = (DefaultMutableTreeNode) node.getParent();
				fileName = node.toString() + "\\" + fileName;
			}

			// 继续处理当前子节点
			if (originalNode != null) {
				// 生成当前子节点对应文件的类变量
				File f = new File(fileName);

				// 如果是目录，则继续处理
				if (f.isDirectory()) {
					// 得到下级文件列表
					File files[] = f.listFiles();
					// 如果目录不为空，循环遍历每个下级文件
					if (files != null) {
						Arrays.sort(files,new FileSorter());
						for (int i = 0; i < files.length; i++) {
							// 建立下级子节点，名称为文件名称
							DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(
									files[i].getName());

							// 如果是目录，则对应的子节点允许存在子节点，显示加号标记，否则不显示
							if (files[i].isDirectory())
								dmtn.setAllowsChildren(true);
							else
								dmtn.setAllowsChildren(false);

							// 将新建的子节点追加到上级节点，作为最后一个节点
							dtm.insertNodeInto(dmtn, originalNode, originalNode
									.getChildCount());
						}
					}
				}
			}
		}
	}

	// 响应收缩树状视图
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