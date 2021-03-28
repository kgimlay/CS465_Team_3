/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 */
package team_3.transactionserver;

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
public class TransactionServer
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
        // check args and assign if they pass
        checkArgs( args, portNum, numAccounts );

        // value 10 in currently hard coded for testing, may need to
        // change in the furure to allow starting account ballances to
        // be passed in
        accountManager = new AccountManager( numAccounts , 10 );
        transactionManager = new TransactionManager( accountManager );

        // open server
        try
        {
            serverSocket = new ServerSocket( portNum, -1,
                                       InetAddress.getByName("localhost"));
            System.out.println("Server opened successfuly.");
        }
        catch( IOException ioE )
        {
            System.out.println("Error occured opening server socket." + ioE );
            System.exit(1);
        }
        // begin server loop
        while( true )
        {
            // when recieving a new connection/socket call openTransaction
            // method in TransactionManager
            try
            {
                transactionManager.newWorkerThread( serverSocket.accept());
            }
            catch( IOException ioE )
            {
                System.out.println("Error occured while waiting for next"+
                        " transaction" + ioE);
                System.exit(1);
            }
        }
    }

    private static void checkArgs( String arg[], int portNum,
                                   int numAcc)
    {
        // check if argument length is valid
        if( arg.length != 2 )
        {
            System.out.println("Incorrect number of parameters.\n" +
                    " Please pass 2 parameters: < (int) port>"+
                    " < (int) number of accounts>");
            System.exit(1);
        }
        
        // read the port number
        try
        {
            portNum = Integer.parseInt( arg[0] );
            if( portNum < 0 || portNum > 65535 )
            {
                System.out.println("Port must be between 0 and 65535.");
                System.exit(1);
            }
        }
        catch( NumberFormatException nfException )
        {
            System.out.println("Error in parsing port number to integer."+
                                nfException);
            System.exit(1);
        }

        // read number of accounts
        try
        {
            numAcc = Integer.parseInt( arg[1] );
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
                    " to integer." + nfException );
            System.exit(1);
        }
    }
}
