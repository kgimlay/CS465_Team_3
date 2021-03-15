/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21 
  */

  package team_3.transactionserver_team_3;
  import java.util.*;
  /**
    * 
    */
  public class LockManager
  {
      // maps accounts to Locks
      private Hashtable theLocks;
      
      // have to find lock that pertains to the account
      public void setLock(Account account, int transID, Lock.LockType lockType)
      {
          // change this to the found lock instead of null
          Lock foundLock = null;
          synchronized(this)
          {
              // find the lock associated with object
              // if there isn't one, create it and add to the hashtable
          }
          foundLock.acquire(transID, lockType);
      }

      // synchronize this one because we want to remove all entires
      public synchronized void unLock(int transID)
      {
          Enumeration e = theLocks.elements();
          while(e.hasMoreElements())
          {
              Lock aLock = (Lock) (e.nextElement());
              // remember to take off true since this is a placeholder
              if(true/* trans is a holder of this lock*/)
              {
                  aLock.release(transID);
              }
          }
      }
  }
  