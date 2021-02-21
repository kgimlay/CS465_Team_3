/**
*
*/

import java.util.ArrayList;
import java.net.InetAddress;

/**
*
*/
public class SendThread {
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
   public SendThread()
   {
      this.message = null;
      this.recipients = null;
   }

   /**
   *
   */
   public void stageMessage( Message message, ArrayList<Participant> recipients )
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
