/**
 * 
 */
package team_3.transactionserver_team_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author kevinimlay
 */
public class DummyClientForTestingPurposes
{
    static ObjectInputStream in;
    static ObjectOutputStream out;
    
    private static Message sendAndGet(Message message) throws IOException, 
            ClassNotFoundException
    {
        out.writeObject(message);
        return (Message)in.readObject();
    }
    
    public static void main(String args[]) throws IOException, 
            ClassNotFoundException
    {
        Socket connection = new Socket(args[0], Integer.parseInt(args[1]));
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        String userInput;
        System.out.print("Connected\n\n");
        
        while (true)
        {
            // get input for what message to send
            userInput = scanner.nextLine();
            
            // send appropriate message or print error message
            // if appropriate message, wait for response and print message
            switch (userInput) {
                case "open":
                    System.out.println((ResponseMessage)sendAndGet(new OpenTransMessage()));
                    break;
                case "close":
                    System.out.println((ResponseMessage)sendAndGet(new CloseTransMessage()));
                    break;
                case "read":
                    System.out.println((ResponseMessage)sendAndGet(new ReadMessage(0)));
                    break;
                case "write":
                    System.out.println((ResponseMessage)sendAndGet(new WriteMessage(0,0)));
                    break;
                default:
                    System.out.println(userInput + " is not a message type!");
                    break;
            }
        }
    }
}
