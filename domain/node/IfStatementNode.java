package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;
import java.util.List;
/**
 * class for if statement nodes, contains bool expr condition, if statement list, else statement list
 * implements statement node execute()
 */
public class IfStatementNode implements StatementNode {
    private BooleanExpressionNode condition;
    private List<StatementNode> ifBlock;
    private List<StatementNode> elseBlock; // null if not present

    /**
     * constructor for IfStatementNode
     * @param condition - boolean expression node
     * @param ifBlock - list of statements
     * @param elseBlock - list of statements
     * @throws InvalidArgumentException if any parameter is null
     */
    public IfStatementNode(BooleanExpressionNode condition, List<StatementNode> ifBlock, List<StatementNode> elseBlock){
        if(condition == null || ifBlock == null){
            throw new InvalidArgumentException("Null parameter in IfStatementNode constructor.");
        }
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    /**
     * executes if statement
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        if(condition.evaluate(memory)){
            for(StatementNode stmt : ifBlock) stmt.execute(memory);
        } else if(elseBlock != null){
            for(StatementNode stmt : elseBlock) stmt.execute(memory);
        }
    }
}