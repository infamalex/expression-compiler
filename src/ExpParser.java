import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by Alex on 17/02/2018.
 */
public class ExpParser implements Iterator<ExpTree>{
    private static final Logger LOGGER = Logger.getLogger(ExpParser.class.getName());
    static {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.WARNING);
        LOGGER.addHandler(handler);}
    private Iterator<String> exps;

    public GrammarParser.ExpressionContext getTree() {
        return tree;
    }

    private GrammarParser.ExpressionContext tree;

    private ExpParser(Iterator<String> exps){
        this.exps = exps;
    }

    public ExpParser(String exp){
        this(Stream.of(exp).iterator());
    }

    public ExpParser(InputStream io){
        this(Stream.generate(new Scanner(io)::nextLine).iterator());
    }


    @Override
    public boolean hasNext() {
        return exps.hasNext();
    }

    @Override
    public ExpTree next() {
        CharStream cs = CharStreams.fromString(exps.next());
        GrammarLexer lexer = new GrammarLexer(cs);
        lexer.removeErrorListeners();
        ParseError listener = new ParseError();
        lexer.addErrorListener(listener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        GrammarParser parser = new GrammarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        tree = parser.expression();

        return ASTBuilder.build(tree);
    }
}
