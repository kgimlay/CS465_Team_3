/**
  * @author: Randy Duerinck
  * @date: March 14, 2021
  */
package team_3.transactionserver;

import java.util.Optional;

/**
 *
 * @author Randy
 * @author Kevin Imlay
 */
public class OpenTransMessage extends Message
{   
    public OpenTransMessage()
    {
        
    }
    
    @Override
    public String toString()
    {
        return "Open Transaction Message";
    }
}
