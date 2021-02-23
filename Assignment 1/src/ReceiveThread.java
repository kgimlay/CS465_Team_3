/**
*
*/

import java.net.Socket;
import java.lang.Runnable;

/**
*
*/
public class ReceiveThread implements Runnable{
   /**
   *
   */
   private Socket connection;

   /**
   *
   */
   public ReceiveThread( Socket socket )
   {
      this.connection = socket;
   }

   /**
   *
   */
   public void run()
   {

   }
}
