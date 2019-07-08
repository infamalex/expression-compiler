import javafx.scene.control.TreeView;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alex on 16/02/2018.
 */
public class Test {
    public static void main(String[] args) {

        ExpParser parser = new ExpParser(System.in);
        for(;;) {
            System.out.println("Enter an Expression");
            try {
                ExpTree etree = parser.next();
                TreeViewer<ExpTree> panel = new TreeViewer<>(etree, ExpTree::value, ExpTree::children);
                TreeViewer<ParseTree> tree = new TreeViewer<>(parser.getTree(),Test::valueOf, Test::badConvert);
                new ExpTreeViewer("Tree Viewer","CST", tree,"AST",panel);

                System.out.println(etree.forthPostFix());
            }
            catch (ParseError.UndefinedException e){
                System.err.printf("%"+(e.getCharPos()+1)+"s%n","^");
                System.err.println(e.getMessage());
                System.err.flush();
            }
        }
    }


    static String valueOf(ParseTree tree){
        if(tree instanceof ParserRuleContext)
            return GrammarParser.ruleNames[((ParserRuleContext) tree).getRuleIndex()];
        else return tree.toString();
    }
static List<ParseTree> badConvert(ParseTree tree){
    return IntStream.range(0,tree.getChildCount()).mapToObj(tree::getChild).collect(Collectors.toList());
}
}
