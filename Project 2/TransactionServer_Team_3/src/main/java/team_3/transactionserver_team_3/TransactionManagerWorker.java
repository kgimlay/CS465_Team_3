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
     * @param accManager reference to account manager
     */
    public TransactionManagerWorker( Socket socket,  
                                     Transaction transaction,
                                     AccountManager accManager )
    {
        // initialize variables
        this.workerTransaction = transaction;
        this.accManager = accManager;        
        // create object streams for communication with message objects
        try
        {
            outObjStream = new ObjectOutputStream( socket.getOutputStream() );
            inObjStream = new ObjectInputStream( socket.getInputStream() );
        }
        catch ( IOException ioE )
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
                                                                        // TO-DO
                                                                        // need to create close confirm message 
                                                                        // or not if replying back with same message is enough
                    // ack be sending back original message
                    outObjStream.writeObject( messageObj );
                    
                    // end loop, set loop flag to false
                    noCloseMessage = false;
                }
                // if not a close then some other message
                // if message is of open type, disregard message
                else if( messageObj instanceof OpenTransMessage )
                {
                    outObjStream.writeObject( 
                            new OpenTransMessage( workerTransaction.id )); 
                }
                // if message is of read type
                else if( messageObj instanceof ReadMessage )
                {
                    // read the account number
                    int accNum = (( ReadMessage ) messageObj).accountNum;                  
                    // get the account ballance
                    int accBal = accManager.read( accNum, workerTransaction );
                    
                    // accManager.read may throw errors later if account 
                    // does not exist
                    
                    // create response message
                    ReadMessage response = new ReadMessage( accNum, accBal );
                    // respond to client with ballance
                    outObjStream.writeObject( response );
                }
                // if message is of write type
                else if( messageObj instanceof WriteMessage )
                {
                    // read account number from message
                    int accNum = ((ReadMessage) messageObj).accountNum;
                    // read balance
                    Object tempBal = ((ReadMessage) messageObj).bal.get();                        
                    int bal = (int) tempBal;
                    // write to the account
                    accManager.write( accNum, workerTransaction, bal );
                    // ack be sending back original message
                    outObjStream.writeObject( messageObj );
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
