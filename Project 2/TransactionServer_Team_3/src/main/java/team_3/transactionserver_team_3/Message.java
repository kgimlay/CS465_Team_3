/**
  * @author: Randy Duerinck
  * @date: March 14, 2021
  */

package team_3.transactionserver_team_3;

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
        return "Generic Message";
    }
}