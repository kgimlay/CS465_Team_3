/**
  * @author: Randy Duerinck
  * @date: March 14, 2021
  */

package team_3.transactionserver;

import java.io.Serializable;

/**
 *
 * @author Randy
 */
public abstract class Message implements Serializable
{
    @Override
    public String toString()
    {
        return "Generic Message"; //Dummy return 
    }
}