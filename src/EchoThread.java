/*
 *
 */

import java.lang.Runnable;
import java.io.*;


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
	 public EchoThread( /*something here, socket object?*/ ) {
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

		// print new connection was made
		System.out.println("New connection made!");

		// enter infinite loop (terminates with state machine on "quit")

			// get character from input buffer

   				// check if its an alphabet character

   					// update state machine accordingly
					System.out.println(stateMachine.updateState('q'));
					System.out.println(stateMachine.updateState('u'));
					System.out.println(stateMachine.updateState('i'));
					System.out.println(stateMachine.updateState('t'));
					// echo back the character

		// deinitialize?

	}

}
