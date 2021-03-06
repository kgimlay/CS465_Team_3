/** @class ChatNode
*  @authors Matt Flanders, Yasmin Vega, Kevin Imlay
*  @date 2-22-21 and 2-25-21
*/

import java.util.ArrayList;
import java.util.Scanner;
import javax.print.event.PrintEvent;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.lang.Thread;
import java.io.*;

/** @brief Main class for peer to peer chat application. Implements the
*  top-level logic of the application: starting a chat, joining a chat, leaving
*  a chat, and sending a message. Also includes a list of participants
*/
public class ChatNode
{
   /** @brief ReceiveManager to handle incomming messages.
   */
   private static Thread receiveManagerThread;

   /** @brief SendThread to send outgoing messages.
   */
   private static Thread sendManagerThread;

   /** @brief List of participants to send messages to. Excludes one's self.
   */
   private static ArrayList<Participant> participantList;

   /** @brief Self as a participant. This is kept separate from the participant
   * list to prevent sending to self.
   */
   private static Participant selfParticipant;

   /** @brief ServerSocket for spawning Sockets for connections.
   */
   private static ServerSocket serverSocket;

   /** @brief Initialize the attribute needed for operation.
   *  @param username - The user's chosen display name.
   *  @param portNum - Integer port number for opening connections on,
   *  between 0 and 65535 inclusive.
   */
   private static void initSelf(String username, int portNum )
   {
      // initialize the participant list
      participantList = new ArrayList<Participant>();

      // Note that sendManager is initialized for every message sent, therefore
      // we don't initialize it here.

      // Initialize serverSocket
      try
      {
         serverSocket = new ServerSocket(portNum, -1,
                                          InetAddress.getByName("localhost"));
      } catch (IOException ioE) {
         System.out.println("An error occured while opening the socket! "
                              + ioE);
			System.exit(1);
      }

      // initialize one's self as a participant
      selfParticipant = new Participant(username,
                                          serverSocket.getInetAddress(),
                                          serverSocket.getLocalPort() );

      // start receiving incoming connection requests
      receiveManagerThread = new Thread(new ReceiveManager(serverSocket,
                                                            participantList,
                                                            selfParticipant));
      receiveManagerThread.start();
   }

   /** @brief Starts a new chat topology with just the one node (self).
   *  @param username - The user's chosen display name.
   *  @param selfPort - Integer port number for opening connections on,
   *  between 0 and 65535 inclusive.
   */
   private static void startChat(String username, int selfPort)
   {
      // initialize the attributes needed for operation (username + port #)
      initSelf( username, selfPort );
   }

   /** @brief Joins an already existing chat topology.
   *  @param username - The user's chosen display name.
   *  @param selfPort - Integer port number for opening connections on,
   *  between 0 and 65535 inclusive.
   *  @param ip - InetAddress of a node known to be in the chat topology to
   *  to connect to.
   *  @param joinPort - Integer port number of the known node, between 0 and
   *  65535 inclusive.
   */
   private static void joinChat(String username, int selfPort, InetAddress ip,
                                 int joinPort)
   {
      // initialize the attributes needed for operation (username + port #)
      initSelf( username, selfPort );

      // create a join message to join the existing chat
      // create a participant of the node to connect to
      Message joinRequest = new JoinMessage(selfParticipant.name,
                                             selfParticipant.port, null);
      ArrayList<Participant> joinRecipient = new ArrayList<Participant>();
      joinRecipient.add(new Participant("Chat", ip, joinPort));

      // send request to connect to a known node already in the chat
      sendManagerThread = new Thread(new SendThread(joinRequest,
                                                      joinRecipient));
      sendManagerThread.start();
   }

   /** @brief Sends a message to the other participants in the chat. Creates a
   *  ChatMessage object and sends to a SendThread to be sent in the topology.
   *  @param message - String of the message content to be sent and displayed
   *  on the receivers' message log.
   */
   private static void sendMessage(String message)
   {
      // create a message to send
      ChatMessage sendMessage = new ChatMessage(selfParticipant.name,
                                                selfParticipant.port, message);
      // pass the created message and participant list to the send manager
      sendManagerThread = new Thread(new SendThread(sendMessage,
                                                      participantList));
      // start the thread in order to send the message to all participants
      sendManagerThread.start();

   }

