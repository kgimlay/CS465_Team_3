/** @class ReceiveManager
*  @authors Kevin Imlay
*  @date 2-23-21
*/

import java.net.ServerSocket;
import java.lang.Runnable;
import java.lang.Thread;
import java.io.IOException;

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

   /** @brief Constructor.
   *  @param serverSocket - ServerSocket for spawning new connections on sockets.
   */
   public void ReceiveManager( ServerSocket serverSocket )
   {
     this.serverSocket = serverSocket;
   }

   /** @brief Start the ReceiveManager thread.
   *  Runs a loop, creating new ReceiveThreads (and runs them) upon
   *  acceptance of an incomming connection request.
   */
   public void run()
   {
      // loop to create as many threads as needed
      while (true)
      {
         try
         {
            // spawn threads on accept and run them
            Thread receiveThread = new Thread(
               new ReceiveThread(serverSocket.accept()) );
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
