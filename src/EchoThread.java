/*Authors: Randy Duerinck, Kevin Imlay, Yasmin Vega, Matt Flanders
  Course: CS465: Distributed Systems
	Section: 1
	Assignment Name: EchoServer
	Last Modification Date: 1-22-21

	Purpose: Defines what the thread does, implements Runnable
	it recieves input string and calls to a character to update the stateMachine
 *
 */

// import of required libraries
import java.lang.Runnable;
import java.io.*;
import java.net.*;


/*
 *
 */
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
	 *
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
				catch ( EOFException eofE ) {
					System.out.println("An end of file exception has occured"
					+ " while reading the input buffer!");
					break;
				}

   				// check if its an alphabet character
					System.out.print(charFromClient);
				if ( Character.isLetter( charFromClient ) ) {
					// echo back the character
					toClient.write( charFromClient );
					// empty buffer stream
					toClient.flush();

   					// update state machine accordingly
					quitFlag = stateMachine.updateState( charFromClient );
					if (quitFlag) {
						break;
					}
				}
			}
		}
		catch ( IOException ioE) {
			System.out.println("An i/o error has occured.");
		}

		// deinitialize
		try {
			socket.close();
		}

		catch ( IOException ioE) {
			System.out.println( "An error occured closing the connection.");
		}

		System.out.println("Connection closed!");
	}

}
