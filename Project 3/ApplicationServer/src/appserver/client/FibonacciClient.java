package appserver.client;

import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import appserver.job.Job;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import utils.PropertyHandler;

/**
 *
 * @author kevinimlay
 */
public class FibonacciClient extends Thread {

    private String host = null;
    private int port;
    private Properties properties;
    private int toFindSeqNum = 0;

    /**
     * Obtain server information and the sequence number of Fibonacci to find.
     *
     * @param serverPropertiesFile file with the server's info
     * @param fibSeqValToFind the sequence number of fibonacci to find
     */
    public FibonacciClient(String serverPropertiesFile, int fibSeqValToFind) {
        try {
            // get properties from properties file
            properties = new PropertyHandler(serverPropertiesFile);
            host = properties.getProperty("HOST");
            System.out.println("[FibonnaciClient.FibonacciClient] Host: " + host);
            port = Integer.parseInt(properties.getProperty("PORT"));
            System.out.println("[FibonnaciClient.FibonacciClient] Port: " + port);

            // set the sequence number of fib to find
            this.toFindSeqNum = fibSeqValToFind;
        } catch (Exception ex) {
            System.err.println("[FibonacciClient.FibonacciClient] Error occurred");
            ex.printStackTrace();
        }
    }

    /**
     * Starts the thread.
     *
     * Connects to the application server, sends it a job request, and accepts a
     * response from the server.
     */
    @Override
    public void run() {
        try {
            // connect to application server
            Socket server = new Socket(host, port);

            // hard-coded string of class, aka tool name
            String classString = "appserver.job.impl.Fibonacci";

            // create job and job request message
            Job job = new Job(classString, this.toFindSeqNum);
            Message message = new Message(JOB_REQUEST, job);

            // sending job out to the application server in a message
            ObjectOutputStream writeToNet = new ObjectOutputStream(server.getOutputStream());
            writeToNet.writeObject(message);

            // reading result back in from application server
            // for simplicity, the result is not encapsulated in a message
            ObjectInputStream readFromNet = new ObjectInputStream(server.getInputStream());
            Integer result = (Integer) readFromNet.readObject();
            System.out.println("Fibonacci of " + this.toFindSeqNum + ":" + result);
        } catch (Exception ex) {
            System.err.println("[FibonacciClient.run] Error occurred");
            ex.printStackTrace();
        }
    }

    /**
     * Main method to run many threads of the Fibonacci client for load testing
     * the application server.
     *
     * @param args Command line arguments. Not used.
     */
    public static void main(String args[]) {
        // create threads and run them
        // find fib(i) starting at largest to ensure high load from start
        for (int i = 46; i > 0; i--) {
            (new FibonacciClient(args[0], i)).start();
            System.out.println("[FibonacciClient] started");
        }
    }

}
