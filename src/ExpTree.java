import java.util.List;
import java.util.stream.Stream;

/**
 * Component of an abstract syntax tree representing an expression
 */
public abstract class ExpTree {
    /**
     * Node representing the root of the AST
     */
    public static class Evaluate extends ExpTree{
        private ExpTree child;

        public Evaluate(ExpTree child){
            this.child = child;
        }
        @Override
        public String forthPostFix() {
            return child.forthPostFix() + (intOnly()?" .s":" f.s");
        }

        @Override
        public String value() {
            return "Expression";
        }

        @Override
        public List<ExpTree> children() {
            return List.of(child);
        }

        @Override
        protected boolean intOnly() {
            return child.intOnly();
        }
    }

    /**
     * Maps the traditional operators to their forth conterparts
     */
    public enum Operator{ADD("+"),SUB("-"),MULT("*"),DIV("/"),MOD("%","mod");

        private String s;
        private String forth;
        Operator(String s){this(s,s);}
        Operator(String s,String forth) {

            this.s = s;
            this.forth = forth;
        }

        public static Operator getOp(String s){
            return Stream.of(values()).filter(v->v.s.equals(s)).findFirst().get();
        }


        @Override
        public String toString() {
            return s;
        }
        public String toFloatForthString() {
            return "f"+toForthString();
        }
        public String toForthString() {
            return forth;
        }
    }

    /**
     * Node that has on operator as it's value and operands as it's children
     */
    public static class OpExp extends ExpTree{
        private final ExpTree lhs;
        private final Operator op;
        private final ExpTree rhs;

        public OpExp(ExpTree lhs, Operator op, ExpTree rhs){

            this.lhs = lhs;
            this.op = op;
            this.rhs = rhs;
        }

        @Override
        public String toString() {
            return String.format("(%s %s %s)",lhs,op,rhs);
        }

        @Override
        public String forthPostFix() {
            String lhss = this.lhs.forthPostFix(),rhss = this.rhs.forthPostFix(),ops = op.toForthString();
            if(!intOnly()){
                ops = op.toFloatForthString();
                if(rhs.intOnly())
                    rhss+= " s>f";
                else if(lhs.intOnly())
                    lhss += " s>f";
            }
            return String.format("%s %s %s",lhss,rhss,ops);
        }

        @Override
        public String value() {
            return ""+op;
        }

        @Override
        public List<ExpTree> children() {
            return List.of(lhs,rhs);
        }

        @Override
        protected boolean intOnly() {
            return lhs.intOnly() && rhs.intOnly();
        }
    }

    public static class IntegerExp extends ExpTree{
        private final int num;

        public IntegerExp(String s){
            num = Integer.valueOf(s);
        }

        @Override
        public String toString() {
            return ""+num;
        }

        @Override
        public String forthPostFix() {
            return toString();
        }

        @Override
        public String value() {
            return toString();
        }

        @Override
        public List<ExpTree> children() {
            return List.of();
        }

        @Override
        protected boolean intOnly() {
            return true;
        }
    }
    public static class FloatExp extends ExpTree{

        private final float num;

        public FloatExp(String s){
            num = Float.valueOf(s);
        }

        @Override
        public String forthPostFix() {
            String str = toString().replace("E","e");
            return str.contains("e")?str:str+"e";
        }

        @Override
        public String value() {
            return toString();
        }

        @Override
        public List<ExpTree> children() {
            return List.of();
        }

        @Override
        protected boolean intOnly() {
            return false;
        }

        @Override
        public String toString() {

            return Float.toString(num);
        }
    }

    /**
     * Node that holds a single child whose result must be negated
     */
    public static class Negate extends ExpTree{
        private ExpTree child;

        public Negate(ExpTree child){

            this.child = child;
        }

        @Override
        public String toString() {
            return "-"+child;
        }

        @Override
        public String forthPostFix() {
            return child.forthPostFix() + (intOnly()?" negate":" fnegate");
        }

        @Override
        public String value() {
            return "-";
        }

        @Override
        public List<ExpTree> children() {
            return List.of(child);
        }

        @Override
        protected boolean intOnly() {
            return child.intOnly();
        }
    }

    /**
     * Traverses the tree recursively and outputs the expression using infix notation and using forth syntax.<BR>
     * Additionally, it adds a type conversion from integer to float if needed
     * @return A compiled Forth statement
     */
    public abstract String forthPostFix();

    /**
     * Value that solely represents the node or leaf
     * @return
     */
    public abstract String value();

    /**
     *
     * @return A list of children if any
     */
    public abstract List<ExpTree> children();

    /**
     * Indicates whether the results of all calculations using this node will return an integer result
     * @return
     */
    protected abstract boolean intOnly();
}
