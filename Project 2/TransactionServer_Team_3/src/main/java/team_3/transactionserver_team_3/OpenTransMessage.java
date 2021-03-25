/**
  * @author: Randy Duerinck
  * @date: March 14, 2021
  */
package team_3.transactionserver_team_3;

import java.util.Optional;

/**
 *
 * @author Randy
 * @author Kevin Imlay
 */
public class OpenTransMessage extends Message
{
    // transaction ID for use in responding
    public Optional transID;
    
    public OpenTransMessage(Integer transactionID)
    {
        this.transID = Optional.ofNullable(transactionID);
    }
}
