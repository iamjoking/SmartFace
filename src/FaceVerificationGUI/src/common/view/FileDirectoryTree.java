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
	
	// ��ȡѡ�е��ļ�
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
					if (files != null)
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

	// ��Ӧ������״��ͼ
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