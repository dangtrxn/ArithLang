package domain;
import globalexceptions.InvalidTokenException;
import java.util.*;
/**
 * class for a lexical analyzer which contains information, get_token method, and additional helper methods
 */
public class LexicalAnalyzer {
    private final String source;
    private int index;
    private int line;
    private int col;

    private boolean atStart;
    private final Deque<Integer> indentStack;
    private final Queue<Token> pendingTokens;

    /**
     * constructor for lexical analyzer
     * @param source - source program file
     */
    public LexicalAnalyzer(String source){
        this.source = source;
        this.index = 0;
        this.line = 0;
        this.col = 0;
        this.atStart = true;
        this.indentStack = new ArrayDeque<>();
        this.indentStack.push(0);
        this.pendingTokens = new ArrayDeque<>();
    }

    /**
     * method to retrieve token based on the token type, skips all whitespace
     * @throws InvalidTokenException if an undefined token is being analyzed
     * @return current char as a token
     */
    public Token get_token() throws InvalidTokenException {
        //return any queued indent/dedent
        if(!pendingTokens.isEmpty()){
            return pendingTokens.poll();
        }

        //handle EOS, pop remaining dedents
        if(index >= source.length()){
            while(indentStack.size() > 1){
                indentStack.pop();
                pendingTokens.add(new Token(TokenType.DEDENT,"",line, col));
            }
            if(!pendingTokens.isEmpty()){
                return pendingTokens.poll();
            }
            return new Token(TokenType.EOS,"",line,col);
        }

        //get current char
        char current = source.charAt(index);

        //handle new lines + empty lines
        if(current == '\n'){
            Token eol = new Token(TokenType.EOL, "\n",line, col);
            index++;
            line++;
            col=0;
            atStart = true;
            return eol;
        }

        //handle indentation at line start
        if(atStart){
            int spaces = 0;

            while(index < source.length()){
                current = source.charAt(index);

                //skip spaces at start of line
                if(current == ' '){
                    spaces++;
                    index++;
                    col++;
                }
                //indent = 4 spaces
                else if(current == '\t'){
                    spaces += 4;
                    index++;
                    col += 4;
                }
                else{
                    break;
                }
            }

            atStart = false;

            int currentIndent = indentStack.peek();
            if(spaces > currentIndent){
                //push indent
                indentStack.push(spaces);
                return new Token(TokenType.INDENT,"",line, col);
            }
            else if(spaces < currentIndent){
                //at least one dedent
                while(spaces < indentStack.peek()){
                    indentStack.pop();
                    pendingTokens.add(new Token(TokenType.DEDENT,"",line, col));
                }

                if(spaces != indentStack.peek()){
                    throw new InvalidTokenException("Indentation error on line " + line);
                }
                return pendingTokens.poll();
            }
        }

        //handle spaces + tabs in middle of lines
        while(index < source.length()){
            current = source.charAt(index);
            if(current == ' ' || current == '\t'){
                index++;
                col++;
            }
            else{
                break;
            }
        }

        if(index >= source.length()){
            return get_token();
        }

        current = source.charAt(index);
        int startCol = col;
        int startIndex = index;

        //identify parenthesis
        if(isParenthesis(current)){
            //return left paren token
            if (current == '('){
                index++;
                col++;
                return new Token(TokenType.LEFT_PAREN, "(", line, startCol);
            }
            //return right paren token
            else{
                index++;
                col++;
                return new Token(TokenType.RIGHT_PAREN, ")", line, startCol);
            }
        }

        //identify arithmetic and not equals
        if(isArithmetic(current)){
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
                    //not equals
                    if(peek() == '='){
                        type = TokenType.NOT_EQUAL;
                        index++;
                        col++;
                    }
                    //division
                    else{
                        type = TokenType.DIVISION;
                    }
                    break;
                default:
                    throw new InvalidTokenException("Unknown operator: " + current);
            }
            col++;
            index++;
            return new Token(type, source.substring(startIndex,index), line,startCol);
        }

        //identify colon and assignment operator
        if(current == ':'){
            if(peek() == '='){
                col+=2;
                index+=2;
                return new Token(TokenType.ASSIGNMENT, ":=", line,startCol);
            }
            index++;
            col++;
            return new Token(TokenType.COLON,":",line,startCol);
        }

        if(current == '.' && peek() == '.'){
            col+=2;
            index+=2;
            return new Token(TokenType.RANGE,"..",line,startCol);
        }

        //identify relation operator
        if(isRelational(current)){
            TokenType type;
            switch(current){
                case '<':
                    if(peek() == '='){
                        type = TokenType.LESS_EQUAL;
                        index++;
                        col++;
                    }
                    else{
                        type = TokenType.LESS_THAN;
                    }
                    break;
                case '>':
                    if(peek() == '='){
                        type = TokenType.GREATER_EQUAL;
                        index++;
                        col++;
                    }
                    else{
                        type = TokenType.GREATER_THAN;
                    }
                    break;
                case '=':
                    type = TokenType.EQUAL;
                    break;
                default:
                    throw new InvalidTokenException("Unknown operator: " + current);
            }
            index++;
            col++;
            return new Token(type, source.substring(startIndex,index), line, startCol);
        }

        //identify digits, return token with lexeme substring from [start index, end index]
        if(Character.isDigit(current)){
            while(index < source.length() && Character.isDigit(source.charAt(index))){
                index++;
                col++;
            }
            return new Token(TokenType.INT_LIT, source.substring(startIndex, index), line, startCol);
        }

        //identify statements and identifiers
        if(Character.isLetter(current)){
            while(index < source.length() && Character.isLetterOrDigit(source.charAt(index))){
                index++;
                col++;
            }

            //lowercase lexeme to ignore case sensitivity
            String lexeme = source.substring(startIndex, index).toLowerCase();
            TokenType type;

            //create token based on lexeme, either statement or identifier
            switch (lexeme){
                case "let":
                    type = TokenType.LET;
                    break;
                case "display":
                    type = TokenType.DISPLAY;
                    break;
                case "input":
                    type = TokenType.INPUT;
                    break;
                case "if":
                    type = TokenType.IF;
                    break;
                case "else":
                    type = TokenType.ELSE;
                    break;
                case "elif":
                    type = TokenType.ELIF;
                    break;
                case "while":
                    type = TokenType.WHILE;
                    break;
                case "for":
                    type = TokenType.FOR;
                    break;
                case "in":
                    type = TokenType.IN;
                    break;
                case "range":
                    type = TokenType.RANGE;
                    break;
                case "colon":
                    type = TokenType.COLON;
                    break;
                default:
                    type = TokenType.ID;
                    break;
            }
            return new Token(type, lexeme, line,startCol);
        }

        //INVALID TOKEN THROW ERROR
        throw new InvalidTokenException("Invalid token at line " + line + ", col " + col + ": " + current);
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
    public boolean isArithmetic(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public boolean isRelational(char c){
        return c == '<' || c == '>' || c == '=';
    }
    /**
     * method to peek at next char
     * @return next char or null
     */
    public char peek(){
        if(index + 1 < source.length()){
            return source.charAt(index+1);
        }
        else{
            return '\0';
        }
    }

}