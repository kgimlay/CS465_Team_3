/** @class Message
*  @authors Kevin Imlay
*  @date 2-20-21
*/


/** @brief Message class as parent type for all message objects
*  to be sent between peers.
*  Not implementable because the subtypes will be used to determine the action
*  the node should take.
*/
public abstract class Message
{
     /** @brief SenderID is used in every subtype of messages.
     */
    String senderID;

}
