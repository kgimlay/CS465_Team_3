/**
 *
 */


/**
*
*/
public class ChatMessage extends Message
{
	/**
	*
	*/
	String message;

	/**
	*
	*/
	public ChatMessage(String senderID, String message)
	{
		super.senderID = senderID;
		this.message = message;
	}
}
