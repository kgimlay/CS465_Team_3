/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  import java.util.*;
  import java.util.ArrayList;

  public class Lock
  {

      // List of transactions who hold a lock
      // if there are more than 1, it could only be read lock
      Vector holders;
      // List of transactions who need a lock
      Vector requestors;
      // types of locks a transaction can have
      enum LockType{READ, WRITE, NONE};
      LockType lockType;
      // The account the lock is associated with
      int accountNum;
  
      public synchronized void acquire(Transaction transaction, LockType aLockType)
      {
          // if lock holders is empty, current trans only one in holders,
          // or if lock type is read then all of that is not conflicting
          // while(/*another transaction holds the lock in conflicting mode*/)
          while((!(holders.isEmpty()) && !(aLockType == LockType.READ))
                 || !((holders.size() == 1) && (holders.contains(transaction))))
          {
              try
              {
                  // add trans to list of requestors
                  requestors.addElement(transaction);
                  // while in conflict, wait
                  wait();
                  // when we exit wait, remove trans from list of requestors
                  requestors.removeElement(transaction);
              }
              catch(InterruptedException e)
              {
                  //probably change
                  System.out.println("InterruptedException ocurred.");
              }
          }
  
          /*no TIDs hold lock*/
          if(holders.isEmpty())
          {
              holders.addElement(transaction);
              lockType = aLockType;
          }
          
          /*another transaction holds the read lock, share it*/
          else if(!(holders.isEmpty()) && aLockType == LockType.READ)
          {
              /*this transaction not a holder*/
              if(!(holders.contains(transaction)))
              {
                  holders.addElement(transaction);
              }
          }
          
          /*this transaction is a holder but needs a more exclusive lock*/
          else if((holders.contains(transaction) && aLockType == LockType.WRITE)
                                               && holders.isEmpty())
          {
              this.promote();
          }
      }

      // this looked like it was a method for a class named LockType based on
      // the pseudocode. I just made it here since it should work the same.
      public void promote()
      {
        // upgrade lock type on current object
        // read to write
        if(this.lockType == LockType.READ)
        {
            this.lockType = LockType.WRITE;
        }
      }
  
      public synchronized void release(Transaction transaction)
      {
          // remove this holder
          holders.removeElement(transaction);
          // set locktype to none
          this.lockType = LockType.NONE;
          
          notifyAll();
      }
  }