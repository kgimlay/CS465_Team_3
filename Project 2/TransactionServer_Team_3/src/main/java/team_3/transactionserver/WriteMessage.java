package team_3.transactionserver;

/**
  * @author: Randy Duerinck
  * @date: March 14, 2021
  */

/**
 *
 * @author Randy
 * @author Kevin Imlay
 */
public class WriteMessage extends Message
{
    int accountNum; //account number 
    int value; //this is integer amount to write to the acct.
    
    public WriteMessage(int accountNum, int value)
    {
       this.accountNum = accountNum;
       this.value = value;
    }
    
    @Override
    public String toString()
    {
        return "Read Message - Account: " + this.accountNum + 
                " Value:" + this.value;
    }
}
