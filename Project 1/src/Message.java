/** @class Message
*  @authors Kevin Imlay
*  @date 2-20-21
*/

import java.io.Serializable;

/** @brief Message class as parent type for all message objects
*  to be sent between peers.
*  Not implementable because the subtypes will be used to determine the action
*  the node should take.
*/
public abstract class Message implements Serializable
{
     /** @brief SenderID is used in every subtype of messages.
     */
    public String senderID;

    /** @brief Port number for opening connections to.
    */
    public int portNum;

    /** @brief Override the toString() method to alllow for nice printing of
    *  the chat messages.
    */
    public String toString()
    {
       return senderID;
    }
}
