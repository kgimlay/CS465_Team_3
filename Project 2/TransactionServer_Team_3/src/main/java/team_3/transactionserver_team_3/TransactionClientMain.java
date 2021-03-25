/**
  * @authors Kevin Imlay
  * @date 3/14/21, 3/24/21
  */

package team_3.transactionserver_team_3;

import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;


/** Transaction Client is the main entrance to the client application for the
 * system. The only transactions possible are transfers between two accounts.
 *
 * Configurable number of transactions to run. Randomly selects values for how
 * much to transfer.
 */
public class TransactionClientMain {
    
    private static ServerSocket serverSocket;
    
    
    /** Main entrance to client application.
     * 
     * Takes in configuration arguments on the command line and starts, running
     * the number of transfers specified on randomly picked pairs of accounts.
     * The amounts in the transfers can be bounded with optional arguments as
     * well.
     * 
     * For command line arguments format, see getConfigurationFromArgs
     * 
     * @param args - String array of arguments from command line.
     */
    public static void main(String args[])
    {
        // get configurations for command line
        Config clientConfig = getConfigurationFromArgs(args);
        
        // exit if the configurations are not correct
        if (clientConfig == null)
        {
            System.out.println("Configuration given is invalid!");
            System.exit(1);
        }
        
        // start client, generate randoms and list of transactions/instructions
        // to perform
        try
        {
            serverSocket = new ServerSocket();
        }
        catch (IOException ioE)
        {
            System.out.println("An error occured trying to open the server "
                    + "socket!\n\n" + ioE);
            System.exit(1);
        }
        
        // perform transactions
        // to be threaded? Looped? I'm not sure yet!
        
    }
    
    
    /** Gathers the command line arguments (passed in) into their appropriate
     * configurations for the system.
     * 
     * @param arguments - String array of the arguments to test. Let this be
     * the args from the main call.
     * 
     * @return Configuration object or Null if the configurations were not
     * correct.
     * 
     * @todo Command line arguments format.
     */
    private static Config getConfigurationFromArgs(String arguments[])
    {
        // reutrn null if config incorrect
        return null;
    }
    
    
    /** Configuration class to use with passing the configurations from the
     * getConfigurationFromArgs method.
     */
    private class Config {
        
        // number of transactions to perform
        int numTransactions;
        
        // number of accounts there are
        // this is needed to set a max limit for random account number
        // generation
        int numAccounts;
        
        // transaction server IP and port
        String serverIpStr;
        int serverPort;
        
        // bounds on how much money to transfer
        int maxTransfer;
        int minTransfer;
        
        
        /** Construct a Config object with the configurations needed for the
         * client.
         * 
         * @param numTrans - The number of transactions to perform.
         * @param numAccounts - Upper bound to keep from trying to access
         *                      accounts that don't exist. (account nums will
         *                      always start from 0, incrementing by 1)
         * @param ipString - String representation of the IP to find the server.
         * @param port - Integer port number of the server.
         * @param minToTransfer - Lower bound of how much money to transfer.
         * @param maxToTransfer - Upper bound of how much money to transfer.
         */
        Config(int numTrans, int numAccounts, String ipString, int port, 
                int minToTransfer, int maxToTransfer)
        {
            this.numTransactions = numTrans;
            this.numAccounts = numAccounts;
            this.serverIpStr = ipString;
            this.serverPort = port;
            this.minTransfer = minToTransfer;
            this.maxTransfer = maxToTransfer;
        }
    }
}
