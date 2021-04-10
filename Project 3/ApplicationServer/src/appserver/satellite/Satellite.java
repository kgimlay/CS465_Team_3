package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;
import java.util.*;

/**
 * Class [Satellite] Instances of this class represent computing nodes that execute jobs by
 * calling the callback method of tool a implementation, loading the tool's code dynamically over a network
 * or locally from the cache, if a tool got executed before.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Satellite extends Thread {

    private ConnectivityInfo satelliteInfo = new ConnectivityInfo();
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private HTTPClassLoader classLoader = null;
    private Hashtable toolsCache = null;
    private PropertyHandler satelliteConfiguration = null;
    private PropertyHandler serverConfiguration = null;
    private PropertyHandler classLoaderConfiguration = null;

    public Satellite(String satellitePropertiesFile, String classLoaderPropertiesFile, String serverPropertiesFile) {

        // read this satellite's properties and populate satelliteInfo object,
        // which later on will be sent to the server
        // ...

        try {
            satelliteConfiguration = new PropertyHandler(satellitePropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        satelliteInfo.setName(satelliteConfiguration.getProperty("NAME"));
        satelliteInfo.setPort(Integer.parseInt(satelliteConfiguration.getProperty("PORT")));

        // read properties of the application server and populate serverInfo object
        // other than satellites, the as doesn't have a human-readable name, so leave it out
        // ...

        try {
            serverConfiguration = new PropertyHandler(serverPropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        serverInfo.setHost(serverConfiguration.getProperty("HOST"));
        serverInfo.setPort(Integer.parseInt(serverConfiguration.getProperty("PORT")));

        // read properties of the code server and create class loader
        // -------------------
        // ...

        try {
            classLoaderConfiguration = new PropertyHandler(classLoaderPropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        String classLoaderHost = classLoaderConfiguration.getProperty("HOST");
        int classLoaderPort = Integer.parseInt(serverConfiguration.getProperty("PORT"));
        classLoader = new HTTPClassLoader(classLoaderHost, classLoaderPort);


        // create tools cache
        // -------------------
        // ...
        toolsCache = new Hashtable();

    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        // register this satellite with the SatelliteManager on the server
        // ---------------------------------------------------------------
        // ...


        // create server socket
        // ---------------------------------------------------------------
        try {
            serverSocket = new ServerSocket(this.serverInfo.getPort());
            System.out.println("Opened at " + this.serverInfo.getHost()
                + ":" + this.serverInfo.getPort());
        } catch (IOException ioE) {
            System.out.println("[Satellite.run] An IO Exception occured starting "
                    + "the server socket\n\n" + ioE);
        }


        // start taking job requests in a server loop
        // ---------------------------------------------------------------
        while (true) {
            try {
                new Thread(new SatelliteThread(
                        serverSocket.accept(),
                        this)).run(); // not sure if this is right?
            } catch (IOException ioE) {
                System.out.println("[Satellite.run] An IO Exception occured on "
                    + "on accepting an incomming connection\n\n" + ioE);
            }
        }
    }

    // inner helper class that is instanciated in above server loop and processes single job requests
    private class SatelliteThread extends Thread {

        Satellite satellite = null;
        Socket jobRequest = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;

        SatelliteThread(Socket jobRequest, Satellite satellite) {
            this.jobRequest = jobRequest;
            this.satellite = satellite;
        }

        @Override
        public void run() {
            System.out.println("[SatelliteThread.run]");
            // setting up object streams
            // -----------------------------------------------------------------
            try {
                readFromNet = new ObjectInputStream(
                        this.jobRequest.getInputStream());
                writeToNet = new ObjectOutputStream(
                        this.jobRequest.getOutputStream());
            } catch (IOException ioE) {
                System.out.println("[SatelliteThread.run] An IO Exception has "
                    + "occured while creating input and output streams.\n\n"
                    + ioE);
            }

            // reading message
            // -----------------------------------------------------------------
            try {
                message = (Message)this.readFromNet.readObject();
                System.out.println(message);
            } catch (IOException ioE) {
                System.out.println("[SatelliteThread.run] An IO Exception has "
                    + "occured while reading the incomming message.\n\n" + ioE);
            } catch (ClassNotFoundException cnfE) {
                System.out.println("[SatelliteThread.run] A Call Not Found "
                        + "Exception has occured while reading the incomming "
                        + "message.\n\n" + cnfE);
            }

            switch (message.getType()) {
                case JOB_REQUEST:
                    // processing job request
                    // ---------------------------------------------------------
                    System.out.println("[SatelliteThread.run] Processing...");
                    // todo - put more here
                    break;

                default:
                    System.err.println("[SatelliteThread.run] Warning: Message type not implemented");
            }
        }
    }

    /**
     * Aux method to get a tool object, given the fully qualified class string
     * If the tool has been used before, it is returned immediately out of the cache,
     * otherwise it is loaded dynamically
     */
    public Tool getToolObject(String toolClassString) throws UnknownToolException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Tool toolObject = null;

        // ...

        return toolObject;
    }

    public static void main(String[] args) {
        // start the satellite
        System.out.println("Started");
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();
    }
}
