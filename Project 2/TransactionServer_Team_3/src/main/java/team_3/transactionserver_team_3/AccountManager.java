/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  import java.util.ArrayList;
  
  /**
    * Implements high-level operations (read, write) on Account objects.
    */
  public class AccountManager
  {
      ArrayList<Account> accounts;
      // Need to be calling on Lock Manager to try to set a read lock before
      // it reads, and will try to set a write lock before it writes
      LockManager lockManager;
      boolean lockingActive;
  
      AccountManager()
      {
          this.accounts = new ArrayList<Account>();
          this.lockManager = new LockManager();
          
      }
  
      int read(int accountNum)
      {
          // loop through accounts to find account associated w/ the accoutNum
          // try to set a reading lock
          // if successful, return account's balance
          return 0;
      }
  
      void write(int accountNum, int amount)
      {
          // loop through accounts to find account associated w/ the accountNum
          // try to set a writing lock
          // if successful, read balance of account
          // update balance based on x amount of dollars
      }
  }
  