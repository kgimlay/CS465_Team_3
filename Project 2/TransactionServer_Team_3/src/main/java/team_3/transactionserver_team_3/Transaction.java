/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/24/21
 */
package team_3.transactionserver_team_3;

// imports
import java.util.ArrayList;

/**
 * @brief a unique transaction object for a transaction
 * @author Matthew Flanders
 */
public class Transaction
{
    // initialize variables/objects
    int id;
    ArrayList<Lock> heldLocks;
    ArrayList<String> log;
    
    public Transaction( int id )
    {
        this.id = id;
    }
}
