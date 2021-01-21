/*
 *
 */

public class QuitStateMachine {
	/*
	 * State will hold one of the following values at a time: 0, 1, 2, 3, 4.
	 * 0 is the beginning state. 4 is the final state.
	 */
	private int state;


	/*
	 * Constructor
	 * Initialize state machine to the beginning state
	 */
	public QuitStateMachine() {
		state = 0;
	}

	/*
	 * Update state
	 * General algorithm: all letters that are not 'q', 'u', 'i', 't' will result in
	   the 0 state. If a 'q' is detected, the state will be changed to 1. If the next
	   character is a 'u', the state will be changed to 2, and so on till the final
	   state of 4 is reached, at which point true will be returned.
	 */
	 public boolean updateState( char character ) {
		 if(character == 'q'){
			 state = 1;
		 }
		 else if (character == 'u' && state == 1){
			 state = 2;
		 }
		 else if( character == 'i' && state == 2){
			 state = 3;
		 }
		 else if(character == 't' && state == 3){
			 state = 4;
			 return true;
		 }
		 else{
			 state = 0;
		 }
		 return false;
	 }
}
