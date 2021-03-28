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
        checkArgs( args );

        // value 10 in currently hard coded for testing, may need to
        // change in the furure to allow starting account ballances to
        // be passed in
        accountManager = new AccountManager( numAccounts , 10 );
        transactionManager = new TransactionManager( accountManager );

        // open server
        try
        {
            System.out.println(portNum);
            serverSocket = new ServerSocket( portNum, -1,
                                       InetAddress.getByName("localhost"));
            System.out.println("Server opened successfuly. " + serverSocket);
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
                System.out.println("Here");
            }
            catch( IOException ioE )
            {
                System.out.println("Error occured while waiting for next"+
                        " transaction" + ioE);
                System.exit(1);
            }
        }
    }

    private static void checkArgs( String arg[] )
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
            String[] split_arg0 = arg[0].split("=");
            if( split_arg0[0] != "port" )
            {
                System.out.println("Please pass first argument as <port=#>.");
                System.exit(1);
            }
            portNum = Integer.parseInt( split_arg0[1] );
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
            String[] split_arg1 = arg[1].split("=");
            if( split_arg1[0] != "numAccounts" )
            {
                System.out.println("Please pass second argument as"+
                        " <numAccounts=#>.");
                System.exit(1);
            }
            
            numAccounts = Integer.parseInt( split_arg1[1] );
            // consider limiting number of accounts
            if( 0 > numAccounts )
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
