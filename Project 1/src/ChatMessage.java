/** @class ChatMessage
*  @authors Kevin Imlay
*  @date 2-20-21
*/


/** @brief ChatMessage is the subtype that carries a message string between
*  peers. This is the 'message' you would expect in a message application.
*/
public class ChatMessage extends Message
{
	/** @brief String of the message content to display on the receiver's
   *  messages.
	*/
	String message;

	/** @brief Constructor.
	*  @param senderID - String of the public chat name to display on peers'
   *  messages.
   *  @param portNum - the port number of the sender.
   *  @param message - String of the message to display on the peers' messages.
	*/
	public ChatMessage(String senderID, int portNum, String message)
	{
		super.senderID = senderID;
      super.portNum = portNum;
		this.message = message;
	}

   /** @brief Override the toString() method to allow for nice printing of
   *  the chat messages. Includes the message and who sent it.
   */
   public String toString()
   {
      return super.senderID + " >> " + message;
   }
}
