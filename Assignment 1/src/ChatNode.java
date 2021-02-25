/** @class ChatNode
*  @authors Matt Flanders (add more names here if you edit)
*  @date 2-22-21
*/

import java.util.ArrayList;
import java.util.Scanner;
import javax.print.event.PrintEvent;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.lang.Thread;

/** @brief Main class for peer to peer chat application. Implements the
*  top-level logic of the application: starting a chat, joining a chat, leaving
*  a chat, and sending a message. Also includes a list of participants
*/
public class ChatNode
{

   /** @brief ReceiveManager to handle incomming messages.
   */
   private ReceiveManager receiveManager;

   /** @brief SendThread to send outgoing messages.
   */
   private SendThread sendManager;

   /** @brief List of participants to send messages to.
   */
   private ArrayList<Participant> participantList;

   /** @brief ServerSocket for spawning Sockets from for connections.
   */
   private ServerSocket serverSocket;

   /** @brief Starts a new chat topology with just the one node (self).
   *  @param portNumber - Integer port number for opening connections on,
   *  between 0 and 65535 inclusive.
   */
   private static void startChat( int portNumber)
   {

   }

   /** @brief Joins an already existing chat topology.
   *  @param ip - InetAddress of a node known to be in the chat topology to
   *  to connect to.
   *  @param port - Integer port number of the known node, between 0 and 65535
   *  inclusive.
   */
   private static void joinChat(InetAddress ip, int port)
   {

   }

   /** @brief Sends a message to the other participants in the chat. Creates a
   *  ChatMessage object and sends to a SendThread to be sent in the topology.
   *  @param message - String of the message content to be sent and displayed
   *  on the receivers' message log.
   */
   private static void sendMessage(String message)
   {

   }

   /** @brief Leaves the chat. Gracefully disconnects from all members of the
   *  topology by sending them LeaveMessage objects so they can remove the
   *  sending node from their participant lists.
   */
   private static void leaveChat()
   {

   }

   /** @brief Add a participant to the participant list.
   *  @param participant - A Participant object to add to the list of
   *  participants.
   */
   private static void addParticipant(Participant participant)
   {

   }

   /** @brief Add a participant list. Used when joining a chat to take the
   *  contained participant list in the returned JoinMessage object and insert
   *  it as this node's participant list.
   *  @param participants - An ArrayList of Participants to set as the new
   *  participant list.
   */
   private static void addParticipants(ArrayList<Participant> participants)
   {

   }

   /** @brief Remove a participant from the participant list. Used when a
   *  LeaveMessage is received to remove the leaving node from the participant
   *  list.
   *  @param participant - Participant object of the participant to remove.
   */
   private static void removeParticipant(Participant participant)
   {

   }

   /** @brief Main entrance to program.
   *  Parses the command line arguments and starts a new chat or joins and
   *  existing chat, whichever is specified. Then falls into a loop for
   *  accepting user input from the command line as messages to send or commands
   *  such as to leave the chat.
   *  @param args - Array of String objects passed into the program from the
   *  command line.
   */
   public static void main(String args[])
   {
      // flag for starting new chat or joining an existing chat
      boolean isStartingNew = true;
      int portNum = 0;
      InetAddress ipAddress = null;

      // try to read command line args
      try
      {
         // get the port number as an int
         portNum = Integer.parseInt(args[1]);

         // if first argument passed is "-n" we are creating a new chat
         if (args[0].equals("-n"))
         {
            // set flag to starting a chat
            isStartingNew = true;
         }

         // if first argument is "-j" we join an existing chat
         else if(args[0].equals("-j"))
         {
         // set flag to join chat
            isStartingNew = false;

         // try and get ip address passed in args
         try
         {
            ipAddress = InetAddress.getByName(args[2]);
         }
         catch (UnknownHostException e)
         {
            System.out.println("Unkown host or malformed ip address.\n");
            System.exit(1);
         }

         }

         // something went wrong in the command line arg
         else
         {
            System.out.println("Command line args given to start chat incorrect.\n");
            System.out.println("Arg 0 : \"" + args[0] + "\" should be \"-n\" or \"-j\".");
            System.exit(1);
         }

         // test to see if a valid ip address has been given
         if (portNum < 0 || portNum > 65535)
         {
            System.out.println("Incorrect port number given, port numbers must be between 0 and 65535.\n");
            System.exit(1);
         }

      }

      catch (Exception e)
      {
      System.out.println("usage for chat application: -j  <port> <ip> | -n <port> ");
      }

      // command line args pass up to here, begin join/create chat
      if (isStartingNew)
      {
         startChat( portNum);
      }
      else
      {
         joinChat(ipAddress, portNum);
      }

      // set up loop to be taking in messages
      // CURRENTLY TEST LOOP
      Scanner scanner = new Scanner(System.in);
      while(true)
      {
         String input = scanner.nextLine();
         if(input.equals("exit"))
         {
            break;
         }
         System.out.println(input);
      }
      scanner.close();

   }
}
