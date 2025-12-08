package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;

import java.util.List;
/**
 * class for while statement node, contains bool expr condition, statement list
 * implements bool expr node evaluate
 */
public class WhileStatementNode implements StatementNode {
    private BooleanExpressionNode condition;
    private List<StatementNode> statements;

    /**
     * constructor for WhileStatementNode
     * @param condition - boolean expression node
     * @param statements - list of statements
     * @throws InvalidArgumentException if any parameter is null
     */
    public WhileStatementNode(BooleanExpressionNode condition, List<StatementNode> statements) throws InvalidArgumentException{
        if(condition == null || statements == null){
            throw new InvalidArgumentException("Null argument in WhileStatementNode constructor.");
        }
        this.condition = condition;
        this.statements = statements;
    }

    /**
     * executes while statement
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        while(condition.evaluate(memory)){
            for(StatementNode statement : statements) statement.execute(memory);
        }
    }
}
