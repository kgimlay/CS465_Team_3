/**
  * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
  * @date 3/7/21
  * 
  */
package team_3.transactionserver;

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
    private LockManager lockManager;
    
    public TransactionManager( AccountManager accManager, 
            LockManager lockManager )
    {
        // initialize varibales
        this.transactions = new Vector<Transaction>();
        this.accManager = accManager;
        this.lockManager = lockManager;
    }    
    
    /**
     * @brief creates a new TransactionManagerWorker, to handle a transaction
     */
    public void newWorkerThread( Socket socket )
    {
        Thread workerThread = new Thread( new TransactionManagerWorker
                                                ( socket, 
                                                  this, 
                                                  accManager,
                                                  lockManager) );
        workerThread.start();
    }
    
    /**
     * @brief creates/returns a new transaction object and adds it the the 
     * transactions vector
     * @return a new unique transaction object
     */
    public Transaction newTransaction()
    {
        Transaction newTransaction = new Transaction();
        transactions.add(newTransaction);
        return newTransaction;
    }
    
    /**
     * @brief removes a passed in transaction from the transactions vector
     * @param toBeRemoved 
     */
    public void removeTransaction( Transaction toBeRemoved )
    {
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
            System.out.println("Removal of transaction failed.");
        }
    }
}