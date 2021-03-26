/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;

/**
 * @brief Initializes server by reading properties, according to which the 
 * TransactionManager, and AcountManager are created/initialized and then 
 * runs the multi-threaded server loop.
 * 
 * @author Matthew Flanders
 */
public class TransactionServerMain
{
    // initialize objects
    int accountCount;
    AccountManager accountManager = new AccountManager();
    TransactionManager transactionManager = new TransactionManager();
    Socket Socket;
    static LockManager lockManager = new LockManager();
    /**
     * @brief uses command line arguments to initialize objects
     * and begin to run the specified number of transactions
     */
    public static void main(String args[])
    {
        
        // check if arguments are valid
        
            // if not valid args stop and return error message and exit
        
        // read args passed
            
        // initialize account manager with number or accounts parameter
        
        // open server
                
        // begin server loop
        
            // when recieving a new connection/socket call openTransaction
            // method in TransactionManager        
    }
}
