/**
  * @authors Kevin Imlay
  * @date 3/14/21, 3/24/21
  */

package team_3.transactionserver_team_3;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;


/** Transaction client server proxy as a layer between the client and the
 * transaction server.
 * 
 * Takes in commands from the client and sends messages to the transaction
 * server in accordance to those commands.
 * 
 * @todo Need to figure out how to handle trying to access accounts that don't
 * exist.
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
    
    
    /** *  Opens a transaction with the transaction server.Sends an open 
     * transaction message to the transaction server.
     * 
     * @return int - The transaction ID.
     * @throws NonMessageObjectRecievedException - passes along to client to
     *          handle.
     * @throws java.io.IOException
     * @throws team_3.transactionserver_team_3.UnexpectedResponseMessageException
     * @throws team_3.transactionserver_team_3.MalformedResponseMessageException
     */
    public int openTransaction() throws NonMessageObjectRecievedException,
             IOException, UnexpectedResponseMessageException,
             MalformedResponseMessageException
    {
        // send an open transaction message to the server and wait for the
        // response
        Message response = messageAndWait( new OpenTransMessage(null) );
        
        // check to make sure the response message is the correct message type
        if (!(response instanceof OpenTransMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected an open transaction message "
                + "response.\n\n" + response);
        }
        
        // return the transaction ID that is returned.
        try
        {
            Object transID = ((OpenTransMessage)response).transID.get();
            return (int)transID;
        }
        catch (NoSuchElementException nseE)
        {
            // the transaction ID was not returned in the message
            throw new MalformedResponseMessageException("The transaction ID "
                + "was not found!\n\n" + nseE);
        }
    }
    
    
    /** *  Close the transaction with the transaction server.Sends a close 
     * transaction message to the transaction server.
     *
     * @throws team_3.transactionserver_team_3.NonMessageObjectRecievedException 
     * @throws java.io.IOException 
     * @throws team_3.transactionserver_team_3.UnexpectedResponseMessageException 
     * @throws IOException.
     * @throws NonMessageObjectRecievedException.
     */
    public void closeTransaction() throws NonMessageObjectRecievedException,
             IOException, UnexpectedResponseMessageException
    {
        // send close transaction message and wait for the response that the
        // transaction is shut down
        Message response = messageAndWait( new CloseTransMessage() );
        
        // check to make sure the response message is saying its good to close
        // the connection
        if (!(response instanceof CloseTransMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected a close transaction message response"
                + "\n\n" + response);
        }
        
        // finally close the connection
        this.connSocket.close();
    }
    
    
    /** *  Write a new balance to an account in the transaction server.Sends a 
     * write message to the transaction server with the specified account ID and
     * new balance to write.
     *
     * @param accountID - ID of the account to write to.
     * @param newBalance  - Dollar amount to write as the new balance.
     * @throws team_3.transactionserver_team_3.NonMessageObjectRecievedException 
     * @throws java.io.IOException 
     * @throws team_3.transactionserver_team_3.UnexpectedResponseMessageException 
     * @throws IOException. See above todo.
     */
    public void write(int accountID, int newBalance) 
            throws NonMessageObjectRecievedException, IOException,
            UnexpectedResponseMessageException
    {
        // send write message and wait for the response that the write has
        // completed
        Message response = messageAndWait( 
                new WriteMessage(accountID, newBalance) );
        
        // check to make sure the response message is saying the write has
        // completed
        if (!(response instanceof WriteMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected a write transaction message response"
                + "\n\n" + response);
        }
    }
    
    
    /** *  Read the balance of an account in the transaction server.Sends a 
     * write message to the transaction server with the account id to read the 
     * balance from.Waits for a response read message and returns the account 
     * balance.
     *
     * @param accountID - ID of the account to read from.
     * @return int - The balance of the account.
     * @throws team_3.transactionserver_team_3.NonMessageObjectRecievedException
     * @throws java.io.IOException
     * @throws team_3.transactionserver_team_3.UnexpectedResponseMessageException
     * @throws team_3.transactionserver_team_3.MalformedResponseMessageException
     * 
     * @throws IOException. See above todo.
     */
    public int read(int accountID) throws NonMessageObjectRecievedException,
             IOException, UnexpectedResponseMessageException,
             MalformedResponseMessageException
    {
        // send read message and wait for the response that contains the value
        // read
        Message response = messageAndWait( 
                new ReadMessage(accountID, null) );
        
        // check to make sure the response message is the right type
        if (!(response instanceof ReadMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected a read transaction message response"
                + "\n\n" + response);
        }
        
        // return the value of the balance
        try
        {
            Object balanceVal = ((OpenTransMessage)response).transID.get();
            return (int)balanceVal;
        }
        catch (NoSuchElementException nseE)
        {
            // the balance was not returned in the message
            throw new MalformedResponseMessageException("The balance value "
                + "was not found!\n\n" + nseE);
        }
    }
    
    /** Sends a message to the server and waits for a response.
     * 
     * @param message - Message object to send.
     * @throws NonMessageObjectRecievedException - If a non-message object is
     *          received.
     * @throws IOException.
     */
    private Message messageAndWait(Message message) 
            throws NonMessageObjectRecievedException, IOException
    {
        try
        {
            // send and revieve (blocking)
            outObjStream.writeObject( message );
            Object recievedObj = inObjStream.readObject();

            // throw exception if not Message object
            if (!(recievedObj instanceof Message))
            {
                // this is really just to pass on the handling to the catch
                // statement below
                throw new ClassNotFoundException();
            }

            // return the message if there was no issue
            return (Message)recievedObj;
        }
        catch (ClassNotFoundException clE)
        {
            throw new NonMessageObjectRecievedException("A non-message "
                    + "object was recieved!\n\n" + clE);
        }
    }
}