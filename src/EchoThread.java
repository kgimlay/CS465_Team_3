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
	//private QuitStateMachine;

	/*
	 * Constructor
	 */
	 public EchoThread( Socket socket ) {
		 // new state machine
		stateMachine = new QuitStateMachine();
		 // socket object

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
		while (true) {
			// get character from input buffer

   				// check if its an alphabet character

   					// update state machine accordingly
					quitFlag = stateMachine.updateState( charFromClient );
					if (quitFlag) {
						break;
					}
					// echo back the character
		}

		// deinitialize
		System.out.println("Connection closed!");
	}

}
