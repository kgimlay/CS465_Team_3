/**
 * 
 */
package team_3.transactionserver;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author kevinimlay
 */
public class ConnectionAcceptor implements Runnable
{
    private ServerSocket serverSocket;
    private TransactionManager transactionManager;
    
    /**
     * 
     * @param transManager
     * @param serverSocket 
     */
    public ConnectionAcceptor(TransactionManager transManager, 
            ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
        this.transactionManager = transManager;
    }
    
    /**
     * 
     */
    @Override
    public void run()
    {
        // begin server loop
        while( true )
        {
            // when recieving a new connection/socket call openTransaction
            // method in TransactionManager
            try
            {
                transactionManager.newWorkerThread( serverSocket.accept() );
            }
            catch( IOException ioE )
            {
                System.out.println("Error occured while waiting for next"+
                        " transaction" + ioE);
                System.exit(1);
            }
        }
    }
}
