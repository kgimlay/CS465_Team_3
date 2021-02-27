/** @class ReceiveThread
*  @authors Matt Flanders
*  @date 2-25-21
*
*  @todo Fugure out how to manipulate par list from calls in here.
*/

import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Runnable;

/** @brief ReceiveThread handles receiving a message from a single connection
*  in the topology. A ReceiveThread is spawned on the acceptance of an incomming
*  connection request, it reads in the incomming message, and reacts to that
*  message however is appropriate:
*  ChatMessage    -  print to print stream (console)
*  JoinMessage    -  responds with a Participant list
*  JoinedMessage  -  adds the sending node to the Participant list
*  LeaveMessage   -  removes the node from the Participant list.
*  The thread terminates once the sending side of the
*  connection closes the connection.
*/
public class ReceiveThread implements Runnable{
   /**  @brief A Socket object for receiving the incomming message from.
   */
   private Socket connection;

   private ArrayList<Participant> threadList;

   private Participant threadSelf;

   private ObjectInputStream fromClient;

   private ObjectOutputStream toClient;


   /** @brief Constructor.
   *  @param socket - The Socket object for receiving the incomming message
   *  from.
   */
   public ReceiveThread( Socket socket,  ArrayList<Participant> list, Participant self)
   {
      // initialize variables
      this.connection = socket;
      this.threadList = list;
      this.threadSelf = self;
      this.fromClient = null;
      this.toClient = null;
   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Receives the incomming message object and handles it appropriately.
   *  Terminates once the connection is closed by the sending node.
   */
   public void run()
   {
      // print for debugging
      System.out.println("---Starting Receive Thread---");

      try
      {
         // set up connections
         fromClient = new ObjectInputStream( connection.getInputStream() );
         toClient = new ObjectOutputStream( connection.getOutputStream() );   // possibly not work? might need to spawn a new SendThread instead for responding

         // get the message class type
         Object messageClass = fromClient.readObject();  // is this blocking? We want it to be blocking

         System.out.println(messageClass);
         // check if message was a chat message
         if ( messageClass instanceof ChatMessage )
         {
            // print the message to the console
            String message = ( String ) messageClass;
            System.out.println( message );
         }

         // check if message equal to a joinMessage
         if ( messageClass instanceof JoinMessage )
         {
            // clean toClient object
            toClient.reset();
            // copy the Participant list and add your selfParticipant to it, then send that
            ArrayList<Participant> copyOfThreadList = threadList;
            copyOfThreadList.add(threadSelf);
            // send back threadList
            toClient.writeObject( copyOfThreadList );
         }

         // check if message equal to a joinedMessage
         if ( messageClass instanceof JoinedMessage )
         {
            // add the new node to the list
            threadList.add( ( Participant ) messageClass );
         }

         // check if message equal to leave message
         if ( messageClass instanceof LeaveMessage )
         {
            // remove node from list
            // threadList.remove(fromClient.readObject());  // get the sending Participant and remove them from the Participant list
            // same mistakes as before not using chatMessage, leaving previous just in case
            threadList.remove( messageClass );
         }

         // print for debugging purposes
         System.out.println("---Ending Receive Thread---");
         // close connection
         connection.close();
      }
      catch (Exception ioE)
      {
         System.out.println("Error in recieving incoming message. " + ioE);
      }

   }
}
