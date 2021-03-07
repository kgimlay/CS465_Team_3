/** @class SendThread
*  @authors Randy Duerinck
*  @date 2-25-21
*/

import java.util.ArrayList;
import java.net.InetAddress;
import java.lang.Runnable;
import java.io.*;
import java.net.*;


/** @brief SendThread handles sending a message to all other nodes in the
*  topology. Once started, iterates over all the participants in the
*  Participant list and sends the message to them. Terminates once the message
*  has been sent to every node on the participant list.
*/
public class SendThread implements Runnable{
   /** @brief Message object to send.
   */
   private Message message;
   /** @brief Participant list to iterate over for sending the message.
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
      // initialize variables
      Socket socketObject;
      int index;
      // loop across recipient list and send messge to all
      for(index = 0; index < recipients.size(); index ++)
      {
         try
         {
            // get temp recipient
            Participant recipient = recipients.get(index);

            // open new connection and send message
            socketObject = new Socket(recipient.ip, recipient.port);
            ObjectOutputStream outputStream =
                     new ObjectOutputStream( socketObject.getOutputStream() );
            outputStream.writeObject(message);
         }
         catch( IOException ioE )
         {
    	      // if send fails due to connection refused
            // remove that participant from the participant list
            if (ioE.toString().contains("Connection refused"))
            {
               Participant leftParticipant = recipients.remove(index);
               ChatPrettyPrinter.printCannotBeReached(leftParticipant.name);
               //ChatPrettyPrinter.printHasLeft(leftParticipant.name);
            }
         }

      }
   }
 }
