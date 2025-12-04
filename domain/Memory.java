package domain;
import globalexceptions.InvalidArgumentException;
import globalexceptions.InvalidTokenException;

import java.util.HashMap;
/**
 * class for memory containing final hashmap
 */
public class Memory {
    private final HashMap<String, Integer> table;
    /**
     * constructor for memory, initializes hashmap
     */
    public Memory(){
        table = new HashMap<>();
    }

    /**
     * method to retrieve value of identifier
     * @param id - name of identifier to get
     * @return value of id, or 0 if not found
     */
    public int get(String id){
        if(!table.containsKey(id.toLowerCase())){
            throw new InvalidArgumentException(id + " does not exist.");
        }
        return table.get(id.toLowerCase());
    }

    /**
     * method to store value in memory for given identifier
     * @param id - name of identifier to set
     * @param value - value to set to identifier
     */
    public void set(String id, int value){
        table.put(id.toLowerCase(), value);
    }

    /**
     * method to check if id is in table
     * @param id - name of identifier to check
     * @return true or false
     */
    public boolean contains(String id){
        return table.containsKey(id.toLowerCase());
    }
}
