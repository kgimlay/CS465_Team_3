/** @class JoinMessage
*  @authors
*  @date
*/

import java.util.ArrayList;

/** @brief JoinMessage is the subtype of Message that is sent from a node that
*  wants to join the chat, to a known node in the chat topology. This is the
*  first message a joining node sends. When sent by the joining node, the
*  participant list is left Null. The node that is contacted sends a "join"
*  message back in response, and fills the participant list with the list of
*  participants to add to the joining node's participant list.
*/
public class JoinMessage extends Message
{
     /** @brief List of Participants for keeping track of connection information
     *  to all other nodes in the topology. Left blank when the message is sent
     *  from the joining node, and filled when the contacted node responds.
     */
     ArrayList<Participant> participantList;

     /** @brief Constructor.
     *  @param senderID - String of the public chat name to display on peers'
     *  messages.
     *  @param participantList - An ArrayList of Participant objects. Optional
     *  as it should only be not Null if the message is responding to a node
     *  that wants to join the chat.
     */
     public JoinMessage(String senderID, ArrayList<Participant> participantList)
     {
          super.senderID = senderID;
          this.participantList = participantList;
     }
}
