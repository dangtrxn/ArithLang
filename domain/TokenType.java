package domain;
/**
 * enumerated type for defined token types
 */
public enum TokenType {
    //arithmetic operators
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,

    //relational operators
    LESS_THAN, // <
    GREATER_THAN, // >
    LESS_EQUAL, // <=
    GREATER_EQUAL, // >=
    EQUAL, // =
    NOT_EQUAL, // /=

    //parentheses
    LEFT_PAREN,
    RIGHT_PAREN,

    //identifiers
    ID,
    //integer literals
    INT_LIT,

    //assignment operator, :=
    ASSIGNMENT,

    //keywords
    LET, //let
    DISPLAY, //display
    INPUT, //input
    IF,
    ELSE,
    ELIF,
    WHILE,
    FOR,
    IN, // in keyword for for loops
    RANGE, // .. range for for loops
    COLON, // :

    //indentation
    INDENT,
    DEDENT,

    //ending
    EOL,
    EOS;
}
