/**
*
*/

import java.util.ArrayList;
import java.net.InetAddress;
import java.lang.Runnable;

/**
*
*/
public class SendThread implements Runnable{
   /**
   *
   */
   private Message message;

   /**
   *
   */
   private ArrayList<Participant> recipients;

   /**
   *
   */
   public SendThread( Message message, ArrayList<Participant> recipients )
   {
      this.message = message;
      this.recipients = recipients;
   }

   /**
   *
   */
   public void run()
   {
    for( client in recipients )
    {
      if( client != self )//Idk how to see if it is yourself

      ObjectOutputStream oos = new ObjectOutputStream(message);
       oos.writeObject("Today");
       objectOutputStream.writeObject(message);
       objectOutputStream.close();
    }
   }
}
