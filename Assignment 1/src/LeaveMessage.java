/** @class LeaveMessage
*  @authors Kevin Imlay
*  @date 2-20-21
*/


/** @brief LeaveMessage is the message subtype that is used to signal to nodes
*  that the sending node is leaving the topology. This message must be set to
*  gracefully disconnect from the topology. The receiving nodes must use this
*  message to remove the sending node from their participants lists.
*/
public class LeaveMessage extends Message
{
     /** Constructor.
     *  @param senderID - String of the public chat name to display on peers'
     *  messages.
     */
     public LeaveMessage(String senderID)
     {
          super.senderID = senderID;
     }

     /** @brief Override the toString() method to alllow for nice printing of
     *  the chat messages.
     */
     public String toString()
     {
        return super.senderID;
     }
}
