/**
 * 
 */
package team_3.transactionserver;

/**
 * @author kevinimlay
 */
public class NonExistantAccountException extends Exception
{
    /** When this exception is thrown, the specified message will print.
     * 
     * @param message - specified message relating to the exception
     */
    public NonExistantAccountException(String message)
    {
        super(message);
    }
}
