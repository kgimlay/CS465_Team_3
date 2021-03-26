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
public class ReadMessage extends Message
{
    int accountNum;
    Optional bal;
    
    public ReadMessage(int accountNum, Integer balance)
    {
        this.accountNum = accountNum;
        this.bal = Optional.ofNullable(balance);
    }
    
    @Override
    public String toString()
    {
        return "Read Message - Account: " + this.accountNum + 
                " Balance:" + this.bal;
    }
}
