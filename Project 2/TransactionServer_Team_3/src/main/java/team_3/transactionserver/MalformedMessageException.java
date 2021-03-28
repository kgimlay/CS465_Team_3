/**
 * @author Kevin Imlay
 * @date 3/24/21
 */
package team_3.transactionserver;

/**
 * 
 */
public class MalformedMessageException extends Exception
{
    public MalformedMessageException(String message)
    {
        super(message);
    }
}
