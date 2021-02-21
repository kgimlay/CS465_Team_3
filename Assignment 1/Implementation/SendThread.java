/**
*
*/

import java.util.ArrayList;

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
      self.message = null;
      self.recipients = null;
   }

   /**
   *
   */
   public stageMessage( Message message, ArrayList<Participant> recipients )
   {
      self.message = message;
      self.recipients = recipients;
   }

   /**
   *
   */
   public void run()
   {

   }
}
