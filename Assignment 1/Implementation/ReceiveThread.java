/**
*
*/

import java.net.ServerSocket;
import java.lang.Runnable;

/**
*
*/
public class ReceiveThread implements Runnable{
   /**
   *
   */
   private ServerSocket serverSocket;

   /**
   *
   */
   public ReceiveThread( ServerSocket serverSocket )
   {
      this.serverSocket = serverSocket;
   }

   /**
   *
   */
   public void run()
   {

   }
}
