package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;

import java.util.ArrayList;

/**
 * class for program node, includes arraylist of statement nodes
 */
public class ProgramNode {
    private ArrayList<StatementNode> statementsList;

    /**
     * constructor for ProgramNode
     * @param statementsList - list of statements to execute
     * @throws InvalidArgumentException if statementsList is null
     */
    public ProgramNode(ArrayList<StatementNode> statementsList) throws InvalidArgumentException{
        if(statementsList == null){
            throw new InvalidArgumentException("Null statementsList in ProgramNode constructor.");
        }
        this.statementsList = statementsList;
    }
    /**
     * method to add statement nodes to statement list
     * @param statementNode - statement node to add to list
     */
    public void addStatement(StatementNode statementNode){
        statementsList.add(statementNode);
    }

    /**
     * method to execute statement nodes in statement list
     * @param memory - memory at compile time
     */
    public int execute(Memory memory){
        for(StatementNode statementNode : statementsList){
            statementNode.evaluate(memory);
        }
        return 0;
    }
}
