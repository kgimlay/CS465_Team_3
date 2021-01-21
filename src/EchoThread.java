/*
 *
 */

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
	BufferedReader fromClient;
	PrintWriter toClient;

	/*
	 * Constructor
	 */
	 public EchoThread( Socket socket ) {
		 // new state machine
		stateMachine = new QuitStateMachine();
		 // socket object
		 this.socket = socket;
		 try{
		 fromClient = new BufferedReader( new InputStreamReader( socket.getInputStream()));
		 toClient = new PrintWriter( socket.getOutputStream(), true);
	 	 }
		 catch( IOException ioE)
		 {
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
		char charFromClient = '\0'; // TODO: remove initialization
		boolean quitFlag = false;

		// print new connection was made
		System.out.println("New connection made!");

		// enter infinite loop (terminates with state machine on "quit")
		// TODO: put in try to catch IOException if connection is closed improperly
		try{
			while (true) {
				// get character from input buffer
						charFromClient = (char)fromClient.read();
	   				// check if its an alphabet character
						if ( Character.isLetter( charFromClient))
						{
		   					// update state machine accordingly
							quitFlag = stateMachine.updateState( charFromClient );

							if (quitFlag) {
								break;
							}
							// echo back the character
							toClient.print( charFromClient);
							toClient.flush();
					}
			}
		}
		catch( IOException ioE)
		{
			System.out.println("An i/o error has occured.");
		}

		// deinitialize
		System.out.println("Connection closed!");
		try{
		socket.close();
		}
		catch( IOException ioE)
		{
			System.out.println( "An error occured closing the connection.");
		}
	}

}
