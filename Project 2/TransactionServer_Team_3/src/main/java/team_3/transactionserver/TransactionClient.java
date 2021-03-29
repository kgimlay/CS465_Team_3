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
    
    
    /** Runs the client - called after argument checking is passed.
     * 
     * Spawns client workers in threads to carry out the operations of the 
     * transactions. Gives each worker the information such as the accounts to
     * transfer between and how much to transfer, and lets the worker go to
     * complete its task.
     * 
     * @param config - Config object of the command line arguments passed in.
     */
    private static void runClient(Config config)
    {
        // random generator for picking account numbers and amount to transfer
        // between accounts
        Random random = new Random();
        System.out.println("Running client with:");
        System.out.println("Transactions: "+config.numTransactions);
        System.out.println("Accounts: "+config.numAccounts);
        
        // create client workers in threads to run the transactions
        for (int counter = 0; counter < config.numTransactions; counter++)
        {
            // generate random values for transactions
            int withdrawAccountNum = random.nextInt(config.numAccounts);
            int depositAccountNum;
            do
            {
                depositAccountNum = random.nextInt(config.numAccounts);
            }
            while (depositAccountNum == withdrawAccountNum);
            int ammountToTransfer = random.nextInt(
                    config.minTransfer + config.maxTransfer) 
                    - config.minTransfer + 1;
            
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
                // string of IP
                String ipString = parseIpAddressArg(arguments[0]);

                // port
                int port = parsePortArg(arguments[1]);
                
                // number of transactions to run
                int numTrans = parseNumTransArg(arguments[2]);

                // number of accounts
                int numAccounts = parseNumAccountsArg(arguments[3]);

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
            System.out.println("Usage: <ipAddress=#> <port=#> "
                    + "<numTrans=#> <numAccounts=#> "
                    + "<minTransfer=#> <maxTransfer=#>");
        }
        return null;
    }
    
    
    /** Parses the number of transactions out of the string given. If the number
     * of transactions cannot be parsed, a malformed command line argument
     * exception is thrown. This can happen if the number of transactions to
     * perform is less than 1 or if there integer is not parse-able from the
     * string.
     * 
     * @throws MalformedCommandLineArgumentException.
     */
    private static int parseNumTransArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            String[] split = arg.split("=");
            if( !split[0].equals("numTrans"))
            {
                 throw new MalformedCommandLineArgumentException("Please pass"+
                         " number of transactions argument as <numTrans=#>. "
                         + arg);
            }
            
            int numTrans = Integer.parseInt(split[1]);
            if (numTrans < 1)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "have less than one transaction! Value: " + arg);
            }
            return numTrans;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad number of "
                + "transactions! Value: " + arg);
        }
    }
    
    /** Parses the number of accounts out of the string given. If the number
     * of accounts cannot be parsed, a malformed command line argument
     * exception is thrown. This can happen if the number of accounts
     * is less than 1 or if there integer is not parse-able from the
     * string.
     * 
     * @throws MalformedCommandLineArgumentException.
     */
    private static int parseNumAccountsArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            String[] split = arg.split("=");
            if( !split[0].equals("numAccounts"))
            {
                throw new MalformedCommandLineArgumentException("Please pass"+
                         " number of accounts argument as <numAccounts=#>. "
                         + arg);
            }
            int numAccounts = Integer.parseInt(split[1]);
            if (numAccounts < 1)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "have less than one account! Value: " + arg);
            }
            return numAccounts;
        }
        catch (NumberFormatException nfE)
        {
            throw new MalformedCommandLineArgumentException("Bad number of "
                + "accounts! Value: " + arg);
        }
    }
    
    /** Parses the IP address out of the string given. If the IP address
     * cannot be parsed, a malformed command line argument
     * exception is thrown.
     * 
     * @throws MalformedCommandLineArgumentException.
     */
    private static String parseIpAddressArg(String arg)
            throws MalformedCommandLineArgumentException
    {
        String[] split = arg.split("=");
        if( !split[0].equals("ipAddress"))
        {
            throw new MalformedCommandLineArgumentException("Please pass"+
                     " the IP address as <ipAddress=#>. "
                     + arg);
        }
        return split[1];
    }
    
    /** Parses the port number out of the string given. If the port number is
     * not in the correct range or the port number is not parse-able to an
     * integer, then a malformed command line argument exception is thrown.
     * 
     * @throws MalformedCommandLineArgumentException.
     */
    private static int parsePortArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            String[] split = arg.split("=");
            if( !split[0].equals("port"))
            {
                throw new MalformedCommandLineArgumentException("Please pass"+
                         " port number argument as <port=#>. "
                         + arg);
            }
            int portNum = Integer.parseInt(split[1]);
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
    
    /** Parses the minimum amount acceptable to transfer for the random choosing
     * of the amount to transfer. Throws a malformed command line argument
     * exception if the number is not parse-able to an integer or if the minimum
     * is less than one dollar.
     * 
     * @throws MalformedCommandLineArgumentException.
     */
    private static int parseMinTransferArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            String[] split = arg.split("=");
            if( !split[0].equals("minTransfer"))
            {
                throw new MalformedCommandLineArgumentException("Please pass"+
                         " number of minimum transfers argument as "+
                        "<minTransfer=#>. "
                         + arg);
            }
            int minTransfer = Integer.parseInt(split[1]);
            if (minTransfer < 1)
            {
                throw new MalformedCommandLineArgumentException("Cannot "
                    + "transfer less than one! Value: " + arg);
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
     * parses the maxTransferArg from the command line
     * throws error if argument is not valid
     */
    private static int parseMaxTransferArg(String arg) 
            throws MalformedCommandLineArgumentException
    {
        try
        {
            String[] split = arg.split("=");
            if( !split[0].equals("maxTransfer"))
            {
                throw new MalformedCommandLineArgumentException("Please pass"+
                         " number of maximum transfers argument as "+
                        "<maxTransfer=#>. "
                         + arg);
            }
            int maxTransfer = Integer.parseInt(split[1]);
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
