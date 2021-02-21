/**
 *
 */

import java.util.ArrayList;
import java.net.InetAddress;

/**
 *
 */
public class ChatNode
{

	/**
	 *
	 */
	private Thread<ReceiveThread> receiveManager;

	/**
	 *
	 */
	private Thread<SendThread> sendManager;

	/**
	 *
	 */
	private ArrayList<Participant> participantList;

	/**
	 *
	 */
	private ServerSocket serverSocket;

	/**
	 *
	 */
	public void startChatNode()
	{

	}

	/**
	 *
	 */
	public void joinChat(InetAddress ip, int port)
	{

	}

	/**
	 *
	 */
	public void sendMessage(String message)
	{

	}

	/**
	 *
	 */
	public void leaveChat()
	{

	}

	/**
	 *
	 */
	private void addParticipant(Participant participant)
	{

	}

	/**
	 *
	 */
	private void addParticipants(ArrayList<Participant> participants)
	{

	}

	/**
	 *
	 */
	private void removeParticipant(Participant participant)
	{

	}

	/**
	 *
	 */
	public void main(String args[])
	{

	}
}
