/** @class ChatPrettyPrinter
*  @authors Kevin Imlay
*  @date 2-27-21
*/

import java.net.InetAddress;

/** @brief Prints content to the command line in a pretty and formatted manner
*  to make a nice user experience. For decorative purposes only, and doesn't
*  add any functionality to the chat program.
*/
public class ChatPrettyPrinter
{
   /** @brief Header string for printing
   */
   private static String chatHearderStr = ""
   +"##########################################################################"
   +"#####\n"
   +"###                          P2P Chat - CS365 Team 3                      "
   +"  ###\n"
   +"##########################################################################"
   +"#####\n";

   /** @brief Creates the chat stats and node info section.
   */
   private static String ChatInfoSectionStr = ""
   +"###  Chat Name: %s\n"
   +"###  IP: %s\n"
   +"###  Port: %d\n"
   +"##########################################################################"
   +"#####\n";

   /** @brief Received Message line header string.
   */
   private static String recMsgLineHeadStr = "  >> %s says: %s";

   /** @brief Has left the chat string.
   */
   private static String hasLeftChatStr = "%s has left the chat :-(";

   /** @brief Cannot be reached string.
   */
   private static String cannotBeReached = "%s cannot be reached.";

   /** @brief Primes the command line screen and prints a header
   */
   public static void ppChatStart()
   {
      // clear the terminal window ad put cursor at home
      System.out.print("\033[2J\033[H");

      // print header
      System.out.println(chatHearderStr);
   }

   /** @brief Clears the username line query line and puts chat stats at the top
   *  of the section, just under the header.
   */
   public static void addChatInfo(String username, InetAddress ip, int port)
   {
      // format string for printing
      String formattedStr = String.format(ChatInfoSectionStr, username,
                                          ip.toString(), port);

      // put cursor just below the chat header and print
      System.out.print("\033[4;0H");
      System.out.println(formattedStr);

      // move cursor to fill in ends of lines
      System.out.print("\033[4;77H###");  // line 1
      System.out.print("\033[5;77H###");  // line 2
      System.out.print("\033[6;77H###");  // line 3
      System.out.print("\033[9;0H");      // restore position
   }

   /** @brief Prints a message formatted.
   */
   public static void printReceivedMessage(String senderID, String message)
   {
      // format message line string
      String formattedStr = String.format(recMsgLineHeadStr, senderID, message);

      // move cursor, print message, then move cursor back
      System.out.println(formattedStr);
      //System.out.print("\033[u");
   }

   /** @brief Prints the has left chat string
   */
   public static void printHasLeft(String senderID)
   {
      // format and print
      String formattedStr = String.format(hasLeftChatStr, senderID);
      System.out.println(formattedStr);
   }

   /** @brief Prints the cannot be reached string
   */
   public static void printCannotBeReached(String senderID)
   {
      // format and print
      String formattedStr = String.format(cannotBeReached, senderID);
      System.out.println(formattedStr);
   }
}
