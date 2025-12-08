package domain;
import domain.node.ProgramNode;
import globalexceptions.InvalidArgumentException;

/**
 * class for parse tree, evaluates value of expression
 */
public class ParseTree {
    private Memory memory = new Memory();
    private ProgramNode programNode;

    /**
     * constructor for parse tree
     * @param programNode - program node as root of tree
     * @throws InvalidArgumentException if programNode is null
     */
    public ParseTree(ProgramNode programNode) throws InvalidArgumentException {
        if(programNode == null) throw new InvalidArgumentException("Null expression parameter in ParseTree constructor.");
        this.programNode = programNode;
    }

    /**
     * method to evaluate value of expression tree
     * @return value of tree in postorder traversal
     */
    public void evaluate(){
        programNode.execute(memory);
    }
}
