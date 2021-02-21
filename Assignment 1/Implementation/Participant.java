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
    public Participant(String userName, int portNum)
    {
        name = userName;
        ip = InetAddress.getLocalHost();
        port = portNum;
    }
}
