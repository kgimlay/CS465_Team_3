/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/24/21
 */
package team_3.transactionserver;

// imports
import java.util.ArrayList;

/**
 * @brief a unique transaction object for a transaction
 * @author Matthew Flanders
 */
public class Transaction
{
    public int id;
    public ArrayList<Lock> heldLocks;
    public ArrayList<String> log;
    private static int transactionCounter = 0;
    
    /**
     * 
     */
    public Transaction()
    {
        transactionCounter ++;
        this.id = transactionCounter;
        this.log = new ArrayList<String>();
    }
    
    /**
     * 
     * @param callLoc
     * @param logMessage 
     */
    public void log(String callLoc, String logMessage)
    {
        // save and also print
        String logPrtStr = "Transaction #" + this.id + " [" + callLoc + "] " 
                + logMessage;
        System.out.println(logPrtStr);
        this.log.add(logMessage);   // yep, saving just the message
    }
    
    /**
     * 
     */
    public void printLog()
    {
        String printStr = "\nTransaction #" + this.id + " commited. Log:\n";
        for (int index = 0; index < this.log.size(); index++)
        {
            printStr += "-----" + this.log.get(index) + "\n";
        }
        
        System.out.println(printStr);
    }
}
