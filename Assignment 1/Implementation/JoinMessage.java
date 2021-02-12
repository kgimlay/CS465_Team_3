/**
*
*/

import java.util.ArrayList;

/**
*
*/
public class JoinMessage extents Message
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
