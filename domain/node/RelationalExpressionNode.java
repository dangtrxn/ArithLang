package domain.node;
import domain.Memory;
import domain.TokenType;
import globalexceptions.InvalidArgumentException;
import globalexceptions.InvalidParseException;

/**
 * class for relational expression nodes, contains arith expr left, relop, arith expr right
 * implements bool expr node evaluate()
 */
public class RelationalExpressionNode implements BooleanExpressionNode {
    private ArithmeticExpressionNode left;
    private TokenType operator;
    private ArithmeticExpressionNode right;

    /**
     * constructor for RelationalExpressionNode
     * @param left - ArithmeticExpressionNode
     * @param operator - relational operator
     * @param right - ArithmeticExpressionNode
     * @throws InvalidArgumentException if any parameter is null
     */
    public RelationalExpressionNode(ArithmeticExpressionNode left, TokenType operator, ArithmeticExpressionNode right) throws InvalidArgumentException{
        if(left == null || operator == null || right == null){
            throw new InvalidArgumentException("Invalid parameter in RelationalExpressionNode constructor.");
        }
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * evaluates result of relational expression
     * @param memory - memory at compile time
     * @return result of relational expression
     */
    @Override
    public boolean evaluate(Memory memory){
        int l = left.evaluate(memory);
        int r = right.evaluate(memory);
        boolean ans;

        switch (operator) {
            case GREATER_THAN:
                ans = l > r;
                break;
            case LESS_THAN:
                ans = l < r;
                break;
            case GREATER_EQUAL:
                ans = l >= r;
                break;
            case LESS_EQUAL:
                ans = l <= r;
                break;
            case EQUAL:
                ans = l == r;
                break;
            case NOT_EQUAL:
                ans = l != r;
                break;
            default:
                throw new InvalidParseException("Unknown relational operator: " + operator);
        }

        return ans;
    }
}
