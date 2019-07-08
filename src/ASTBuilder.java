import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Alex on 16/02/2018.
 */


/**
 * Traverses the concrete syntax tree and converts it into an abstract syntax tree.<BR>
 *     While positive unary operators are an accepted part of the grammar, they are not used in the AST
 */
public class ASTBuilder{
    private static final ExpressionFactory factory = new ExpressionFactory();

    /**
     *
     * @param cxt
     * @return
     */
    public static ExpTree build(GrammarParser.ExpressionContext cxt){
        return cxt.accept(factory);
    }
    private static class ExpressionFactory extends GrammarBaseVisitor<ExpTree> {
        @Override
        public ExpTree visitExpression(GrammarParser.ExpressionContext ctx) {
            return new ExpTree.Evaluate(ctx.expp1().accept(this));
        }

        @Override
        public ExpTree visitExpp1(GrammarParser.Expp1Context ctx) {
            if(ctx.children.size() == 3)
            {
//                List<GrammarParser.Expp2Context> ch = new ArrayList<>(ctx.expp2());
//                ExpTree lhs = (ctx.expp2().size() == 1?ctx.expp1():ch.remove(0)).accept(this);
//                ExpTree.Operator op = ExpTree.Operator.getOp(ctx.UNARY().getText());
//                return new ExpTree.OpExp(lhs,op,ch.remove(0).accept(this));
                ExpTree lhs = ctx.expp1(0).accept(this);
                ExpTree rhs = ctx.expp1(1).accept(this);
                ExpTree.Operator op  = ExpTree.Operator.getOp(Optional.ofNullable(ctx.BINARY()).orElse(ctx.UNARY()).getText());
                return new ExpTree.OpExp(lhs, op , rhs);
            }
            ExpTree exp = ctx.atom().accept(this);
            if (ctx.children.size() == 2){
                ExpTree.Operator op = ExpTree.Operator.getOp(ctx.UNARY().getText());
                if(op == ExpTree.Operator.SUB)
                return new ExpTree.Negate(exp);
            }
            return exp;
        }

//        @Override
//        public ExpTree visitExpp2(GrammarParser.Expp2Context ctx) {
//            if(ctx.children.size() == 3){
//                ExpTree lhs = ctx.getChild(0).accept(this);
//                ExpTree.Operator op = ExpTree.Operator.getOp(ctx.BINARY().getText());
//                ExpTree rhs = ctx.getChild(2).accept(this);
//                return new ExpTree.OpExp(lhs,op,rhs);
//            }
//            return ctx.getChild(0).accept(this);
//        }



        @Override
        public ExpTree visitAtom(GrammarParser.AtomContext ctx) {
            if(ctx.children.size() == 3)
                return visitExpp1(ctx.expp1());
            return ctx.getChild(0).accept(this);
        }

        @Override
        public ExpTree visitInteger(GrammarParser.IntegerContext ctx) {
            return new ExpTree.IntegerExp(ctx.getText());
        }

        @Override
        public ExpTree visitFloatp(GrammarParser.FloatpContext ctx) {
            return new ExpTree.FloatExp(ctx.getText());
        }
//
//        @Override
//        public ExpTree visitSignedatom(GrammarParser.SignedatomContext ctx) {
//            ExpTree tree = ctx.atom().accept(this);
//            if(ctx.UNARY().getText().equals("+"))
//                return tree;
//            else
//                return new ExpTree.Negate(tree);
//
//        }
    }
}
