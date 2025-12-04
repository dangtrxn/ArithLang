package domain.node;

import globalexceptions.InvalidArgumentException;
import domain.Memory;

/**
 * class for unary - operation, inherits FactorNode, implements evaluate for unary negation operation
 */
//(-factor)
public class NegFactorNode implements FactorNodeArithmetic {
    private ArithmeticExpressionNode expr;

    /**
     * constructor for NegFactorNode
     * @param expr - arithmetic expression node
     * @throws InvalidArgumentException - if expr is null
     */
    public NegFactorNode(ArithmeticExpressionNode expr) throws InvalidArgumentException {
        if(expr == null){
            throw new InvalidArgumentException("Null expression parameter in NegFactorNode constructor");
        }
        this.expr = expr;
    }

    /**
     * method to evaluate unary negation operation
     * @param memory - memory at compile time
     * @return negation operation on expression
     */
    @Override
    public int evaluate(Memory memory){
        return -expr.evaluate(memory);
    }
}
