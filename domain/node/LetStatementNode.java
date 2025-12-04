package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;

/**
 * class for let statement nodes, contains id and arithmetic expression node, implements execute
 */
public class LetStatementNode implements StatementNode{
    private String id;
    private ArithmeticExpressionNode expr;

    /**
     * constructor for let statement node
     * @param id - name of identifier
     * @param expr - following arithmetic expression node
     * @throws InvalidArgumentException if parameter is null
     */
    public LetStatementNode(String id, ArithmeticExpressionNode expr) throws InvalidArgumentException {
        if(id == null || expr == null){
            throw new InvalidArgumentException("Null parameter in LetStatementNode constructor.");
        }
        this.id = id;
        this.expr = expr;
    }

    /**
     * method to execute let statement, sets value of expr to id
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        int value = expr.evaluate(memory);
        memory.set(id, value);
    }
}
