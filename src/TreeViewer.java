import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Alex on 18/02/2018.
 */
public class TreeViewer<T> extends JPanel{

    public static final Color BG = new Color(0, 0, 0, 0);
    private final int size;
    private final JLabel top;
    private final JPanel center;

    public TreeViewer(T tree, Function<T,String> valueExtractor, Function<T,List<? extends T>> childExtractor){
        String value = valueExtractor.apply(tree);
        setLayout(new BorderLayout());
        top = new JLabel(value);
        top.setBorder(new EmptyBorder(10,5,10,5));
        //setBorder(new LineBorder(Color.blue));
        setAlignmentY(0);
//        setBorder(BorderFactory.createLineBorder(Color.GREEN));
        top.setHorizontalAlignment(SwingConstants.CENTER);
        add(top,BorderLayout.NORTH);
        List<? extends T> children = childExtractor.apply(tree);
        size = children.size();
        center = new JPanel();
        //center.setLayout(new BoxLayout(center,BoxLayout.X_AXIS));
        setBackground(BG);
        center.setBackground(BG);

        children.stream().map(c->new TreeViewer<>(c,valueExtractor,childExtractor)).forEach(center::add);
        add(center);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D)g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int startX = top.getX()+getWidth()/2;
        int startY = top.getY()+ top.getHeight()-5;

        for(Component c : center.getComponents()){
            TreeViewer c1 = (TreeViewer) c;
            int endX = c1.getX()+c1.getWidth()/2;
            int endY = c1.getY()+ c1.top.getHeight()+5;
            g.drawLine(startX,startY,endX,endY);
        }
    }
}
