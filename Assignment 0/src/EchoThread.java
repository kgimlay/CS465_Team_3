/* Authors: Randy Duerinck, Kevin Imlay, Yasmin Vega, Matt Flanders
 * Course: CS465: Distributed Systems
 * Section: 1
 * Assignment Name: EchoServer
 * Last Modification Date: 1-22-21
 *
 * Purpose: Defines what the thread does. Implements Runnable for threading.
 * 	As characters are received, they are captured, filtered to only allow for
 *	alphabet letters (upper and lower case), and echos them back to the client.
 *	Additionally, a state machine is incorporated to keep track of the command
 *	to close the connection.
 */

// import of required libraries
import java.lang.Runnable;
import java.io.*;
import java.net.*;



public class EchoThread implements Runnable {
	// Class attributes
	private QuitStateMachine stateMachine;
	private Socket socket;
	DataInputStream fromClient;
	DataOutputStream toClient;



	/*
	 * Constructor for QuitStateMachine
	 */
	 public EchoThread( Socket socket ) {
		// new state machine
		stateMachine = new QuitStateMachine();
		// socket object
		this.socket = socket;

		// try to create data streams
		try {
			fromClient = new DataInputStream( socket.getInputStream() );
			toClient = new DataOutputStream( socket.getOutputStream() );
		}
		// error during creation of data streams
		catch( IOException ioE) {
			System.out.println( "An i/o exception has occured when creating either abstract"+
			"an input or output stream");
		}
	 }

	/*
	 * Interface Runnable's run method. Used in the creation of a new thread
	 * 	to handle a connection.
	 */
	public void run() {
		// initialize buffers for reading, echoing, and var for character
		// being looked at, server socket
		char charFromClient;
		boolean quitFlag = false;

		// print new connection was made
		System.out.println("New connection made!");

		// enter infinite loop (terminates with state machine on "quit")
		try {
			while (true) {
				// get character from input buffer
				try {
					charFromClient = (char)fromClient.readByte();
				}
				// catch end of file excetion if the end of the read buffer
				// is reached
				catch ( EOFException eofE ) {
					System.out.println("An end of file exception has occured"
					+ " while reading the input buffer!");
					break;
				}

   				// check if its an alphabet character
				if ( Character.isLetter( charFromClient ) ) {
					// buffer to send back to client
					toClient.write( charFromClient );
					// send
					toClient.flush();

   					// update state machine with the current character
					quitFlag = stateMachine.updateState( charFromClient );
					// if state machine has accepted "quit", break loop
					if ( quitFlag ) {
						break;
					}
				}
			}
		}
		// catches io exceptions from write and flush commands
		// usually throws if the connection has been closed impropperly
		catch ( IOException ioE) {
			System.out.println("An i/o error has occured.");
		}

		// close connection, close socket
		try {
			socket.close();
		}
		catch ( IOException ioE) {
			System.out.println("An error occured closing the connection.");
		}
		// report to server terminal
		System.out.println("Connection closed!");
	}

}