   /** @brief Leaves the chat. Gracefully disconnects from all members of the
   *  topology by sending them LeaveMessage objects so they can remove the
   *  sending node from their participant lists.
   */
   private static void leaveChat()
   {
      // create a leave message to signal departure from the chat
      LeaveMessage leaveMessage = new LeaveMessage(selfParticipant.name,
                                                      selfParticipant.port);
      // pass the created leave message and participant list to the send manager
      sendManagerThread = new Thread(new SendThread(leaveMessage,
                                                      participantList));
      // start the thread in order to have all participants remove the departing
      // participant from their participant lists
      sendManagerThread.start();
   }

   /** @brief Main entrance to program.
   *  Parses the command line arguments and starts a new chat or joins the
   *  existing chat, whichever is specified. Then falls into a loop for
   *  accepting user input from the command line as messages to send or commands
   *  such as to leave the chat.
   *  @param args - Array of String objects passed into the program from the
   *  command line.
   */
   public static void main(String args[])
   {
      /** Command line arguments are structured as such:
      *  -n <port to open>  |
      *  -j <port to open> <ip to connect to> <port to connect to>
      */

      int openPort = 0;          // port to open your own connection on
      int joinPort = 0;          // port to connect to for joining
      InetAddress joinIp = null; // IP address to connect to for joining
      boolean isJoining = false; // default to starting a new chat

      // check for correct number of parameters and
      // check for correct flags
      if (!(args.length == 2 && args[0].equals("-n"))
         && !(args.length == 4 && args[0].equals("-j") ))
      {
         System.out.println("\nUsage: -n <port to open>  | -j <port to open> "
            + "<ip to connect to> <port to connect to>\n");
         System.exit(1);
      }
      // flags are correct, good to get next arguments
      try {
         // grab the opening port number
         openPort = Integer.parseInt(args[1]);
         // report if port out of range
         if (openPort < 0 || openPort > 65535)
         {
            System.out.println("Opening port must be between 0 and 65535 "
               + "inclusive");
            System.exit(1);
         }
      }
      // if port number isn't formated as expected, report the error
      catch(NumberFormatException nfException) {
         System.out.println("Error parsing open port to an integer");
         System.exit(1);
      }

      if (args[0].equals("-j"))
      {
         // set flag to join
         isJoining = true;

         // catch exceptions for parsing port to an integer and
         // from trying to make an InetAddress object on the IP given
         try {
            // grab ip address given
            joinIp = InetAddress.getByName(args[2]);
            // grab port number given
            joinPort = Integer.parseInt(args[3]);
            // report if port out of range
            if (joinPort < 0 || joinPort > 65535)
            {
               System.out.println("Joining port must be between 0 and 65535 "
                  + "inclusive");
               System.exit(1);
            }
         }
         // if port number isn't formated as expected, report the error
         catch(NumberFormatException nfException) {
            System.out.println("Error parsing connecting port to an integer");
            System.exit(1);
         }
         // the IP address of a host could not be determined, report the error
         catch(UnknownHostException uhException) {
            System.out.println("Error parsing IP address");
            System.exit(1);
         }
      }

      // pretty print the chat header
      ChatPrettyPrinter.ppChatStart();

      // Create scanner object to take user input
      Scanner getInput = new Scanner(System.in);
      // var to hold chosen username
      String username;
      // Prompt user to enter a username
      System.out.print("###  Chat Name: ");
      // Grab user input and store into var username
      username = getInput.nextLine();

      // start a new chat or join a chat, whichever was specified in command
      // line arguments
      if (isJoining)
      {
         // join a chat
         joinChat(username, openPort, joinIp, joinPort);
      }
      else
      {
         // start a chat
         startChat(username, openPort);
      }

      // report chat started and where
      ChatPrettyPrinter.addChatInfo(serverSocket.getInetAddress(), openPort);

      // set up loop to be taking in messages
      // Create scanner object to read input
      Scanner scanner = new Scanner(System.in);

      while(true)
      {
         // read input
         String input = scanner.nextLine();
         // if input equals the exit command, leave chat
         if(input.equals("exit"))
         {
            leaveChat();
            break;
         }
         // if the input is not empty, send input as message
         if(!input.equals(""))
         {
            sendMessage(input);
         }
      }
      // close created scanner object
      scanner.close();
      try
      {
        // sleep to let all leave messages be sent before exiting
        Thread.sleep(500);
        System.exit(0);
      }
      // thread is interrupted, either before or during activity
      // report the error
      catch(InterruptedException interExcept)
      {
        System.out.println("Error interrupted exception: Main Thread "
         +"interrupted");
      }
   }
}
