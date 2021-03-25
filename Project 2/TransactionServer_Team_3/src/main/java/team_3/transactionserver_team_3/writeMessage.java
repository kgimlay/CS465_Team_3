package team_3.transactionserver_team_3;

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
    int accountNum;
    int value; //this is integer amount to write to the acct.
    
    public WriteMessage(int accountNum, int value)
    {
       this.accountNum = accountNum;
       this.value = value;
    }
}
