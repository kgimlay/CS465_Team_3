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
 * @brief client worker is a single thread doing a transaction
 * @author Randy Duerinck
 * @author Kevin Imlay
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
        Boolean isConnected = false;
        
        // open connection with server
        try
        {
            connection = new Socket(this.serverIp, this.serverPort);
            this.proxy = new ServerProxy(connection);
            isConnected = true;
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
        
        if (isConnected)
        {
            // run the transaction by calling on the proxy
            try
            {
                // open a transaction
                int transactionId = this.proxy.openTransaction();
                System.out.println( transactionId + " has opened");

                // withdraw from fist account
                int withdrawAccBal = this.proxy.read(this.withdrawAccountId);
                this.proxy.write(this.withdrawAccountId, 
                        withdrawAccBal - this.transferAmount);
                System.out.println("Transaction number: "+transactionId+
                        " has withdrawn $"+this.transferAmount+" from account "+
                        this.withdrawAccountId+", balance is now $"+
                        (withdrawAccBal - this.transferAmount));
                
                // deposit into second account
                int depositAccBal = this.proxy.read(this.depositAccountId);
                this.proxy.write(this.depositAccountId, 
                        depositAccBal + this.transferAmount);
                System.out.println("Transaction number: "+transactionId+
                        " has deposited $"+this.transferAmount+" into account "+
                        this.depositAccountId+", balance is now $"+
                        (depositAccBal + this.transferAmount));

                // close the transaction
                this.proxy.closeTransaction();
                System.out.println( transactionId + " has closed.");
                
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
        else
        {
            // do something about it
        }
    }
    
    
    
    
}
