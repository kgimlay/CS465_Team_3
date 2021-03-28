/**
  * @authors Kevin Imlay
  * @date 3/14/21, 3/24/21
  */

package team_3.transactionserver;

import java.net.ServerSocket;
import java.util.Random;


/** Transaction Client is the main entrance to the client application for the
 * system. The only transactions possible are transfers between two accounts.
 *
 * Configurable number of transactions to run. Randomly selects values for how
 * much to transfer.
 */
public class TransactionClient {
    
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
            System.exit(1);
        }
        
        // start the client
        runClient(clientConfig);
    }
    
    
    /**
     * 
     * @param config 
     */
    private static void runClient(Config config)
    {
        // random generator for picking account numbers and amount to transfer
        // between accounts
        Random random = new Random();
        
        // create client workers in threads to run the transactions
        for (int counter = 0; counter < config.numTransactions; counter++)
        {
            // generate random values for transactions
            int withdrawAccountNum = random.nextInt(
                    config.minTransfer + config.maxTransfer + 1) 
                    - config.minTransfer;
            int depositAccountNum = random.nextInt(config.numAccounts + 1); // can pick same account to transfer out of and into
            int ammountToTransfer = random.nextInt(config.numAccounts + 1); // can pick same account to transfer out of and into
            
            // start workers to carry out transactions
            ClientWorker worker = new ClientWorker(withdrawAccountNum, 
                    depositAccountNum, 
                    ammountToTransfer, 
                    config.serverIpStr, 
                    config.serverPort);
            new Thread( worker ).start();
        }
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
     *//* Command Line Arguments Format: <number of transactions>
     *      <number of accounts> <ip address of server> <port of server>
     *      <minimum amount to transfer> <maximum amount to transfer>
     */
    private static Config getConfigurationFromArgs(String arguments[])
    {
        // if correct number of arguments
        // check to avoid out of bounds exception
        if (arguments.length == 6)
        {
            try
            {
                // number of transactions to run
                int numTrans = parseNumTransArg(arguments[0]);

                // number of accounts
                int numAccounts = parseNumAccountsArg(arguments[1]);

                // string of IP
                String ipString = arguments[2];

                // port
                int port = parsePortArg(arguments[3]);

                // minimum amount to transfer
                int minToTransfer = parseMinTransferArg(arguments[4]);

                // maximum amount to transfer
                int maxToTransfer = parseMaxTransferArg(arguments[5]);
                
                // check that all mins are not more than all maxes
                // bounds of amount to transfer (only min-max for now)
                if (minToTransfer > maxToTransfer)
                {
                    throw new MalformedCommandLineArgumentException("Maximum  "
                    + "amount to transfer is less than the minimum amount"
                    + " to transfer! Min: " + minToTransfer + " Max: " 
                    + maxToTransfer);
                }

                // create config and pass back
                return (new Config(numTrans, numAccounts, ipString, 
                        port, minToTransfer, maxToTransfer));
            }
            catch (MalformedCommandLineArgumentException mclaE)
            {
                System.out.println(mclaE.getMessage());
            }
        }
        else
        {
            // print usage and reutrn null if config incorrect
            System.out.println("Usage <number of transactions> <number of "
                    + "accounts> <ip address of server> <port of server> "
                    + "<minimum amount to transfer> <maximum amount to "
                    + "transfer>");
        }
        return null;
    }
    
    
    /**
     * 
     */
    private static int parseNumTransArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            int numTrans = Integer.parseInt(arg);
            if (numTrans < 0)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "have less than zero transactions! Value: " + arg);
            }
            return numTrans;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad number of "
                + "transactions! Value: " + arg);
        }
    }
    
    /**
     * 
     */
    private static int parseNumAccountsArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            int numAccounts = Integer.parseInt(arg);
            if (numAccounts < 0)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "have less than zero accounts! Value: " + arg);
            }
            return numAccounts;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad number of "
                + "accounts! Value: " + arg);
        }
    }
    
    /**
     * 
     */
    private static int parsePortArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            int portNum = Integer.parseInt(arg);
            if (portNum < 0 || portNum > 65525)
            {
                throw new MalformedCommandLineArgumentException("Port number "
                    + "not in range! Value: " + arg);
            }
            return portNum;
        }
        catch (NumberFormatException nfE) 
        {
            throw new MalformedCommandLineArgumentException("Bad port number!"
                + " Value: " + arg);
        }
    }
    
    /**
     * 
     */
    private static int parseMinTransferArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            int minTransfer = Integer.parseInt(arg);
            if (minTransfer < 0)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "transfer less than zero! Value: " + arg);
            }
            return minTransfer;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad minimum "
                + "amount to transfer! Value: " + arg);
        }
    }
    
    /**
     * 
     */
    private static int parseMaxTransferArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            int maxTransfer = Integer.parseInt(arg);
            if (maxTransfer < 0 )
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "transfer less than zero! Value: " + arg);
            }
            return maxTransfer;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad maximum "
                + "amount to transfer! Value: " + arg);
        }
    }
    
    
    /** Configuration class to use with passing the configurations from the
     * getConfigurationFromArgs method.
     */
    private static class Config {
        
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
