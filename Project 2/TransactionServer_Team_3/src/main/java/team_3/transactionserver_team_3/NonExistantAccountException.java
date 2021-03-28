/**
 * 
 */
package team_3.transactionserver_team_3;

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
