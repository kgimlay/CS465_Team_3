/** @class ReceiveThread
*  @authors Matt Flanders
*  @date 2-25-21
*
*  @todo Fugure out how to manipulate par list from calls in here.
*/

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Runnable;
import java.io.IOException;
import java.lang.ClassNotFoundException;

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

   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Receives the incomming message object and handles it appropriately.
   *  Terminates once the connection is closed by the sending node.
   */
   public void run()
   {
      // print for debugging
      //System.out.println("---Starting Receive Thread---");

      try
      {
         // set up connections
         fromClient = new ObjectInputStream( connection.getInputStream() );

         // get the message class type
         Object messageClass = fromClient.readObject();
         // close connection
         connection.close();

         // check if message was a chat message
         if ( messageClass instanceof ChatMessage )
         {
            // print the message to the console
            System.out.println( messageClass );
         }

         // check if message equal to a joinMessage
         else if ( messageClass instanceof JoinMessage
                  && ((JoinMessage)messageClass).participantList == null)
         {
            // copy the Participant list and add your selfParticipant to it, then send that
            ArrayList<Participant> copyOfThreadList = new ArrayList<Participant>(threadList);
            copyOfThreadList.add(threadSelf);
            // send back threadList
            int senderPort= ((Message)messageClass).portNum;
            InetAddress senderAddress = connection.getInetAddress();
            ArrayList<Participant> recipient =  new ArrayList<Participant>();
            recipient.add( new Participant("bbb", senderAddress, senderPort) );
            JoinMessage responseMessage = new JoinMessage( "aaa", threadSelf.port, copyOfThreadList);
            Thread response = new Thread( new SendThread( responseMessage, recipient ) );
            response.start();
         }
         else if ( messageClass instanceof JoinMessage )
         {
            // add participants recieved to participant list
            threadList.addAll(((JoinMessage)messageClass).participantList);
            // send JoinedMessage to everyone on participant list
            Thread sendJoined = new Thread(new SendThread( new JoinedMessage(threadSelf.name, threadSelf.port), threadList));
            sendJoined.start();

            // report
            System.out.print("You have joined the chat with ");
            int index;
            for (index = 0; index < threadList.size()-1; index++)
            {
               System.out.print(threadList.get(index).name + ", ");
            }
            System.out.println(threadList.get(index).name);
         }

         // check if message equal to a joinedMessage
         else if ( messageClass instanceof JoinedMessage )
         {
            // add the new node to the list
            JoinedMessage message = (JoinedMessage)messageClass;
            Participant newParticipant = new Participant(message.senderID, connection.getInetAddress(), message.portNum);
            threadList.add( newParticipant );

            // report new user
            System.out.println(message.senderID + " has joined the chat!");
         }

         // check if message equal to leave message
         else if ( messageClass instanceof LeaveMessage )
         {
            //set up variables for participant list search
            int index = 0;
            boolean found = false;
            int length = threadList.size();
            // find the participant in the participant list
            while( index < length && !found)
            {
               if( threadList.get(index).name ==  ((LeaveMessage)messageClass).senderID 
                   && threadList.get(index).port == ((LeaveMessage)messageClass).portNum
                   && threadList.get(index).ip == connection.getInetAddress() )
               {
                  found = true;      
               }
            }
            int indexToRemove;
            // remove node from list
            threadList.remove(index);
         }

         else
         {
            System.out.println("Non-message object recieved: " + messageClass );
         }

         // print for debugging purposes
         //System.out.println("---Ending Receive Thread---");
      }
      catch (IOException ioE)
      {
         System.out.println("Error in recieving incoming message. " + ioE);
      }
      catch (ClassNotFoundException clE)
      {
         System.out.println("Error in recieving incoming message. " + clE);
      }

   }
}
