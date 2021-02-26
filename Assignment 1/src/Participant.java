/** @class Participant
*  @authors Yasmin Vega
*  @date 2-20-21
*/

import java.net.InetAddress;

/** @brief Participant stores the public name, port, and ip address of a single
*  connection within the chat topology. Used to form the participant list for
*  sending messages to all of the nodes in the topology.
*/
public class Participant
{
    /** @brief String representation of the public chat name to display.
    */
    String name;

    /** @brief Port to connect to for sending messages.
    */
    int port;

    /** @IP address to connect to for sending messages in the form of an
    *  InetAddress object.
    */
    InetAddress ip;

    /** @brief Constructor.
    *  @param userName - String of the public user name for the connection.
    *  @param ipAddress - InetAddress object for the IP address to send messages
    *  to.
    *  @param portNum - Integer port number for sending messages to.
    */
    public Participant(String userName, InetAddress ipAddress,  int portNum)
    {
        name = userName;
        ip = ipAddress;
        port = portNum;
    }
}
