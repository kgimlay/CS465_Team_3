/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21 
  */

  package team_3.transactionserver;
  import java.util.*;
  /**
    * 
    */
  public class LockManager
  {
      // maps accounts to Locks
      private Hashtable<Object, Lock> lockTable;
      private final String locStr = "LockManager";
      private final String lockStr = "LOCKING";
      private final String unlockStr = "UNLOCKING";
      
      public LockManager()
      {
          lockTable = new Hashtable<>();
      }
      
      // have to find lock that pertains to the account
      public void lock(Object object, Transaction transaction, Lock.LockType lockType)
      {
          // change this to the found lock
          Lock foundLock;
          synchronized(this)
          {
              // log
              transaction.log(locStr, lockStr 
                + " Account " + ((Account)object).accountNum + " type " + lockType);
              
              // find the lock that pertains to the account number(object)
              if(this.lockTable.containsKey(object))
              {
                  // once found, try to acquire that lock
                  foundLock = this.lockTable.get(object);
              }

              // if there isn't one, create it and add to the hashtable
              else
              {
                  Lock newLock = new Lock(object, lockType);
                  this.lockTable.put(object, newLock);
                  foundLock = newLock;
              }
          }
          
          // get lock
          foundLock.acquire(transaction, lockType);
          transaction.heldLocks.add(foundLock);
      }

      // synchronize this one because we want to remove all entries
      // all the transaction's locks are released at once
      public synchronized void unLock(Transaction transaction)
      {
          Enumeration e = this.lockTable.elements();
          while(e.hasMoreElements())
          {   
              Lock aLock = (Lock) (e.nextElement());
              /* transaction is a holder of this lock*/
              if(transaction.heldLocks.contains(aLock))
              {
                  aLock.release(transaction);
                  
                    // log
                    transaction.log(locStr, unlockStr);
              }
          }
      }
  }
  