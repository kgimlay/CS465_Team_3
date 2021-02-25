/** @class ChatMessage
*  @authors
*  @date
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
   *  @param message - String of the message to display on the peers' messages.
	*/
	public ChatMessage(String senderID, String message)
	{
		super.senderID = senderID;
		this.message = message;
	}
}