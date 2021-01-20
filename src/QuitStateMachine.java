/*
 *
 */

public class QuitStateMachine {
	// class attributes
	private int state;


	/*
	 * Constructor
	 */
	public QuitStateMachine() {
		state = 0;

	}

	/*
	 * Update state
	 */
	 public boolean updateState( char character ) {
		 if(character == 'q' && state == 0){
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
