/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 * 
 */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;
import java.lang.Runnable;

/**
 * @brief A single thread used to handle a transaction between two accounts. 
 * Establishes the connection from the client, then reads messages from the
 * network and translates for the account manager to preform operations. 
 * Keeps looping until a close transaction message is received. 
 * 
 * 
 * @author Matthew Flanders
 */
public class TransactionManagerWorker implements Runnable
{
    // initialize variables and objects
    private static Socket socket;
    private static Transaction workerTransaction; 
    
    /**
     * @brief initialization of worker
     * @param socket socket connection to the client
     * @param transaction unique transaction object 
     */
    public void TransactionManagerWorker( Socket socket, Transaction transaction)
    {
        // initialize variables
               
    }
            
    /**
     * @brief run method to establish connection to client and loop until
     * a close message has been received
     */
    public void run()
    {
        // open connection from the client
        
        // initialize loop flag to true
        
        // begin loop 
        
            // read message from client
        
            // translate low level message into high level action   
        
            // if the message was a close message
        
                // close the connection with client
        
                // end loop, set loop flag to false
        
            // call to AccountManager to prefrom an operation
        
                // if open send open
        
                // else read send read message
        
                // else write send write message
    }
}
