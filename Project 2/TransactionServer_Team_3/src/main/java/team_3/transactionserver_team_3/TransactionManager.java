/**
  * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
  * @date 3/7/21
  * 
  */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;
import java.util.Vector;
import java.lang.Thread;

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
    private AccountManager accManager;
    
    public TransactionManager( AccountManager accManager )
    {
        // initialize varibales
        this.transactions = new Vector<Transaction>();
        this.accManager = accManager;
    }    
    
    /**
     * @brief creates a new transaction object adds it to the transaction list
     * then takes socket object from the transaction server and gives it 
     * to a newly created TransactionManagerWorker.
     */
    public void openTransaction( Socket socket )
    {
        System.out.println("Open transaction called");
        // new transaction added to transactions vector and new worker thread
        Transaction newTransaction = new Transaction();
        transactions.add(newTransaction);
        Thread workerThread = new Thread( new TransactionManagerWorker
                                                ( socket, 
                                                 newTransaction, 
                                                 accManager) );
        workerThread.start();
        System.out.println("New transaction started with new worker.");
    }
    
    /**
     * @brief removes a passed in transaction from the transactions vector
     * @param toBeRemoved 
     */
    public void removeTransaction( Transaction toBeRemoved )
    {
        System.out.println("Remove transaction called.");
        // may not need this conditional, since transactions remove
        // themselves, but can be good for checking for errors
        // if the remove is successful it returns true
        if( transactions.remove( toBeRemoved ) )
        {
            // send a confirm?
            System.out.println("Transaction removed successfuly.");
        }
        else
        {
            // send an error?
            System.out.println("Removal to transaction failed.");
        }
    }
}