package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;

/**
 * class for identifier node, implements FactorNode, implements evaluate for identifiers
 */
//<id>
public class IdentifierNode implements FactorNodeArithmetic{
    private String id;

    /**
     * constructor for IdentifierNode
     * @param id - identifier name
     * @throws InvalidArgumentException if id is null
     */
    public IdentifierNode(String id) throws InvalidArgumentException{
        if(id == null){
            throw new InvalidArgumentException("Null id in IdentifierNode constructor.");
        }
        this.id = id;
    }

    /**
     * method to evaluate identifier node
     * @param memory - memory at compile time
     * @return string id
     */
    @Override
    public int evaluate(Memory memory){
        return memory.get(id);
    }
}
