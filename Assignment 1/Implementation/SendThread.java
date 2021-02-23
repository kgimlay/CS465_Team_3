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

   }
}
