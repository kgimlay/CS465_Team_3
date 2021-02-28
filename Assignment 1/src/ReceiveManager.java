/** @class ReceiveManager
*  @authors Kevin Imlay
*  @date 2-23-21
*/

import java.net.ServerSocket;
import java.lang.Runnable;
import java.lang.Thread;
import java.io.IOException;
import java.util.ArrayList;

/** @brief ReceiveManager
*  Implements Runnable to run as a seperate thread from the rest of the program.
*  Accepts incomming connection requests and spawns new threads to handle those
*  connections for receiving messages.
*/
public class ReceiveManager implements Runnable
{
   /** @brief ServerSocket attribute to be used to spawn new connections.
   */
   private ServerSocket serverSocket;

   /** @brief The list of participants in the topology. Excludes one's self.
   *  Passed to the ReceiveThreads generated.
   */
   private ArrayList<Participant> participantList;

   /** @brief The one's self excluded from the participant list. This is kept
   *  separate to prevent sending to one self, and is added to the participant
   *  list that is sent in a response to a "JoinMessage". Passed to the
   *  ReceiveThreads generated.
   */
   private Participant selfParticipant;

   /** @brief Constructor.
   *  @param serverSocket - ServerSocket for spawning new connections on
   *  sockets.
   *  @param participantList - The list of participants. Used to respond to a
   *  "JoinedMessage" and "JoinedMessage".
   *  @param selfParticipant - The participant that represents this particular
   *  node. Used for responding to a "JoinMessage".
   */
   public ReceiveManager( ServerSocket serverSocket,
                                 ArrayList<Participant> participantList,
                                 Participant selfParticipant )
   {
     this.serverSocket = serverSocket;
     this.participantList = participantList;
     this.selfParticipant = selfParticipant;
   }

   /** @brief Start the ReceiveManager thread.
   *  Runs a loop, creating new ReceiveThreads (and runs them) upon
   *  acceptance of an incomming connection request.
   */
   public void run()
   {
      // print for debugging
      //System.out.println("---Starting Receive Manager---");

      // loop to create as many threads as needed
      while (true)
      {
         try
         {
            // spawn threads on accept and run them
            Thread receiveThread = new Thread(
               new ReceiveThread(serverSocket.accept(), this.participantList,
                                 this.selfParticipant) );
            receiveThread.run();
         }
         catch (IOException ioE)
         {
   			System.out.println("Something went wrong while waiting "
   			+ "for a connection!");
   			System.exit(1);
   		}
      }
   }
}
