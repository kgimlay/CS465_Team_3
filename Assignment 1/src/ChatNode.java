/**
 *
 */

import java.util.ArrayList;
import java.util.Scanner;

import javax.print.event.PrintEvent;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.lang.Thread;

/**
 *
 */
public class ChatNode
{

	/**
	 *
	 */
	private ReceiveManager receiveManager;

	/**
	 *
	 */
	private SendThread sendManager;

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
	public static void startChat( int portNumber)
	{

	}

	/**
	 *
	 */
	public static void joinChat(InetAddress ip, int port)
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
	public static void main(String args[])
	{
		// flag for starting new chat or joining an existing chat
		boolean isStartingNew = true;
		int portNum = 0;
		InetAddress ipAddress = null;

		// try to read command line args
		try
		{
			// get the port number as an int
			portNum = Integer.parseInt(args[1]);

			// if first argument passed is "-n" we are creating a new chat
			if (args[0].equals("-n"))
			{
				// set flag to starting a chat
				isStartingNew = true;
			}

			// if first argument is "-j" we join an existing chat
			else if(args[0].equals("-j"))
			{
				// set flag to join chat
				isStartingNew = false;

				// try and get ip address passed in args
				try
				{
					ipAddress = InetAddress.getByName(args[2]);
				}
				catch (UnknownHostException e)
				{
					System.out.println("Unkown host or malformed ip address.\n");
					System.exit(1);
				}

			}

			// something went wrong in the command line arg
			else
			{
				System.out.println("Command line args given to start chat incorrect.\n");
				System.out.println("Arg 0 : \"" + args[0] + "\" should be \"-n\" or \"-j\".");
				System.exit(1);
			}

			// test to see if a valid ip address has been given
			if (portNum < 0 || portNum > 65535)
			{
				System.out.println("Incorrect port number given, port numbers must be between 0 and 65535.\n");
				System.exit(1);
			}

		}

		catch (Exception e)
		{
			System.out.println("usage for chat application: -j  <port> <ip> | -n <port> ");
		}

		// command line args pass up to here, begin join/create chat
		if (isStartingNew)
		{
			startChat( portNum);
		}
		else
		{
			joinChat(ipAddress, portNum);
		}

		// set up loop to be taking in messages
		// CURRENTLY TEST LOOP
		Scanner scanner = new Scanner(System.in);
		while(true)
		{
			String input = scanner.nextLine();
			if(input.equals("exit"))
			{
				break;
			}
			System.out.println(input);
		}
		scanner.close();

	}
}
