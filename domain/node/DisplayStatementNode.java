package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;

/**
 * class for display statement nodes, contains id, implements execute
 */
public class DisplayStatementNode implements StatementNode{
    private String id;

    /**
     * constructor for display statement node
     * @param id - name of identifier
     * @throws InvalidArgumentException if id is null
     */
    public DisplayStatementNode(String id) throws InvalidArgumentException{
        if(id == null){
            throw new InvalidArgumentException("Null ID parameter in DisplayStatementNode constructor.");
        }
        this.id = id;
    }

    /**
     * method to execute display statement, prints value store with id to standard output
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        int value = memory.get(id);
        System.out.println(id + " = " + value);
    }
}
