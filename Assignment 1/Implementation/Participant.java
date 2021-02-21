/**
*
*/
import java.net.InetAddress;

/**
*
*/
public class Participant
{
     /**
     *
     */
    String name;

    /**
    *
    */
    int port;

    /**
    *
    */
    InetAddress ip;

    /**
    *
    */
    public Participant(String userName, InetAddress ipAddress,  int portNum)
    {
        name = userName;
        ip = ipAddress;
        port = portNum;
    }
}
