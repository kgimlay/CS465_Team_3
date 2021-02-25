/** @class JoinedMessage
*  @authors
*  @date
*/


/** @brief JoinedMessage is the subtype of message that is used to signal to a
*  receiving node that the sending node has already requested and received a
*  participant list. When the receiving node receives this a 'joined' message,
*  the receiving node adds the sending node's connection information to it's
*  participant list.
*/
public class JoinedMessage extends Message
{
     /** @brief Constructor.
     *  @param senderID - String of the public chat name to display on peers'
     *  messages.
     */
     public JoinedMessage(String senderID)
     {
          super.senderID = senderID;
     }
}
