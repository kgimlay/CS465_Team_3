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
   private ArrayList<Participant> participantList;
   private Participant selfParticipant;
   private ObjectInputStream fromPeer;

   /** @brief Constructor.
   *  @param socket - The Socket object for receiving the incomming message
   *  from.
   *  @param list - ArrayList of Participants to put newly joined participants
   *  into and to send in join request responses.
   *  @param self - Participant of one node's self. Used in sending the
   *  participant list in a join request response.
   */
   public ReceiveThread( Socket socket,  ArrayList<Participant> list,
                           Participant self)
   {
      // initialize variables
      this.connection = socket;
      this.participantList = list;
      this.selfParticipant = self;
      this.fromPeer = null;
   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Receives the incomming message object and handles it appropriately.
   *  Terminates once the connection is closed by the sending node.
   */
   public void run()
   {
      try
      {
         // set up connections
         fromPeer = new ObjectInputStream( connection.getInputStream() );

         // get the message class type
         Object messageObj = fromPeer.readObject();

         // close connection because it is not being used any more.
         // closing happens on this side to make sure the message is received
         // before the connection is closed.
         connection.close();

         // if message was a chat message, print the message to the console
         if ( messageObj instanceof ChatMessage )
         {
            //System.out.println( messageObj );
            ChatPrettyPrinter.printReceivedMessage(
                  ((ChatMessage)messageObj).senderID,
                  ((ChatMessage)messageObj).message);
         }

         // check if message is a joinMessage without a participant list
         // (received join request)
         else if ( messageObj instanceof JoinMessage
                  && ((JoinMessage)messageObj).participantList == null)
         {
            handleJoinRequest((JoinMessage) messageObj);
         }

         // if message is a JoinMessage
         // (response to join request)
         else if ( messageObj instanceof JoinMessage )
         {
            handleJoinResponse((JoinMessage) messageObj);
         }

         // check if message is a joinedMessage
         else if ( messageObj instanceof JoinedMessage )
         {
            handleJoinedMessage((JoinedMessage) messageObj);
         }

         // check if message is a leave message
         else if ( messageObj instanceof LeaveMessage )
         {
            handleLeaveMessage((LeaveMessage) messageObj);
         }

         // a non-message object was received! This shouldn't happn!
         else
         {
            System.out.println("Non-message object recieved: " + messageObj );
         }
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

   /** @brief Handles a join request.
   *  Responds to a join request with a JoinMessage. The Participant list field
   *  is filled with the participant of this particular node plus it's self
   *  participant added to it.
   */
   private void handleJoinRequest(Message messageObj)
   {
      // copy the Participant list and add your selfParticipant to it,
      // then send that
      ArrayList<Participant> copyOfThreadList =
            new ArrayList<Participant>(participantList);
      copyOfThreadList.add(selfParticipant);

      // get info to send back participantList
      int senderPort= messageObj.portNum;
      InetAddress senderAddress = connection.getInetAddress();

      // create dummy Participant list with just the node to send to
      ArrayList<Participant> recipient =  new ArrayList<Participant>();
      recipient.add( new Participant("", senderAddress, senderPort) );

      // send back JoinMessage with the participant list
      JoinMessage responseMessage = new JoinMessage( selfParticipant.name,
                                                      selfParticipant.port,
                                                      copyOfThreadList);
      Thread response = new Thread( new SendThread( responseMessage,
                                                      recipient ) );
      response.start();
   }

   /** @brief Handles a join response.
   *  Takes the Participant list attribute from the join response and fills one
   *  owns participant list with it. Uses the newly formed participant list to
   *  send JoinedMessages to all the other participants and reports all of the
   *  names of the participants that you have connected to.
   */
   private void handleJoinResponse(JoinMessage messageObj)
   {
      // add participants recieved to participant list
      participantList.addAll(messageObj.participantList);
      // send JoinedMessage to everyone on participant list
      Thread sendJoined = new Thread(new SendThread(
            new JoinedMessage(selfParticipant.name,
                              selfParticipant.port),
                              participantList));
      sendJoined.start();

      // report
      System.out.print("You have joined the chat with ");
      int index;
      for (index = 0; index < participantList.size()-1; index++)
      {
         System.out.print(participantList.get(index).name + ", ");
      }
      System.out.println(participantList.get(index).name);
   }

   /** @brief Handles a joined message.
   *  Takes the information from the message, makes a new Participant with it,
   *  and adds that Participant to the participant list. Then it reports who
   *  has joined the chat.
   */
   private void handleJoinedMessage(JoinedMessage messageObj)
   {
      // add the new node to the list
      JoinedMessage message = messageObj;
      Participant newParticipant = new Participant(message.senderID,
                                             connection.getInetAddress(),
                                             message.portNum);
      participantList.add( newParticipant);

      // report new user
      System.out.println(message.senderID + " has joined the chat!");
   }

   /** @brief Handles a leave message.
   *  When a LeaveMessage is received, the Participant that that message was
   *  sent from is looked up and removed from the participant list. Then the
   *  participant is reported as leaving the chat.
   */
   private void handleLeaveMessage(LeaveMessage messageObj)
   {
      // find the participant in the participant list
      for (int index = 0; index < participantList.size(); index++)
      {
         // if the information matches the participant being compared to
         if( participantList.get(index).name.equals(
                  ((LeaveMessage)messageObj).senderID)
             && participantList.get(index).port ==
                  ((LeaveMessage)messageObj).portNum
             && participantList.get(index).ip.equals(
                  connection.getInetAddress() ))
         {
            // remove node from list
            Participant nodeLeft = participantList.remove(index);
            // report
            ChatPrettyPrinter.printHasLeft(nodeLeft.name);
            break;
         }
      }
   }

}
