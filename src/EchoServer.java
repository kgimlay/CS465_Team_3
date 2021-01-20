/*
 *
 */

import java.net.*;
import java.io.*;
import java.lang.Thread;

public class EchoServer {
	public static void main( String[] args ){
		// Check for correct parameters
	   		// if params correct, accept param as port number, else, exit
			// 0 < port number <= 65,535


								if (args.length != 1) {
									 System.err.println("Usage: java EchoServer <port number>");
									 System.exit(1);
								                      }
							  int portNum = 0;
                try {
                   portNum = Integer.parseInt(args[0]);
								 }
						    catch( NumberFormatException e ){
								   System.out.println("ERR: Port number not in right format");
									 System.exit(1);
								}

               if( portNum > 0 && portNum <= 65535){

								 System.out.println("Your number is in range");
                 // spawn a thread
								 Thread thread = new Thread( new EchoThread() );
								 thread.start();
							 }
							 else
							 {
								 System.out.println("Outta range");
							 }
                    //  serverSocket.accept()


                //   else{



        	// loop that creates threads upon acceptance of an open socket

	}
}
