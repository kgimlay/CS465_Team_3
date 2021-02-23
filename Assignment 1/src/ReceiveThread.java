/**
*
*/

import java.net.Socket;
import java.util.Scanner;
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
