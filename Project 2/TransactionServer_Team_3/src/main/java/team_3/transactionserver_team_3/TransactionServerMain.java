/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 */
package team_3.transactionserver_team_3;

// imports
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;

/**
 * @brief Initializes server by reading properties, according to which the 
 * TransactionManager, and AcountManager are created/initialized and then 
 * runs the multi-threaded server loop.
 * 
 * @author Matthew Flanders
 */
public class TransactionServerMain
{
    // initialize objects
    static AccountManager accountManager;
    static TransactionManager transactionManager;
    static ServerSocket serverSocket;
    static LockManager lockManager = new LockManager();
    static int numAccounts;
    static int portNum;
    
    /**
     * @brief uses command line arguments to initialize objects
     * and begin to run the specified number of transactions
     * 
     * needs command line arguments: <port> <number of accounts>
     * 
     */
    public static void main(String args[])
    {
        
        // check if argument length is valid
        if( args.length != 2 )
        {
            System.out.println("Incorrect parameters.\n" +
                    " Please pass 2 parameters: < (int) port>"+
                    " < (int) number of accounts>");
            System.exit(1);
        }
        checkArgs( args[0], args[1], numAccounts, portNum );
        try
        {
            portNum = Integer.parseInt( args[1] );
        }
        catch( NumberFormatException nfException )
        {
            System.out.println("Error in parsing port number to integer.");
            System.exit(1);
        }
        
        // initialize account manager with number of accounts parameter
        accountManager = new AccountManager( numAccounts , 10 );
        transactionManager = new TransactionManager( accountManager );
        
        // open server
        try
        {
            serverSocket = new ServerSocket( portNum, -1, 
                                       InetAddress.getByName("localhost"));
        }
        catch( IOException ioE )
        {
            System.out.println("Error occured opening socket.");
            System.exit(1);
        }       
        // begin server loop
        while( true )
        {
            // when recieving a new connection/socket call openTransaction
            // method in TransactionManager 
            try
            {
                transactionManager.openTransaction( serverSocket.accept());
            }
            catch( IOException ioE )
            {
                System.out.println("Error occured while waiting for next"+
                        " transaction");
                System.exit(1);
            }
        }      
    }
    
    private static void checkArgs( String arg0, String arg1, int numAcc, 
            int portNum )
    {
        
        // read the port number
        try
        {
            portNum = Integer.parseInt( arg0 );
            if( portNum < 0 || portNum > 65535 )
            {
                System.out.println("Port must be between 0 and 65535.");
                System.exit(1);
            }            
        }
        catch( NumberFormatException nfException )
        {
            System.out.println("Error in parsing port number to integer.");
            System.exit(1);
        }
        
        // read number of accounts
        try
        {
            numAcc = Integer.parseInt( arg1 );
            // consider limiting number of accounts
            if( 0 > numAcc )
            {
                System.out.println("Number of accounts must be positive.");
                System.exit(1);
            }            
        }
        catch( NumberFormatException nfException )
        {
            System.out.println("Error in parsing number of accounts argument"+
                    " to integer.");
            System.exit(1);
        }
    }
}
