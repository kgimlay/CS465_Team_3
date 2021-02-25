/** @class SendThread
*  @authors
*  @date
*/

import java.util.ArrayList;
import java.net.InetAddress;
import java.lang.Runnable;

/** @brief SendThread handles sending a message to all other nodes in the
*  topology. Once started, iterates over all the participants in the
*  Participant list and sends the message to them. Terminates once the message
*  has been sent to every node on the participant list.
*/
public class SendThread implements Runnable{
   /** @brief Message object to send.
   */
   private Message message;

   /** @brief Participant list to itterate over for sending the message.
   */
   private ArrayList<Participant> recipients;

   /** @brief Constructor.
   *  @param message - Message object of the message to send.
   *  @param recipients - ArrayList of Participant as the participant list to
   *  send the message to.
   */
   public SendThread( Message message, ArrayList<Participant> recipients )
   {
      this.message = message;
      this.recipients = recipients;
   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Once started, iterates over each participant in the participant list. For
   *  each participant, a connection is opened, the message is sent, and the
   *  connection is closed. The message is not sent to it's sending node (self).
   *  Once all the message has been sent to all participants, the
   *  thread terminates.
   */
   public void run()
   {
   /*
    for( client in recipients )
    {
      if( client != self )//Idk how to see if it is yourself

      ObjectOutputStream oos = new ObjectOutputStream(message);
       oos.writeObject("Today");
       objectOutputStream.writeObject(message);
       objectOutputStream.close();
    }
    */
   }
}
