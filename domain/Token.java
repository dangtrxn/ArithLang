package domain;

import globalexceptions.InvalidTokenException;

/**
 * class for a lexical analyzer which contains information, get methods, and additional helper methods
 */
public class Token {
    private TokenType type;
    private String lexeme;
    private int row;
    private int col;

    /**
     * constructor for token, takes in source string to analyze
     * @param type - TokenType
     * @param lexeme - subject of token
     * @param row - token row
     * @param col - token col
     * @throws InvalidTokenException if any constructor params are empty
     */
    public Token(TokenType type, String lexeme, int row, int col){
        if (type == null || lexeme == null) {
            throw new InvalidTokenException("Null tokenType or lexeme in Token constructor.");
        }
        if (row < 0 || col < 0) {
            throw new InvalidTokenException("Row and column indices must be non-negative.");
        }
        this.type = type;
        this.lexeme = lexeme;
        this.row = row;
        this.col = col;
    }

    /**
     * method to get token type
     * @return token type
     */
    public TokenType get_type(){
        return type;
    }
    /**
     * method to get token lexeme
     * @return token lexeme
     */
    public String get_lexeme(){
        return lexeme;
    }
    /**
     * method to get token row
     * @return token row
     */
    public int get_row(){
        return row;
    }
    /**
     * method to get token col
     * @return token col
     */
    public int get_col(){
        return col;
    }
    /**
     * method to determine if token type is relational
     * @return boolean value
     */
    public boolean isRelational(){
        return type == TokenType.LESS_THAN || type == TokenType.LESS_EQUAL || type == TokenType.GREATER_THAN || type == TokenType.GREATER_EQUAL || type == TokenType.EQUAL || type == TokenType.NOT_EQUAL;
    }
    /**
     * method to print token information
     * @return token information in string form
     */
    @Override
    public String toString(){
        return "<Token: " + get_lexeme() + " Type: " + get_type() + " Row: " + get_row() + " Col: " + get_col() + ">";
    }
}
