// Generated from C:/Users/Alex/OneDrive/ParserA/src\Grammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(GrammarParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(GrammarParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#floatp}.
	 * @param ctx the parse tree
	 */
	void enterFloatp(GrammarParser.FloatpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#floatp}.
	 * @param ctx the parse tree
	 */
	void exitFloatp(GrammarParser.FloatpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expp1}.
	 * @param ctx the parse tree
	 */
	void enterExpp1(GrammarParser.Expp1Context ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expp1}.
	 * @param ctx the parse tree
	 */
	void exitExpp1(GrammarParser.Expp1Context ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(GrammarParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(GrammarParser.AtomContext ctx);
}