package domain.node;

import globalexceptions.InvalidArgumentException;
import domain.Memory;

/**
 * class for paren factor node, implements FactorNode, implements evaluate for parenthesized expressions
 */
//(<expr>)
public class ParenFactorNode implements FactorNodeArithmetic {
    private ArithmeticExpressionNode expr;

    /**
     * constructor for ParenFactorNode
     * @param expr - arithmetic expression node
     * @throws InvalidArgumentException - if expr is null
     */
    public ParenFactorNode(ArithmeticExpressionNode expr) throws InvalidArgumentException {
        if(expr == null){
            throw new InvalidArgumentException("Null expression parameter in ParenFactorNode constructor");
        }
        this.expr = expr;
    }

    /**
     * method to evaluate paren factor node
     * @return expression evaluation
     */
    @Override
    public int evaluate(Memory memory){
        return expr.evaluate(memory);
    }
}
