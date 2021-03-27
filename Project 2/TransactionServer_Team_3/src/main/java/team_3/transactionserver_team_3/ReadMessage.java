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
    int bal;
    
    public ReadMessage(int accountNum)
    {
        this.accountNum = accountNum;
        this.bal = 0;
    }
    
    public ReadMessage(int accountNum, int balance)
    {
        this.accountNum = accountNum;
        this.bal = balance;
    }
    
    @Override
    public String toString()
    {
        return "Read Message - Account: " + this.accountNum + 
                " Balance:" + this.bal;
    }
}
