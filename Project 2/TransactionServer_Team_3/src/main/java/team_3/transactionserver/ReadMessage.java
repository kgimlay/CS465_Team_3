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
public class ReadMessage extends Message
{
    int accountNum; //account number
    int bal; //account balance
    
    /** Create a new ReadMessage object.
     * 
     * @param accountNum - specified account object's number ID
     */
    public ReadMessage(int accountNum)
    {
        this.accountNum = accountNum; 
        this.bal = 0;
    }

    /** Create a new ReadMessage object.
     * 
     * @param accountNum - specified account object's number ID
     * @param balance - the account object's balance
     */
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
