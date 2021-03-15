/**
  * @authors Kevin Imlay
  * @date 3/14/21
  */

package team_3.transactionserver_team_3;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/** Transaction client server proxy as a layer between the client and the
 * transaction server.
 * 
 * Takes in commands from the client and sends messages to the transaction
 * server in accordance to those commands.
 * 
 * @todo Need to figure out how to handle trying to access accounts that don't
 * exist.
 * @todo Need to figure out exactly what to do for IO exceptions.
 */
public class TransactionClientServerProxy {
    
    private Socket connSocket;
    private ObjectInputStream inObjStream;
    private ObjectOutputStream outObjStream;
    
    
    /** Create a new transaction (server proxy) to run. 
     * 
     * @param socket - Socket object for communication with the transaction
     * server.
     */
    public TransactionClientServerProxy(Socket socket)
    {
        this.connSocket = socket;
        try
        {
            inObjStream = new ObjectInputStream( socket.getInputStream() );
            outObjStream = new ObjectOutputStream( socket.getOutputStream() );
        }
        catch (IOException ioE)
        {
            // handle exception
        }
    }
    
    
    /** Opens a transaction with the transaction server.
     * 
     * Sends an open transaction message to the transaction server.
     * 
     * @throws IOException.
     */
    public void openTransaction()
    {
        
    }
    
    
    /** Close the transaction with the transaction server.
     * 
     * Sends a close transaction message to the transaction server.
     * 
     * @throws IOException.
     */
    public void closeTransaction()
    {
        
    }
    
    
    /** Write a new balance to an account in the transaction server.
     * 
     * Sends a write message to the transaction server with the specified
     * account ID and new balance to write.
     * 
     * @throws IOException.
     */
    public void write(int accountID, int newBalance)
    {
        
    }
    
    
    /** Read the balance of an account in the transaction server.
     * 
     * Sends a write message to the transaction server with the account id to
     * read the balance from. Waits for a response read message and returns the
     * account balance.
     * 
     * @return int - The balance of the account.
     * 
     * @throws IOException.
     */
    public int read(int accountID)
    {
        return 0;   // placeholder return to let compile
    }
}