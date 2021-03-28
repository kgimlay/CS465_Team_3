/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver;

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
      Object account;
      
      public Lock(Object account, LockType lockType)
      {
          this.account = account;
          this.lockType = lockType;
          this.holders = new Vector();
          this.requestors = new Vector();
      }

      public boolean isHoldersNotEmpty()
      {
          return !(this.holders.isEmpty());
      }

      public boolean isLockTypeNotRead(LockType aLockType)
      {
          return !(aLockType == LockType.READ);
      }

      public boolean isTransNotAloneInHolders(Transaction transaction)
      {
          return  !((this.holders.size() == 1) && (this.holders.contains(transaction)));
      }

      public synchronized void acquire(Transaction transaction, LockType aLockType)
      {
          // if lock holders is empty, current trans only one in holders,
          // or if lock type is read then all of that is not conflicting
          // while(/*another transaction holds the lock in conflicting mode*/)
          while( isHoldersNotEmpty() || isLockTypeNotRead(aLockType)
                 || isTransNotAloneInHolders(transaction))
          {
              try
              {
                  // add trans to list of requestors
                  this.requestors.addElement(transaction);
                  // while in conflict, wait
                  wait();
                  // when we exit wait, remove trans from list of requestors
                  this.requestors.removeElement(transaction);
              }
              catch(InterruptedException e)
              {
                  //probably change
                  System.out.println("InterruptedException ocurred.");
              }
          }
  
          /*no TIDs hold lock*/
          if(this.holders.isEmpty())
          {
              this.holders.addElement(transaction);
              lockType = aLockType;
          }
          
          /*another transaction holds the read lock, share it*/
          else if(!(this.holders.isEmpty()) && aLockType == LockType.READ)
          {
              /*this transaction not a holder*/
              if(!(this.holders.contains(transaction)))
              {
                  this.holders.addElement(transaction);
              }
          }
          
          /*this transaction is a holder but needs a more exclusive lock*/
          else if((this.holders.contains(transaction) && aLockType == LockType.WRITE)
                                               && this.holders.isEmpty())
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
          this.holders.removeElement(transaction);
          // set locktype to none
          this.lockType = LockType.NONE;
          
          notifyAll();
      }
  }