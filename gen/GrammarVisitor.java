// Generated from C:/Users/Alex/OneDrive/ParserA/src\Grammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(GrammarParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#floatp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatp(GrammarParser.FloatpContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expp1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpp1(GrammarParser.Expp1Context ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(GrammarParser.AtomContext ctx);
}