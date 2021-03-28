/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_3.transactionserver;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Randy Duerinck
 */
public class ClientWorker implements Runnable
{
    int withdrawAccountId;
    int depositAccountId;
    int transferAmount;
    String serverIp;
    int serverPort;
    Socket connection;
    ServerProxy proxy;
    
    public ClientWorker(int withAccId, int depAccId, int transAmount,
            String ip, int port)
    {
     this.withdrawAccountId = withAccId;
     this.depositAccountId = depAccId;
     this.transferAmount = transAmount;
     this.serverIp = ip;
     this.serverPort = port;
    }
            
    @Override
    public void run()
    {
        // open connection with server
        try
        {
            connection = new Socket(this.serverIp, this.serverPort);
            this.proxy = new ServerProxy(connection);
        }
        catch (UnknownHostException uhE)
        {
            System.out.println("Cannot connect to server!\n\n" + uhE);
        }
        catch (IOException ioE)
        {
            System.out.println("A problem occured with the server connection!"
                    + "\n\n" + ioE);
        }
        
        // run the transaction by calling on the proxy
        try
        {
            // open a transaction
            int transactionId = this.proxy.openTransaction();
            
            // withdraw from fist account
            int withdrawAccBal = this.proxy.read(this.withdrawAccountId);
            this.proxy.write(this.withdrawAccountId, 
                    withdrawAccBal - this.transferAmount);
            
            // deposit into second account
            int depositAccBal = this.proxy.read(this.depositAccountId);
            this.proxy.write(this.depositAccountId, 
                    depositAccBal + this.transferAmount);
            
            // close the transaction
            this.proxy.closeTransaction();
        }
        
        // A message object was not returned, uh oh! this should never happen!
        catch (NonMessageObjectRecievedException nmorE)
        {
            System.out.println(nmorE);
        }
        
        // likely an issue with the connection to the server
        catch (IOException ioE)
        {
            System.out.println(ioE);
        }
        
        // the message received was not of the correct type or was an error
        // message
        catch (UnexpectedResponseMessageException urmE)
        {
            System.out.println(urmE);
        }
        
        // the message returned was not formatted correctly!
        catch (MalformedMessageException mmE)
        {
            System.out.println(mmE);
        }
    }
    
    
    
    
}
