package domain.node;
import domain.Memory;

/**
 * interface for arithmetic expression nodes, contains evaluate method
 */
public interface ArithmeticExpressionNode {
    int evaluate(Memory memory);
}
