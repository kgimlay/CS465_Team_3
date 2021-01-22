/*
 *
 */

import java.net.*;
import java.io.*;
import java.lang.Thread;

public class EchoServer {
	public static void main( String[] args ){
		// initialize variables
		int portNum = 0;
		ServerSocket serverSocket = null;

		// Check for correct number of parameters
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}

		// try parsing integer from argument
		try {
			portNum = Integer.parseInt(args[0]);
		}

		// not a valid number format
	    catch (NumberFormatException numberFormatE) {
			System.out.println("ERR: Port number not in right format");
			System.exit(1);
		}

		// try to create a new server socket
		try {
			serverSocket = new ServerSocket( portNum );
		}

		// throws an IOException if the socket cannot be opened
		catch (IOException ioE) {
			System.out.println("An error occured while opening the socket!");
			System.exit(1);
		}

		// throws and IllegalArgumentException if the port number is out
		// of range (0 <= port number <= 65535)
		catch (IllegalArgumentException illArgE) {
			System.out.println("An out of range port number was provided! "
			+ "Must be between 0 and 65,535 inclusive!");
			System.exit(1);
		}

		// loop that creates threads upon acceptance of an open socket
		while (true) {
			// spawn a thread
			try {
				// create thread (blocking on accept)
				Thread thread = new Thread(
					new EchoThread( serverSocket.accept() ) );
				// start thread
				thread.start();
			}

			// throws an IOException if an error occurs while waiting for
			// a connetion
			catch (IOException ioE) {
				System.out.println("Something went wrong while waiting "
				+ "for a connection!");
				System.exit(1);
			}
		}
	}
}
