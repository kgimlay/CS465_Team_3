/**
*
*/

import java.util.ArrayList;

/**
*
*/
public class JoinMessage extends Message
{
     /**
     *
     */
     ArrayList<Participant> participantList;

     /**
     *
     */
     public JoinMessage(String senderID, ArrayList<Participant> participantList)
     {
          super.senderID = senderID;
          this.participantList = participantList;
     }
}
