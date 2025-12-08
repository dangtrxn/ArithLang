package domain.node;

import domain.Memory;
import globalexceptions.InvalidArgumentException;

import java.util.List;
/**
 * class for for statement node, contains arith expr node start, arith expr node end, and statements list
 * implements bool expr node evaluate
 */
public class ForStatementNode implements StatementNode {
    private String id;
    private ArithmeticExpressionNode startExpr;
    private ArithmeticExpressionNode endExpr;
    private List<StatementNode> statements;

    /**
     * constructor for ForStatementNode
     * @param id - string id
     * @param startExpr - start expression
     * @param endExpr - end expression
     * @param statements - list of statements
     * @throws InvalidArgumentException if any parameter is null
     */
    public ForStatementNode(String id, ArithmeticExpressionNode startExpr, ArithmeticExpressionNode endExpr, List<StatementNode> statements){
        this.id = id;
        this.startExpr = startExpr;
        this.endExpr = endExpr;
        this.statements = statements;
    }

    /**
     * executes for statement
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        int start = startExpr.evaluate(memory);
        int end = endExpr.evaluate(memory);

        for(int i = start; i <= end; i++){
            memory.set(id, i);
            for(StatementNode statement : statements){
                statement.execute(memory);
            }
        }
    }
}
