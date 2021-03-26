/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 * 
 */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;
import java.lang.Runnable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.util.NoSuchElementException;

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
    private AccountManager accManager;
    private Transaction workerTransaction; 
    private ObjectInputStream inObjStream;
    private ObjectOutputStream outObjStream;
    
    /**
     * @brief initialization of worker
     * @param socket socket connection to the client
     * @param transaction unique transaction object 
     */
    public void TransactionManagerWorker( Socket socket, 
                                          Transaction transaction, 
                                          AccountManager accManager)
    {
        // initialize variables
        this.workerTransaction = transaction;
        this.accManager = accManager;        
        // create object streams for communication with message objects
        try
        {
            inObjStream = new ObjectInputStream( socket.getInputStream() );
            outObjStream = new ObjectOutputStream( socket.getOutputStream() );
        }
        catch (IOException ioE)
        {
            System.out.println("An IO Exception has occured!\n\n" + ioE);
            // handle exception
        }
    }

    
    /**
     * @brief run method to establish connection to client and loop until
     * a close message has been received
     */
    public void run()
    {
        // try/catch to read network messages
        try
        {
            // initialize loop flag to true
            boolean noCloseMessage = true; 
            
            // begin loop 
            while( noCloseMessage )
            {        
                // read object from client and get the message type
                Object messageObj = inObjStream.readObject();
                // translate low level message into high level action   

                // if the message was a close message
                if( messageObj instanceof CloseTransMessage )
                {
                    // respond to client with confirm close message
                    // need close confirm message

                    // end loop, set loop flag to false
                    noCloseMessage = false;
                }
                // if not a close then some other message
                // if open message disregard message
                else if( messageObj instanceof OpenTransMessage)
                {
                    outObjStream.writeObject(new OpenTransMessage( 0 ));
                }
                // else read send read message
                else if( messageObj instanceof ReadMessage)
                {
                    
                    //read the account number from transaction
                    int accNum = ((ReadMessage) messageObj).accountNum;
                    //read bal
                    Object tempBal = ((ReadMessage) messageObj).bal.get();                        
                    int bal = (int) tempBal;
                    
                    AccountManager.read( accNum, workerTransaction);
                }
                // else write send write message
                else if( messageObj instanceof WriteMessage)
                {
                    accManager.WriteMessage();
                }
            }
        }
        catch( IOException ioE )
        {
            System.out.println("Error in recieving message"+ioE);
        }
        catch (ClassNotFoundException clE)
        {
            System.out.println("Error in recieving incoming message. " + clE);
        }
        
    }
}
