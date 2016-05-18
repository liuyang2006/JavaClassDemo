package ch08;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class FileTreeTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                FileTree fileTree = new FileTree();
                FileTreeModel model = new FileTreeModel(new DefaultMutableTreeNode(new FileNode("root", null, null, true)));
                fileTree.setModel(model);
                fileTree.setCellRenderer(new FileTreeRenderer());

                frame.getContentPane().add(new JScrollPane(fileTree), BorderLayout.CENTER);
                frame.setSize(300, 700);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}

class FileTree extends JTree {
    public TreePath mouseInPath;
    protected FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    public FileTree() {
        setRootVisible(false);
        addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
                if (!fileNode.isInit) {
                    File[] files;
                    if (fileNode.isDummyRoot) {
                        files = fileSystemView.getRoots();
                    } else {
                        files = fileSystemView.getFiles(
                                ((FileNode) lastTreeNode.getUserObject()).file,
                                false);
                    }
                    for (int i = 0; i < files.length; i++) {
                        FileNode childFileNode = new FileNode(
                                fileSystemView.getSystemDisplayName(files[i]),
                                fileSystemView.getSystemIcon(files[i]), files[i],
                                false);
                        DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
                        lastTreeNode.add(childTreeNode);
                    }
                    //通知模型节点发生变化
                    DefaultTreeModel treeModel1 = (DefaultTreeModel) getModel();
                    treeModel1.nodeStructureChanged(lastTreeNode);
                }
                //更改标识，避免重复加载
                fileNode.isInit = true;
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                TreePath path = getPathForLocation(e.getX(), e.getY());

                if (path != null) {
                    if (mouseInPath != null) {
                        Rectangle oldRect = getPathBounds(mouseInPath);
                        mouseInPath = path;
                        repaint(getPathBounds(path).union(oldRect));
                    } else {
                        mouseInPath = path;
                        Rectangle bounds = getPathBounds(mouseInPath);
                        repaint(bounds);
                    }
                } else if (mouseInPath != null) {
                    Rectangle oldRect = getPathBounds(mouseInPath);
                    mouseInPath = null;
                    repaint(oldRect);
                }
            }
        });
    }
}

class FileNode {
    public FileNode(String name, Icon icon, File file, boolean isDummyRoot) {
        this.name = name;
        this.icon = icon;
        this.file = file;
        this.isDummyRoot = isDummyRoot;
    }

    public boolean isInit;
    public boolean isDummyRoot;
    public String name;
    public Icon icon;
    public File file;
}

class FileTreeRenderer extends DefaultTreeCellRenderer {
    public FileTreeRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {

        FileTree fileTree = (FileTree) tree;
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        FileNode fileNode = (FileNode) node.getUserObject();
        label.setText(fileNode.name);
        label.setIcon(fileNode.icon);

        label.setOpaque(false);
        if (fileTree.mouseInPath != null &&
                fileTree.mouseInPath.getLastPathComponent().equals(value)) {
            label.setOpaque(true);
            label.setBackground(new Color(255, 0, 0, 90));
        }
        return label;
    }
}

class FileTreeModel extends DefaultTreeModel {
    public FileTreeModel(TreeNode root) {
        super(root);
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File[] files = fileSystemView.getRoots();
        for (int i = 0; i < files.length; i++) {
            FileNode childFileNode = new FileNode(fileSystemView.getSystemDisplayName(files[i]), fileSystemView.getSystemIcon(files[i]), files[i], false);
            DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
            ((DefaultMutableTreeNode) root).add(childTreeNode);
        }
    }

    @Override
    public boolean isLeaf(Object node) {
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
        FileNode fileNode = (FileNode) treeNode.getUserObject();
        if (fileNode.isDummyRoot) return false;
        return fileNode.file.isFile();
    }
}