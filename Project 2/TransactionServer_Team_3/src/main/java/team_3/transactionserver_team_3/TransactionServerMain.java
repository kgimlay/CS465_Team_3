/**
 * @authors Kevin Imlay, Randy Duerinck, Yasmin Vega, Matthew Flanders
 * @date 3/7/21
 */
package team_3.transactionserver_team_3;

// imports
import java.net.Socket;

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
<<<<<<< Updated upstream
    int accountCount;
    AccountManager accountManager = new AccountManager();
    TransactionManager transactionManager = new TransactionManager();
    Socket Socket;
    static LockManager lockManager = new LockManager();
=======
        AccountManager accountManager;
        TransactionManager transactionManager;
        Socket Socket;
    
>>>>>>> Stashed changes
    /**
     * @brief uses command line arguments to initialize objects
     * and begin to run the specified number of transactions
     * 
     * needs command line arguments: <port> <number of accounts>
     * 
     */
    public static void main(String args[])
<<<<<<< Updated upstream
    {
        
        // check if arguments are valid
        
            // if not valid args stop and return error message and exit
        
        // read args passed
=======
    {        
        // check if argument length is valid
        if( args.length != 2 )
        {
            System.out.println("Incorrect parameters.\n" +
                    " Please pass 2 parameters: < (int) port>"+
                    " < (int) number of accounts>");
            System.exit(1);
        }
        checkArgs( args[0], args[1] );
>>>>>>> Stashed changes
            
        // initialize account manager with number or accounts parameter
//        accountManager = new AccountManager( numberOfAccounts );
        
        // open server
                
        // begin server loop
        
            // when recieving a new connection/socket call openTransaction
            // method in TransactionManager        
    }
    
    private static void checkArgs( String arg0, String arg1 )
    {
        // initialize variables
        int portNum;
        int numberOfAccounts;
        
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
            numberOfAccounts = Integer.parseInt( arg1 );
            // consider limiting number of accounts
            if( 0 < numberOfAccounts )
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
