import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by Alex on 17/02/2018.
 */
public class ExpTreeViewer extends JFrame{

    private static JPanel labeled(JPanel panel, String title){
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel);
        JLabel comp = new JLabel(title);
        JPanel top = new JPanel(new GridLayout());
        top.setBackground(Color.LIGHT_GRAY);
        top.add(comp);
        container.add(top,BorderLayout.NORTH);
        return container;
    }

    public ExpTreeViewer(String name,String title, JPanel panel,String title2,JPanel panel2){
        this(name,new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,labeled(panel,title),labeled(panel2,title2)));
    }
    public ExpTreeViewer(String name,JComponent content){
        super(name);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.white);
        //content.add(l,BorderLayout.SOUTH);
        setContentPane(content);
//
//        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Expression");
//        JTree treeView = new JTree(root);
//        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeView.getCellRenderer();
//        renderer.setLeafIcon(LEAF);
//        renderer.setOpenIcon(NODE);
//

//        treeView.setBorder(new EmptyBorder(10,10,10,10));
//        addToTree( root,tree);

//        expandAll(treeView,new TreePath(root));
        pack();
        setMinimumSize(getSize());
        setVisible(true);
    }

    private static void addToTree( DefaultMutableTreeNode parent, ExpTree tree){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(tree.value());
        parent.add(node);
        tree.children().forEach(t-> addToTree(node,t));
    }
    private static void expandAll(JTree tree, TreePath parent) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (TreeNode n : (Iterable<TreeNode>) IntStream.range(0, node.getChildCount()).mapToObj(node::getChildAt)::iterator) {

                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path);
            }
        }


        tree.expandPath(parent);
    }
}
