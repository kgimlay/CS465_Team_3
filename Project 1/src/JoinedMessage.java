/** @class JoinedMessage
*  @authors Kevin Imlay
*  @date 2-20-21
*/


/** @brief JoinedMessage is the subtype of message that is used to signal to a
*  receiving node that the sending node has already requested and received a
*  participant list. When the receiving node receives a 'joined' message,
*  the receiving node adds the sending node's connection information to it's
*  participant list.
*/
public class JoinedMessage extends Message
{
     /** @brief Constructor.
     *  @param senderID - String of the public chat name to display on peers'
     *  messages.
     *  @param portNum - the port number of the sender.
     */
     public JoinedMessage(String senderID, int portNum)
     {
        super.senderID = senderID;
        super.portNum = portNum;
     }

     /** @brief Override the toString() method to allow for nice printing of
     *  the chat messages.
     */
     public String toString()
     {
        return super.senderID  + " with port number: " + super.portNum;
     }
}
