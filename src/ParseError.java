import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 17/02/2018.
 */
public class ParseError extends BaseErrorListener{
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(ParseError.class.getName());

        LOGGER.setParent(Logger.getLogger(ExpParser.class.getName()));
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        if(e instanceof LexerNoViableAltException)
            throw new UndefinedException(charPositionInLine);
        else if(e instanceof NoViableAltException)
            throw new BadSyntaxException(charPositionInLine);
        else if(e instanceof InputMismatchException)
            throw new MissingException(charPositionInLine,msg,offendingSymbol);
        else if(msg.matches("missing.+"))
            throw new MissingException(charPositionInLine,msg);
        throw new ParseCancellationException("Input cannot be parsed");
    }

    public static class UndefinedException extends ParseCancellationException{
        int pos;

        public UndefinedException(int pos){

            this.pos = pos;
        }

        @Override
        public String getMessage() {
            return "Undefined token at "+pos;
        }

        public int getCharPos() {
            return pos;
        }
    }

    public static class BadSyntaxException extends UndefinedException{

        public BadSyntaxException(int pos) {
            super(pos);
        }

        @Override
        public String getMessage() {
            return "Malformed expression";
        }
    }
    public static class MissingException extends UndefinedException{
        static final Pattern missingPattern = Pattern.compile("missing (.+) at (.+)");
        static final Pattern mismatchPattern = Pattern.compile("mismatched input (.+) expecting [{]((.+)(,.+)*)}");
        private final String place;
        private final String missing;

        public MissingException(int pos, String mes){
            super(pos);
            Matcher matcher = missingPattern.matcher(mes);
            matcher.find();
            missing = matcher.group(1);
            place = matcher.group(2);
        }

        public MissingException(int pos, String mes, Object off){

            super(pos);
            Matcher matcher = mismatchPattern.matcher(mes);
            matcher.find();
            List<String> missing = new ArrayList<>();
            for (String gr : matcher.group(2).split(", "))
            missing.add(convert(gr));

            this.missing = "("+String.join(", ",missing)+" )";
            place = matcher.group(1);

        }

        @Override
        public String getMessage() {
            return "Expected "+missing+" at "+pos;
        }


        private static String convert(String s){

            String lower = s.toLowerCase();
            switch (lower){
                case "unary":return "+, -";
                case "integer": return "int";
                default:return lower;
            }
        }
    }
}
