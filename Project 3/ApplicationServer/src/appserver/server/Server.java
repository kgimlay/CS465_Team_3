package appserver.server;

import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.comm.ConnectivityInfo;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Server {

    // Singleton objects - there is only one of them. For simplicity, this is not enforced though ...
    static SatelliteManager satelliteManager = null;
    static LoadManager loadManager = null;
    static ServerSocket serverSocket = null;
    static ConnectivityInfo serverInfo = new ConnectivityInfo();

    public Server(String serverPropertiesFile) {

        // create satellite manager and load manager
        satelliteManager = new SatelliteManager();
        loadManager = new LoadManager();

        // read server properties and create server socket
        try {
            PropertyHandler serverConfiguration = new PropertyHandler(
                    serverPropertiesFile);
            serverInfo.setName(serverConfiguration.getProperty("NAME"));
            serverInfo.setPort(Integer.parseInt(
                    serverConfiguration.getProperty("PORT")));
            serverSocket = new ServerSocket(serverInfo.getPort());
            System.out.println("Opened at " + serverSocket);
        } catch (IOException ioE) {
            System.out.println("[Server.java] An IO Exception has occured!" 
                    + ioE);
        }
    }

    public void run() {
        // serve clients in server loop ...
        // when a request comes in, a ServerThread object is spawned
        try {
            while (true) {
                (new ServerThread(serverSocket.accept())).start();
            }
        } catch (IOException ioE) {
            System.out.println("[Server.java] An IO Exception has occured!" 
                    + ioE);
        }
    }

    // objects of this helper class communicate with satellites or clients
    private class ServerThread extends Thread {

        Socket client = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;
        SatelliteManager satelliteManager = null;
        LoadManager loadManager = null;

        private ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            // set up object streams and read message
            try {
                readFromNet = new ObjectInputStream(
                        this.client.getInputStream());
                writeToNet = new ObjectOutputStream(
                        this.client.getOutputStream());
                message = (Message) readFromNet.readObject();
            } catch (IOException ioE) {
                System.out.println("[ServerThread.java] An IO Exception has "
                        + "occurred!" + ioE);
            } catch (ClassNotFoundException cnfE) {
                System.out.println("[ServerThread.java] A Class Not Found "
                        + "Exception has occurred!" + cnfE);
            }

            // process message
            switch (message.getType()) {
                case REGISTER_SATELLITE:
                    System.err.println("\n[ServerThread.run] Received "
                            + "registration request");

                    // read satellite info
                    ConnectivityInfo satelliteInfo = 
                            (ConnectivityInfo) message.getContent();

                    // register satellite
                    synchronized (Server.satelliteManager) {
                        Server.satelliteManager.registerSatellite(satelliteInfo);
                    }

                    // add satellite to loadManager
                    synchronized (Server.loadManager) {
                        Server.loadManager.satelliteAdded(satelliteInfo.getName());
                    }

                    break;

                case JOB_REQUEST:

                    String satelliteName = null;
                    ConnectivityInfo satConnInfo = null;
                    synchronized (Server.loadManager) {
                        // get next satellite from load manager
                        try {
                            satelliteName = Server.loadManager.nextSatellite();
                        } catch (Exception e) {
                            System.out.println("[ServerThread.run] Exception. " 
                                    + e);
                        }

                        // get connectivity info for next satellite from 
                        // satellite manager
                        System.err.println("[ServerThread.run] Received job "
                                + "request, forwarding to " + satelliteName);
                        satConnInfo = Server.satelliteManager
                                .getSatelliteForName(satelliteName);
                    }

                    try {
                        // open object streams,
                        Socket satelliteSoc = new Socket(satConnInfo.getHost(), 
                                satConnInfo.getPort());
                        ObjectInputStream inSat = new ObjectInputStream(
                                satelliteSoc.getInputStream());
                        ObjectOutputStream outSat = new ObjectOutputStream(
                                satelliteSoc.getOutputStream());

                        // forward message (as is) to satellite,
                        outSat.writeObject(message);

                        // receive result from satellite and
                        // write result back to client
                        writeToNet.writeObject(inSat.readObject());

                    } catch (IOException ioE) {
                        System.out.println("[ServerThread.java] An IO Exception"
                                + " has occurred!" + ioE);
                    } catch (ClassNotFoundException cnfE) {
                        System.out.println("[ServerThread.java] A Class Not "
                                + "Found Exception has occurred " + cnfE);
                    }

                    break;

                default:
                    System.err.println("[ServerThread.run] Warning: Message "
                            + "type not implemented");
            }
        }
    }

    public static void main(String[] args) {
        // start the application server
        Server server = null;
        if (args.length == 1) {
            server = new Server(args[0]);
        } else {
            server = new Server("../../config/Server.properties");
        }
        server.run();
    }
}
