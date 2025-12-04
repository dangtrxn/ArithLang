package domain;
import globalexceptions.InvalidTokenException;
/**
 * class for a lexical analyzer which contains information, get_token method, and additional helper methods
 */
public class LexicalAnalyzer {
    private String source;
    private static int row_index = 0;
    private int col_index;
    /**
     * constructor for lexical analyzer
     * @param source - string to analyze
     */
    public LexicalAnalyzer(String source){
        this.source = source;
        this.col_index = 0;
    }

    /**
     * method to retrieve token based on the token type, skips all whitespace
     * @throws InvalidTokenException if an undefined token is being analyzed
     * @return current char as a token
     */
    public Token get_token() throws InvalidTokenException {
        //return EOS if at end of line
        if(col_index >= source.length()){
            return new Token(TokenType.EOS, "", row_index++, col_index);
        }

        //skip all whitespace
        while(col_index < source.length() && Character.isWhitespace(source.charAt(col_index))){
            col_index++;
        }

        //check again for end-of-line
        if(col_index >= source.length()){
            return new Token(TokenType.EOS, "", row_index++, col_index);
        }

        //get current char
        char current = source.charAt(col_index);

        //identify parenthesis
        if(isParenthesis(current)){
            //return left paren token
            if (current == '('){
                Token t = new Token(TokenType.LEFT_PAREN, "(", row_index, col_index);
                col_index++;
                return t;
            }
            else{
                //return right paren token
                Token t = new Token(TokenType.RIGHT_PAREN, ")", row_index, col_index);
                col_index++;
                return t;
            }
        }

        //identify operators
        if(isOperator(current)){
            TokenType type;
            switch (current){
                case '+':
                    type = TokenType.ADDITION;
                    break;
                case '-':
                    type = TokenType.SUBTRACTION;
                    break;
                case '*':
                    type = TokenType.MULTIPLICATION;
                    break;
                case '/':
                    type = TokenType.DIVISION;
                    break;
                default:
                    throw new InvalidTokenException("Unknown operator: " + current);
            }

            Token token = new Token(type, Character.toString(current), row_index,col_index);
            col_index++;
            return token;
        }

        //identify assignment operator
        if(isAssignment(current)){
            int start_index = col_index;
            col_index+=2;
            return new Token(TokenType.ASSIGNMENT, ":=", row_index,start_index);
        }

        //identify digits, return token with lexeme substring from [start index, end index]
        if(Character.isDigit(current)){
            int start_index = col_index;
            while(col_index < source.length() && Character.isDigit(source.charAt(col_index))){
                col_index++;
            }
            String lexeme = source.substring(start_index, col_index);
            return new Token(TokenType.INT_LIT, lexeme, row_index, start_index);
        }

        //identify statements and identifiers
        if(Character.isLetter(current)){
            int start_index = col_index;

            while(col_index < source.length() && Character.isLetterOrDigit(source.charAt(col_index))){
                col_index++;
            }

            //lowercase lexeme to ignore case sensitivity
            String lexeme = source.substring(start_index,col_index).toLowerCase();
            Token token;

            //create token based on lexeme, either statement or identifier
            switch (lexeme){
                case "let":
                    token = new Token(TokenType.LET, lexeme, row_index, start_index);
                    break;
                case "display":
                    token = new Token(TokenType.DISPLAY, lexeme, row_index, start_index);
                    break;
                case "input":
                    token = new Token(TokenType.INPUT, lexeme, row_index, start_index);
                    break;
                default:
                    token = new Token(TokenType.ID, lexeme, row_index, start_index);
                    break;
            }
            return token;
        }

        //INVALID TOKEN THROW ERROR
        throw new InvalidTokenException("Invalid token at row " + row_index + ", col " + col_index + ": " + source.charAt(col_index));
    }

    /**
     * method to identify parenthesis
     * @param c - current char
     * @return true if paren, false otherwise
     */
    public boolean isParenthesis(char c){
        return c == '(' || c ==')';
    }
    /**
     * method to identify operator
     * @param c - current char
     * @return true if operator, false otherwise
     */
    public boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    /**
     * method to identify assignment operator
     * @param c - current char
     * @return true if assignment, false otherwise
     */
    public boolean isAssignment(char c){
        return c == ':' && col_index + 1 < source.length() && source.charAt(col_index + 1) == '=';
    }

}