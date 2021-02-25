/** @class ReceiveThread
*  @authors
*  @date
*
*  @todo Fugure out how to manipulate par list from calls in here.
*/

import java.net.Socket;
import java.util.Scanner;
import java.lang.Runnable;
import java.util.ArrayList;

/** @brief ReceiveThread handles receiving a message from a single connection
*  in the topology. A ReceiveThread is spawned on the acceptance of an incomming
*  connection request, it reads in the incomming message, and reacts to that
*  message however is appropriate: ChatMessage - print to print stream (console)
*  JoinMessage, responds with a Participant list, JoinedMessage - adds the
*  sending node to the Participant list, LeaveMessage - removes the node from
*  the Participant list. The thread terminates once the sending side of the
*  connection closes the connection.
*/
public class ReceiveThread implements Runnable{
   /**  @brief A Socket object for receiving the incomming message from.
   */
   private Socket connection;

   /** @brief The list of participants in the topology. Excludes one's self.
   */
   private ArrayList<Participant> participantList;

   /** @brief The one's self excluded from the participant list. This is kept
   *  separate to prevent sending to one self, and is added to the participant
   *  list that is sent in a response to a "JoinMessage".
   */
   private Participant selfParticipant;

   /** @brief Constructor.
   *  @param socket - The Socket object for receiving the incomming message
   *  from.
   *  @param participantList - The list of participants. Used to respond to a
   *  "JoinedMessage" and "JoinedMessage".
   *  @param selfParticipant - The participant that represents this particular
   *  node. Used for responding to a "JoinMessage".
   */
   public ReceiveThread( Socket socket, ArrayList<Participant> participantList,
                           Participant selfParticipant )
   {
      this.connection = socket;
      this.participantList = participantList;
      this.selfParticipant = selfParticipant;
   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Receives the incomming message object and handles it appropriately.
   *  Terminates once the connection is closed by the sending node.
   */
   public void run()
   {

   }
}
