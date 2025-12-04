package domain.node;

import domain.*;
import globalexceptions.InvalidArgumentException;
import globalexceptions.InvalidParseException;

/**
 * class for a binary expression node, inherits ExpressionNode, implements evaluate for addition and subtraction
 */
/*
(expr + term)
(expr - term)
 */
public class BinaryExpressionNodeArithmetic implements ArithmeticExpressionNode {
    private ArithmeticExpressionNode left;
    private TokenType operator;
    private TermNodeArithmetic right;

    /**
     * constructor for BinaryExpressionNodeArithmetic
     * @param left - ArithmeticExpressionNode
     * @param operator - operator type, + or -
     * @param right - TermNodeArithmetic
     * @throws InvalidArgumentException if any parameter is null
     */
    public BinaryExpressionNodeArithmetic(ArithmeticExpressionNode left, TokenType operator, TermNodeArithmetic right) throws InvalidArgumentException{
        if(left == null || operator == null || right == null){
            throw new InvalidArgumentException("Null parameter in BinaryExpressionNode constructor");
        }
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * method to evaluate binary expression, evaluate left child then right child, operate on both children
     * @param memory - memory at compile time
     * @throws InvalidParseException if invalid operator found
     * @return operation value on both children
     */
    @Override
    public int evaluate(Memory memory){
        int leftVal = left.evaluate(memory);
        int rightVal = right.evaluate(memory);

        switch(operator){
            case ADDITION:
                return leftVal + rightVal;
            case SUBTRACTION:
                return leftVal - rightVal;
            default:
                throw new InvalidParseException("Invalid operator in ExpressionNodeBinary: " + operator);
        }
    }
}
