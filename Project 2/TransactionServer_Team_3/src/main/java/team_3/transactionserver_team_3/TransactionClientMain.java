/**
  * @authors Kevin Imlay
  * @date 3/14/21
  */

package team_3.transactionserver_team_3;

import java.net.ServerSocket;
import java.net.InetAddress;


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
     */
    public static void main(String args[])
    {
        // get configurations for command line
        Config clientConfig = getConfigurationFromArgs(args);
        
        // exit if the configurations are not correct
        if (clientConfig == null)
        {
            System.exit(1);
        }
        
        // start client, generate randoms and list of transactions/instructions
        // to perform
        
        
        // loop and perform transactions
        
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
        InetAddress ipPort;
        
        // the maximum number to transfer (optional, defaults to $100)
        int maxTransfer = 100;
        
        // the minimum number to transfer (optional, defaults to $1)
        int minTransfer = 1;
        
        
        /** Construct a Config object with the configurations needed for the
         * client.
         * 
         * @param numTrans
         * @param numAccounts
         * @param ipString
         * @param port
         * @param minToTransfer
         * @param maxToTransfer
         */
        Config(int numTrans, int numAccounts, String ipString, int port, 
                int minToTransfer, int maxToTransfer)
        {
            
        }
    }
}
