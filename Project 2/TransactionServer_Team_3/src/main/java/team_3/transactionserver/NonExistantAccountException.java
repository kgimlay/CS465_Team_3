/**
 * 
 */
package team_3.transactionserver;

/**
 * @author kevinimlay
 */
public class NonExistantAccountException extends Exception
{
    public NonExistantAccountException(String message)
    {
        super(message);
    }
}
