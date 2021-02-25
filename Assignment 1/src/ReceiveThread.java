/** @class ReceiveThread
*  @authors
*  @date
*
*  @todo Fugure out how to manipulate par list from calls in here.
*/

import java.net.Socket;
import java.util.Scanner;
import java.lang.Runnable;

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

   /** @brief Constructor.
   *  @param socket - The Socket object for receiving the incomming message
   *  from.
   */
   public ReceiveThread( Socket socket )
   {
      this.connection = socket;
   }

   /** @brief Interface method from Runnable - starts the thread.
   *  Receives the incomming message object and handles it appropriately.
   *  Terminates once the connection is closed by the sending node.
   */
   public void run()
   {

      try
      {
         Scanner scanner = new Scanner(connection.getInputStream());
         String message = scanner.nextLine();
         System.out.println(message);
         connection.close();
      }
      catch (Exception e)
      {
         System.out.println("Error in getting connection message.");
      }

   }
}
