package domain.node;
import domain.Memory;
import globalexceptions.InvalidArgumentException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * class for input statement nodes, contains id and scanner object, implements execute
 */
public class InputStatementNode implements StatementNode{
    private String id;
    private static Scanner sc = new Scanner(System.in);

    /**
     * constructor for input statement node
     * @param id - name of identifier
     * @throws InvalidArgumentException if id is null
     */
    public InputStatementNode(String id) throws InvalidArgumentException{
        if(id == null){
            throw new InvalidArgumentException("Null ID parameter in InputStatementNode constructor.");
        }
        this.id = id;
    }

    /**
     * method to execute input statement, takes in integer value from standard input and stores in location of id
     * @param memory - memory at compile time
     */
    @Override
    public void execute(Memory memory){
        System.out.print("Enter value for " + id + ": ");
        try {
            int value = sc.nextInt();
            memory.set(id, value);
        }
        catch (InputMismatchException e){
            System.err.println("Invalid value entered for " + id + ". Expected an integer.");
        }
    }
}
