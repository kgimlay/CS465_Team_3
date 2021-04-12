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
import java.lang.reflect.InvocationTargetException;
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
        try {
            satelliteConfiguration = new PropertyHandler(satellitePropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        
        //Give satellite name and port information to the satelliteInfo object
        //from the given Satellite properties file
        satelliteInfo.setName(satelliteConfiguration.getProperty("NAME"));
        satelliteInfo.setPort(Integer.parseInt(satelliteConfiguration.getProperty("PORT")));

        // read properties of the application server and populate serverInfo object
        // other than satellites, the as doesn't have a human-readable name, so leave it out
        try {
            serverConfiguration = new PropertyHandler(serverPropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        
        //Give server host and port information to the serverInfo object
        //from the given Server properties file 
        serverInfo.setHost(serverConfiguration.getProperty("HOST")); 
        serverInfo.setPort(Integer.parseInt(serverConfiguration.getProperty("PORT")));

        // read properties of the code server and create class loader      
        try {
            classLoaderConfiguration = new PropertyHandler(classLoaderPropertiesFile);
        } catch (IOException e) {
            // no use carrying on, so bailing out ...
            System.err.println("No config file found, bailing out ...");
            System.exit(1);
        }
        
        //Get and store classLoader host and port information into respective 
        //variables from the given WebServer properties file
        String classLoaderHost = classLoaderConfiguration.getProperty("HOST");
        int classLoaderPort = Integer.parseInt(classLoaderConfiguration.getProperty("PORT"));
        
        // create the class loader object using the host and port info obtained
        classLoader = new HTTPClassLoader(classLoaderHost, classLoaderPort);
        System.out.println("Will connect to web server at: " + classLoaderHost + " : " + classLoaderPort);

        // create tools cache
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
            serverSocket = new ServerSocket(this.satelliteInfo.getPort());
            System.out.println("Port :" + this.satelliteInfo.getPort());
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
                        this)).start(); // not sure if this is right?
            } catch (IOException ioE) {
                System.out.println("[Satellite.run] An IO Exception occured on "
                    + "on accepting an incomming connection\n\n" + ioE);
            }
        }
    }

    // inner helper class that is instantiated in above server loop and processes single job requests
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
            Tool tool = null;
            
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
                    
                    // get the job to run
                    Job job = (Job)message.getContent();
                    String toolName = job.getToolName();
                    Object parameters = job.getParameters();
                    
                    // get tool and calculate result
                    try {
                        tool = getToolObject(toolName);
                    } catch (UnknownToolException utE) {
                        System.out.println("[SatelliteThread.run] UnknownToolException\n\n" + utE);
                        return;
                    } catch (ClassNotFoundException cnfE) {
                        System.out.println("[SatelliteThread.run] ClassNotFoundException\n\n" + cnfE);
                        return;
                    } catch (InstantiationException iE) {
                        System.out.println("[SatelliteThread.run] InstantiationException\n\n" + iE);
                        return;
                    } catch (IllegalAccessException iaE) {
                        System.out.println("[SatelliteThread.run] IllegalAccessException\n\n" + iaE);
                        return;
                    } catch (NoSuchMethodException nsmE) {
                        System.out.println("[SatelliteThread.run] NoSuchMethodException\n\n" + nsmE);
                        return;
                    }
                    
                    // get result
                    Object result = tool.go(parameters);
                    
                    // send result back to client
                    try {
                        this.writeToNet.writeObject(result);
                    } catch (IOException ioE) {
                        System.out.println("[SatelliteThread.run] An IO Exception has "
                        + "occured while writing the outgoing message.\n\n" + ioE);
                    }
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
     * @param toolClassString
     * @return Tool object
     * @throws appserver.job.UnknownToolException 
     * @throws java.lang.ClassNotFoundException 
     * @throws java.lang.InstantiationException 
     * @throws java.lang.IllegalAccessException 
     * @throws java.lang.NoSuchMethodException 
     */
    public Tool getToolObject(String toolClassString) 
        throws UnknownToolException, ClassNotFoundException, 
        InstantiationException, IllegalAccessException, 
        NoSuchMethodException {

        Tool toolObject = null;

        // if tool object not already in cache, it will throw UnknownToolException
        if ((toolObject = (Tool)toolsCache.get(toolClassString)) == null) 
        {
            // The class that is being accessed is PlusOne. Print that info.
            String toolClassStr = "appserver.job.impl.PlusOne";
            System.out.println("\nTool's Class: " + toolClassStr);
            
            if (toolClassStr == null) 
            {
                throw new UnknownToolException();
            }
            
            // load the PlusOne class and store into toolClass
            Class<?> toolClass = classLoader.loadClass(toolClassStr);
            
            // attempt to create the tool object using the class loaded
            try {
                toolObject = (Tool) toolClass.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("[Satellite] getToolObject() - InvocationTargetException");
            }
            // store the tool object in the cache so class loading isn't necessary
            // on the next attempt to get the tool 
            toolsCache.put(toolClassString, toolObject);
        } 
        
        // the tool is already stored in cache, so display info and return it
        else 
        {
            System.out.println("Tool: \"" + toolClassString + "\" already in Cache");
        }
        
        return toolObject;
    }

    public static void main(String[] args) {
        // start the satellite
        System.out.println("Started");
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();
    }
}
