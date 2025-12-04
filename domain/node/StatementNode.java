package domain.node;
import domain.Memory;
import globalexceptions.InvalidParseException;

/**
 * interface for statement nodes, contains execute method and default evaluate method
 */
public interface StatementNode {

    void execute(Memory memory);

    /**
     * default method which delegates to execute, used for different node types since statements return void
     * @param memory - memory at compile time
     * @return 0
     */
    default int evaluate(Memory memory){
        execute(memory);
        return 0;
    }
}
