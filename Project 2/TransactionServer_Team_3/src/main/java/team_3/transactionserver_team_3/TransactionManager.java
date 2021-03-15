/**
  * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
  * @date 3/7/21
  * 
  */
package team_3.transactionserver_team_3;

// imports
import java.net.ServerSocket;
import java.lang.Runnable;
import java.util.ArrayList;

/**
  * @brief Oversees all transactions and spawns TransactionManagerWorkers to
  * handle incoming transactions.
  * 
  * @author Matthew Flanders
  */
public class TransactionManager implements Runnable
{
    // initialize objects and variables
    private static ServerSocket serverSocket;
    private ArrayList<TransactionManagerWorker> activeTransactions;
    private int counter;
        
    public TransactionManager( ServerSocket serverSocket)
    {
        
    }
    
    public void run()
    {
        
    }
    
    /**
     * @brief takes socket to client and gives it to TransactionManagerWorker
     */
    public void handoff()
    {
        
    }
}