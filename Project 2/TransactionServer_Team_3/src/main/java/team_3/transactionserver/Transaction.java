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
    public ArrayList heldLocks;
    public ArrayList log;
    private static int transactionCounter = 0;
    
    /** Create a new Transaction object. 
     * 
     */
    public Transaction()
    {
        transactionCounter ++;
        this.id = transactionCounter;
        this.log = new ArrayList<String>();
        this.heldLocks = new ArrayList<Lock>();
    }
    
    /** Will print information relating to a specific transaction's actions.
     * Saves the message as well to the log array list for later printing.
     * 
     * @param callLoc - The file name of where the action originated
     * @param logMessage - Message specifying the action that occurred
     */
    public void log(String callLoc, String logMessage)
    {
        // save and also print
        String logPrtStr = "Transaction #" + this.id + " [" + callLoc + "] " 
                + logMessage;
        System.out.println(logPrtStr);
        this.log.add(logMessage);   // yep, saving just the message
    }
    
    /** Prints a transaction's actions that occurred in its lifetime.
     * 
     */
    public void printLog()
    {
        String printStr = "\nTransaction #" + this.id + " commited. Log:\n";
        for (int index = 0; index < this.log.size(); index++)
        {
            printStr += "   " + this.log.get(index) + "\n";
        }
        
        System.out.println(printStr);
    }
}
