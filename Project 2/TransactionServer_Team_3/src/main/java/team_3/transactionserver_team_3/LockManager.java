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
      private Hashtable lockTable;
      
      // have to find lock that pertains to the account
      public void lock(Object object, Transaction transaction, Lock.LockType lockType)
      {
          // change this to the found lock instead of null
          Lock foundLock = null;
          synchronized(this)
          {
              // find the lock that pertains to the account (object)
              // once found, try to acquire that lock
              // if there isn't one, create it and add to the hashtable
          }
          foundLock.acquire(transaction, lockType);
      }

      // synchronize this one because we want to remove all entires
      // all the transaction's locks are realesed at once
      public synchronized void unLock(Transaction transaction)
      {
          Enumeration e = lockTable.elements();
          while(e.hasMoreElements())
          {
              Lock aLock = (Lock) (e.nextElement());
              // remember to take off true since this is a placeholder
              if(true/* trans is a holder of this lock*/)
              {
                  aLock.release(transaction);
              }
          }
      }
  }
  