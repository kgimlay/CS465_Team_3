/**
 * @author Kevin Imlay
 * @date 3/24/21
 */
package team_3.transactionserver;

/** 
 * 
 */
public class NonMessageObjectRecievedException extends Exception
{
    public NonMessageObjectRecievedException(String message)
    {
        super(message);
    }
}
