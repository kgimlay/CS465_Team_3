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
            System.out.println("Worker attempting to open streams.");
            this.outObjStream = new ObjectOutputStream( 
                                            socket.getOutputStream() );
            System.out.println("Output stream successful.");
            this.inObjStream = new ObjectInputStream( socket.getInputStream() );
            System.out.println("Input stream successful.");
        }
        catch ( IOException ioE )
        {
            System.out.println("An IO Exception has occured!\n" + ioE);
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
            System.out.println("Worker reading network messages.");
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
                    System.out.println("Worker recieves close message.");
                    // respond to client with confirm close message
                                                                        // TO-DO
                                                                        // need to create close confirm message 
                                                                        // or not if replying back with same message is enough
                    // ack be sending back original message
                    outObjStream.writeObject( messageObj );
                    System.out.println("Worker replies back with close message");
                    
                    // end loop, set loop flag to false
                    noCloseMessage = false;
                }
                
                // if not a close then some other message
                // if message is of open type, disregard message
                else if( messageObj instanceof OpenTransMessage )
                {
                    System.out.println("Worker recieves open message.");
                    outObjStream.writeObject( 
                            new OpenTransMessage( workerTransaction.id ));
                    System.out.println("Worker responds back to open"+
                            " transaction.");
                }
                
                // if message is of read type
                else if( messageObj instanceof ReadMessage )
                {
                    System.out.println("Worker recieves read message.");
                    // read the account number
                    int accNum = (( ReadMessage ) messageObj).accountNum;                  
                    
                    // get the account ballance
                    int accBal = accManager.read( accNum, workerTransaction );
                    System.out.println("Worker calls account manager for"+
                            " account ballance.");
                    // accManager.read may throw errors later if account 
                    // does not exist
                    
                    // create response message
                    ReadMessage response = new ReadMessage( accNum, accBal );
                   
                    // respond to client with ballance
                    outObjStream.writeObject( response );
                    System.out.println("Worker responds to read message.");
                }
                
                // if message is of write type
                else if( messageObj instanceof WriteMessage )
                {
                    System.out.println("worker recieves write message");
                    // read account number from message
                    int accNum = ((WriteMessage) messageObj).accountNum;
                    
                    // read value
                    Object tempVal = ((WriteMessage) messageObj).value;                        
                    int value = (int) tempVal;
                    
                    // write to the account
                    accManager.write( accNum, workerTransaction, value );
                    System.out.println("Worker calls to account manager "+
                            "to write a value.");
                    
                    // ack be sending back original message
                    outObjStream.writeObject( messageObj );
                    System.out.println("Worker responds to write message.");
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
