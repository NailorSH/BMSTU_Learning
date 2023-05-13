import java.text.NumberFormat;
import java.text.ParseException;

/*
 * <Expr>::= ( cons <List> ) | nil | NUMBER
 * <List>::= <Expr> <Tail>
 * <Tail>::= <List> | ε
 *
 * ( cons 10 ( cons 1 ) nil )
 * */

class Parser {
    private char currentChar;
    private String currentWord;
    private final String input;
    private String[] words;
    private int index = 0;
//    private int line;
//    private int col;

    public void parse() throws Exception {
        parseExpr();
//        if (!isWhitespace(currentChar)) {
//            throw new RuntimeException("syntax error at (" + line + "," + col + ")");
//        }
    }

    public Parser(String source) {
        this.input = source;
        words = source.trim().split("\\s+");
    }

    private void parseExpr() throws Exception {
        currentWord = words[index];

        if (isOpenBracket()) {
            index++;
            if(isCons()) {
                index++;

                System.out.println("<Expr> => ( cons <List> )");

                parseList();

                if(!isCloseBracket()) throw new Exception();
            }
        } else if (isNil()) {
            index++;
            System.out.println("<Expr> => nil");
        } else if(isNumber()) {
            index++;
            System.out.println("<Expr> => NUMBER");
        } else throw new Exception();
    }

    private boolean isOpenBracket() {
        currentWord = words[index];
        return currentWord.equals("(");
    }

    private boolean isCons() {
        currentWord = words[index];
        return currentWord.equals("cons");
    }

    private boolean isCloseBracket() {
        currentWord = words[index];
        return currentWord.equals("(");
    }

    private boolean isNumber() {
        currentWord = words[index];
        try {
            NumberFormat.getInstance().parse(currentWord);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isNil() {
        currentWord = words[index];
        return currentWord.equals("nil");
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }

    private void parseList() throws Exception {
        System.out.println("<List> => <Expr> <Tail>");

        parseExpr();
        parseTail();
    }


    private void parseTail() throws Exception {
        currentWord = words[index];

        try {
            parseList();
            System.out.println("<Tail> => <List>");
        } catch (Exception e){
            System.out.println("<Tail> => ε");
        }
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }
}