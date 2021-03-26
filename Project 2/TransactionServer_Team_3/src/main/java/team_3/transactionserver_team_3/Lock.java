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
      ArrayList<Transaction> holders;
      // List of transactions who need a lock
      ArrayList<Transaction> requestors;
      // types of locks a transaction can have
      enum LockType{READ, WRITE, NONE};
      LockType lockType;
      // The account the lock is associated with
      int accountNum;
  
      public synchronized void acquire(Transaction transaction, LockType aLockType)
      {
          // take off all true in conditional statements
          // they are there temporarily to minimize compil. errors
          
          // if lock holders is empty, current trans only one in holders,
          // or if lock type is read then all of that is not conflicting
          while(true/*another transaction holds the lock in conflicting mode*/)
          {
              // add trans to list of requestors
              
              try
              {
                  // when we exit wait, then remove trans from list of requestors
                  wait();
              }
              catch(InterruptedException e)
              {
                  
              }
              break; // to let compile
          }
  
          /*no TIDs hold lock*/
          if(holders.isEmpty())
          {
              holders.add(transaction);
              lockType = aLockType;
          }
          else if(true/*another transaction holds the lock, share it*/)
          {
              if(true/*this transaction not a holder*/)
              {
                  holders.add(transaction);
              }
          }
          else if(true/*this transaction is a holder but needs a more exclusive lock*/)
          {
              this.promote();
          }
      }

      public void promote()
      {
        // upgrade lock type on current object
        // read to write

      }
  
      public synchronized void release(Transaction transaction)
      {
          // remove this holder
          holders.remove(transaction);
          // set locktype to none
          notifyAll();
      }
  }