import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Lexer {

    private static String input;
    private static InputStream stream;
    private static int pos;

    public static void setInput(String in) {
        input = in;
        stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        pos = 0;
    }

    public static Token getNextToken() throws Exception {
        StringBuilder builder = new StringBuilder();
        int c;
        while ((c = stream.read()) != -1) {
            pos++;
            if (Character.isWhitespace(c)) {
                continue;
            } else if (Character.isDigit(c)) {
                builder.append((char) c);
                while (Character.isDigit((c = stream.read()))) {
                    pos++;
                    builder.append((char) c);
                }
                stream.reset();
                pos--;
                return new Token(Type.NUMBER, builder.toString());
            } else if (Character.isLetter(c)) {
                builder.append((char) c);
                while (Character.isLetterOrDigit((c = stream.read()))) {
                    pos++;
                    builder.append((char) c);
                }
                stream.reset();
                pos--;
                String identifier = builder.toString();
                if (identifier.equals("cons")) {
                    return new Token(Type.CONS, identifier);
                } else if (identifier.equals("nil")) {
                    return new Token(Type.NIL, identifier);
                } else {
                    return new Token(Type.IDENT, identifier);
                }
            } else if (c == '(') {
                return new Token(Type.LPAREN, "(");
            } else if (c == ')') {
                return new Token(Type.RPAREN, ")");
            } else {
                throw new Exception("lexical error at (" + pos + ")");
            }
        }
        return new Token(Type.EOF, "$");
    }

    public enum Type {
        LPAREN, RPAREN, CONS, NIL, IDENT, NUMBER, EOF
    }

    public static class Token {
        private final Type type;
        private final String value;

        public Token(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        public Type getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}