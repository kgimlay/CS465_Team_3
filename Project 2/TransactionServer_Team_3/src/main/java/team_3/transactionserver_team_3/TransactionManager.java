/**
  * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
  * @date 3/7/21
  * 
  */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;
import java.util.Vector;

/**
  * @brief Oversees all transactions. When a socket object is received 
  * openTransaction() is called with the socket object as the parameter 
  * and a new worker thread is created and passed the socket object.
  * 
  * @author Matthew Flanders
  */
public class TransactionManager
{
    // initialize objects and variables
    private Vector<Transaction> transactions;
    
    public TransactionManager()
    {
        // initialize varibales
        this.transactions = new Vector<Transaction>();        
    }    
    
    /**
     * @brief takes socket object from the transaction server and gives it to
     * a newly created TransactionManagerWorker thread.
     */
    public void openTransaction( Socket socket )
    {
        // create a new TransactionManagerWorker thread with socket object
        
        // create new transaction object and add to active transactions vector 
    }
}