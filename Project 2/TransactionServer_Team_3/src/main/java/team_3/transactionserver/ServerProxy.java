/**
  * @authors Kevin Imlay
  * @date 3/14/21, 3/24/21
  */

package team_3.transactionserver;

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
public class ServerProxy {
    
    private Socket connSocket;
    private ObjectInputStream inObjStream;
    private ObjectOutputStream outObjStream;
    
    
    /** Create a new transaction (server proxy) to run. 
     * 
     * @param socket - Socket object for communication with the transaction
     * server.
     */
    public ServerProxy(Socket socket)
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
     * @throws team_3.transactionserver.UnexpectedResponseMessageException
     * @throws team_3.transactionserver.MalformedMessageException
     */
    public int openTransaction() throws NonMessageObjectRecievedException,
             IOException, UnexpectedResponseMessageException,
             MalformedMessageException
    {
        // send an open transaction message to the server and wait for the
        // response
        Message response = messageAndWait( new OpenTransMessage() );
        
        // check to make sure the response message is the correct message type
        if (!(response instanceof ResponseMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected an open transaction message "
                + "response.\n\n" + response);
        }
        
        // return the transaction ID that is returned.
        try
        {
            int transID = ((ResponseMessage)response).returnIntVal;
            return transID;
        }
        catch (NoSuchElementException nseE)
        {
            // the transaction ID was not returned in the message
            throw new MalformedMessageException("The transaction ID "
                + "was not found!\n\n" + nseE);
        }
    }
    
    
    /** *  Close the transaction with the transaction server.Sends a close 
     * transaction message to the transaction server.
     *
     * @throws team_3.transactionserver.NonMessageObjectRecievedException 
     * @throws java.io.IOException 
     * @throws team_3.transactionserver.UnexpectedResponseMessageException 
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
        if (!(response instanceof ResponseMessage))
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
     * @throws team_3.transactionserver.NonMessageObjectRecievedException 
     * @throws java.io.IOException 
     * @throws team_3.transactionserver.UnexpectedResponseMessageException 
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
        if (!(response instanceof ResponseMessage))
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
     * @throws team_3.transactionserver.NonMessageObjectRecievedException
     * @throws java.io.IOException
     * @throws team_3.transactionserver.UnexpectedResponseMessageException
     * @throws team_3.transactionserver.MalformedMessageException
     * 
     * @throws IOException. See above todo.
     */
    public int read(int accountID) throws NonMessageObjectRecievedException,
             IOException, UnexpectedResponseMessageException,
             MalformedMessageException
    {
        // send read message and wait for the response that contains the value
        // read
        Message response = messageAndWait( 
                new ReadMessage(accountID) );
        
        // check to make sure the response message is the right type
        if (!(response instanceof ResponseMessage))
        {
            // not correct message expected, throw exception
            throw new UnexpectedResponseMessageException("An unexpected message"
                + " was received! Expected a read transaction message response"
                + "\n\n" + response);
        }
        
        // return the value of the balance
        try
        {
            int balanceVal = ((ResponseMessage)response).returnIntVal;
            return balanceVal;
        }
        catch (NoSuchElementException nseE)
        {
            // the balance was not returned in the message
            throw new MalformedMessageException("The balance value "
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
            throws NonMessageObjectRecievedException, IOException, 
            UnexpectedResponseMessageException
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
            
            // if an error message, throw an exception
            if (recievedObj instanceof ResponseMessage
                    && ((ResponseMessage)recievedObj).msgType 
                        == MessageType.ERROR_MESSAGE)
            {
                throw new UnexpectedResponseMessageException("An error message"
                    + " was received!\n\n" 
                    + ((ResponseMessage)recievedObj).message);
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