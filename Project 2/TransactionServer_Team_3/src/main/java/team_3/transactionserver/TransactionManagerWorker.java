/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 *
 */
package team_3.transactionserver;

// imports
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

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
    private TransactionManager transManager;
    private LockManager lockManager;
    private ObjectInputStream inObjStream;
    private ObjectOutputStream outObjStream;
    private final String locStr = "TransactionManagerWorker";
    private final String openTransStr = "OPEN TRANSACTION";
    private final String closeTransStr = "CLOSE TRANSACTION";
    private final String errStr = "";

    /**
     * @brief initialization of worker
     * @param lockManager
     * @param transManager
     * @param socket socket connection to the client
     * @param accManager reference to account manager
     */
    public TransactionManagerWorker( Socket socket,
                                     TransactionManager transManager,
                                     AccountManager accManager,
                                     LockManager lockManager)
    {
        // initialize variables
        this.transManager = transManager;
        this.accManager = accManager;
        this.lockManager = lockManager;

        // create object streams for communication with message objects
        try
        {
            this.outObjStream = new ObjectOutputStream(
                                            socket.getOutputStream() );
            this.inObjStream = new ObjectInputStream( socket.getInputStream() );
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
    @Override
    public void run()
    {
        // try/catch to read network messages
        try
        {
            // initialize loop flag to true
            boolean isOpened = false;
            boolean isClosed = false;

            // begin loop
            while( !isClosed )
            {
                // read object from client and get the message type
                Object messageObj = inObjStream.readObject();

                // if message is of open type, disregard message
                if( messageObj instanceof OpenTransMessage )
                {
                    // create transaction object here actually
                    this.workerTransaction = transManager.newTransaction();
                    isOpened = true;

                    // log
                    this.workerTransaction.log(locStr, openTransStr);

                    // respond to client with transaction ID
                    Message responseMessage =
                            new ResponseMessage(MessageType
                                    .OPEN_TRANSACTION_MESSAGE, 
                                    this.workerTransaction.id);
                    outObjStream.writeObject( responseMessage );
                }

                // if the message was a close message
                else if( messageObj instanceof CloseTransMessage && isOpened )
                {
                    // log
                    this.workerTransaction.log(locStr, closeTransStr);

                    // respond to client with confirm close message
                    Message responseMessage =
                            new ResponseMessage(MessageType
                                    .CLOSE_TRANSACTION_MESSAGE);
                    outObjStream.writeObject(  responseMessage );

                    // print log for transaction
                    this.workerTransaction.printLog();
                    
                    // release locks
                    this.lockManager.unLock(this.workerTransaction);
                    
                    // delete transaction object
                    this.transManager.removeTransaction(workerTransaction);

                    // end loop, set loop flag to false
                    isClosed = true;
                }

                // if message is of read type
                else if( messageObj instanceof ReadMessage && isOpened )
                {
                    // read the account number
                    int accNum = (( ReadMessage ) messageObj).accountNum;
                    Message responseMessage;

                    try
                    {
                        // get the account ballance
                        int accBal = accManager.read( accNum, workerTransaction );

                        // create response message with balace
                        responseMessage =
                                new ResponseMessage(MessageType
                                        .READ_MESSAGE, accBal);
                    }
                    // account does not exist, create error response message
                    // intead
                    catch (NonExistantAccountException neaE)
                    {
                        responseMessage =
                                new ResponseMessage(MessageType.ERROR_MESSAGE,
                                "That account does not exist!");
                    }

                    // respond to client with ballance
                    outObjStream.writeObject( responseMessage );
                }

                // if message is of write type
                else if( messageObj instanceof WriteMessage && isOpened )
                {
                    // read account number from message
                    int accNum = ((WriteMessage) messageObj).accountNum;
                    Message responseMessage;

                    // read value
                    Object tempVal = ((WriteMessage) messageObj).value;
                    int value = (int) tempVal;

                    try
                    {
                        // write to the account
                        accManager.write( accNum, workerTransaction, value );

                        // create response message with balace
                        responseMessage =
                                new ResponseMessage(MessageType
                                        .READ_MESSAGE, 0);
                    }
                    // account does not exist, create error response message
                    // intead
                    catch (NonExistantAccountException neaE)
                    {
                        responseMessage =
                                new ResponseMessage(MessageType.ERROR_MESSAGE,
                                "That account does not exist!");
                    }

                    // respond to client with ballance
                    outObjStream.writeObject( responseMessage );
                }

                // else, the transaction was never opened
                // send error message
                else
                {
                    Message responseMessage =
                            new ResponseMessage(MessageType.ERROR_MESSAGE,
                            "Transaction not open!");
                    outObjStream.writeObject( responseMessage );
                }
            }
        }
        catch( IOException ioE )
        {
            System.out.println("Error in recieving message "+ioE);
            // todo: close the transaction at this point, if open
        }
        catch (ClassNotFoundException clE)
        {
            System.out.println("Error in recieving incoming message. " + clE);
            // todo: close the transaction at this point, if open
        }
    }
}
