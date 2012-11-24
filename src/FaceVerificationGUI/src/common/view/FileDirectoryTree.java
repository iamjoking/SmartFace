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
 
public class FileDirectoryTree extends JPanel implements TreeExpansionListener {
	JTree tree = null;
	DefaultTreeModel dtm = null;
	File root = null;
	FileOperation fdtOp = null;
	JScrollPane jsp = null;
	
	public FileDirectoryTree (String rootString, final FileOperation fdtOp) {
		this(new File(rootString), fdtOp);
	}
	
	public FileDirectoryTree (File rootFile, final FileOperation fdtOp) {
		super();
		this.fdtOp = fdtOp;
		changeRoot(rootFile);
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		jsp = new JScrollPane(tree,v,h);
		
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
							//changeRoot(getSelectedFile());
						}
					}
				}			
			}
		});
		
		setLayout(new BorderLayout());
		JPanel jpHead = new JPanel(new GridLayout());
		jpHead.setBackground(new Color(58,143,183));
		JLabel jlTitle = new JLabel("File Explorer");
		jlTitle.setForeground(Color.WHITE);
		jpHead.add(jlTitle);
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
		for (Object obj : tp.getPath()) {
			fullPath += obj.toString() + System.getProperty("file.separator");
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
					if (files != null)
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

	// 响应收缩树状视图
	public void treeCollapsed(TreeExpansionEvent event) {
	
	}

	private DefaultTreeModel genDmt(File root) {
		DefaultTreeModel ret;
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(root.getPath());
		File files[] = root.listFiles();
		for (int i = 0; i < files.length; i++) {
			DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(files[i]
					.getName());

			if (files[i].isDirectory())
				dmtn.setAllowsChildren(true);
			else
				dmtn.setAllowsChildren(false);

			top.add(dmtn);
		}
		ret = new DefaultTreeModel(top);
		ret.setAsksAllowsChildren(true);
		return ret;
	}

	private void changeRoot(File dir) {
		root = dir;
		dtm = genDmt(root);
		tree = new JTree(dtm);
	}
	
	public static void main (String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.add(new FileDirectoryTree("E:\\", new FileOperation() {
			public boolean open(File file) {
				System.out.println(file.getPath());
				return true;
			}
		}));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}

}