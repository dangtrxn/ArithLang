package domain.node;
import domain.Memory;
/**
 * class for num node, extends FactorNode, implements evaluate for integer literal
 */
//(int_lit)
public class NumNode implements FactorNodeArithmetic {
    private int num;

    /**
     * constructor for NumNode
     * @param num - integer value
     */
    public NumNode(int num){
        this.num = num;
    }

    /**
     * method to evaluate num node
     * @param memory - memory at compile time
     * @return integer
     */
    @Override
    public int evaluate(Memory memory){
        return num;
    }
}
