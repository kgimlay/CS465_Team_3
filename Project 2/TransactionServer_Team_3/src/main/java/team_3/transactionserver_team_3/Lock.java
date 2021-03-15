/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  import java.util.*;
  import java.util.ArrayList;

  public class Lock
  {

      // transaction ids as integers
      ArrayList<Integer> holders;
      ArrayList<Integer> requestors;
      // types of locks a transaction can have
      enum LockType{READ, WRITE, NONE};
      LockType lockType;
      int accountNum;
  
      public synchronized void acquire(int transID, LockType aLockType)
      {
          // take off all true in conditional statements
          // they are there temporarily to minimize compil. errors
          while(true/*another transaction holds the lock in conflicting mode*/)
          {
              try
              {
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
              holders.add(transID);
              lockType = aLockType;
          }
          else if(true/*another transaction holds the lock, share it*/)
          {
              if(true/*this transaction not a holder*/)
              {
                  holders.add(transID);
              }
          }
          else if(true/*this transaction is a holder but needs a more exclusive lock*/)
          {
              this.promote();
          }
      }

      public void promote()
      {
        // upgrade lock type in current object
        // read to write

      }
  
      public synchronized void release(int transID)
      {
          // remove this holder
          holders.remove(transID);
          // set locktype to none
          notifyAll();
      }
  }