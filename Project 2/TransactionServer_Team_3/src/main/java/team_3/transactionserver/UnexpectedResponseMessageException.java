/**
 * @author Kevin Imlay
 * @date 3/24/21
 */
package team_3.transactionserver;

/**
 * 
 */
public class UnexpectedResponseMessageException extends Exception
{
    public UnexpectedResponseMessageException(String message)
    {
        super(message);
    }
}
