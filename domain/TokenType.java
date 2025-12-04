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

    //end of source
    EOS;
}
