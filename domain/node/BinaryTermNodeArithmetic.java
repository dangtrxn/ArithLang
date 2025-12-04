package domain.node;

import domain.*;
import globalexceptions.InvalidArgumentException;
import globalexceptions.InvalidParseException;

/**
 * class for binary term node, extends TermNode, implements evaluate for multiplication and division
 */
/*
(term * factor)
(term / factor)
 */
public class BinaryTermNodeArithmetic implements TermNodeArithmetic {
    private TermNodeArithmetic left;
    private TokenType operator;
    private FactorNodeArithmetic right;

    /**
     * constructor for BinaryTermNode
     * @param left - TermNodeArithmetic
     * @param operator - operator type, * or /
     * @param right - FactorNodeArithmetic
     * @throws InvalidArgumentException if any parameter is null
     */
    public BinaryTermNodeArithmetic(TermNodeArithmetic left, TokenType operator, FactorNodeArithmetic right) throws InvalidArgumentException{
        if(left == null || operator == null || right == null){
            throw new InvalidArgumentException("Null parameter in BinaryTermNode constructor");
        }
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * method to evaluate binary term, evaluate left child then right child, operate on both children
     * @param memory - memory at compile time
     * @throws InvalidParseException if invalid operator found
     * @return operation value on both children
     */
    @Override
    public int evaluate(Memory memory){
        int leftVal = left.evaluate(memory);
        int rightVal = right.evaluate(memory);

        switch(operator){
            case MULTIPLICATION:
                return leftVal * rightVal;
            case DIVISION:
                return leftVal / rightVal;
            default:
                throw new InvalidParseException("Invalid operator in TermNodeBinary: " + operator);
        }
    }
}
