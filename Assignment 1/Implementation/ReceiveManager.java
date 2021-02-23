/**
 *
 */

 import java.net.ServerSocket;
 import java.lang.Runnable;

 /**
  *
  */
public class ReceiveManager implements Runnable
{
   /**
    *
    */
    private ServerSocket serverSocket;

    /**
     *
     */
     public void ReceiveManager( ServerSocket serverSocket )
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
