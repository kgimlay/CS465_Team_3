/**
 * @author Kevin Imlay
 * @date 3/24/21
 */
package team_3.transactionserver;

/**
 *
 * @author kevinimlay
 */
public class CloseTransMessage extends Message
{
    public CloseTransMessage()
    {
        
    }
    
    @Override
    public String toString()
    {
        return "Close Transaction Message";
    }
}
