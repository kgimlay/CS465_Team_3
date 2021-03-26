/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  import java.util.ArrayList;
  
  /**
    * Implements high-level operations (read, write) on Account objects.
    * need to be calling on Lock Manager to try to set a read lock before
      it reads, and will try to set a write lock before it writes
    */
  public class AccountManager
  {
      // manages the list of accounts that will be created
      ArrayList<Account> accounts;
      boolean lockingActive;
  
      AccountManager()
      {
          this.accounts = new ArrayList<Account>();
          // generate accounts?
      }
      
      Account getAccount(int accountNum)
      {
          for(int index = 0; index < accounts.size(); index++)
          {
              if(accounts.get(index).accountNum == accountNum)
              {
                  return accounts.get(index);
              }
          }
          return null;
      }
  
      int read(int accountNum, Transaction transaction)
      {
        // loop through accounts to find account associated w/ the accoutNum
        Account account = getAccount(accountNum);
        // try to set a reading lock
        TransactionServerMain.lockManager.lock(account, transaction, Lock.LockType.READ);
        // if successful (after waiting or no deadlock), return account's balance
        return account.balance;
      }
  
      void write(int accountNum, Transaction transaction, int balance)
      {
          // loop through accounts to find account associated w/ the accountNum
          Account account = getAccount(accountNum);
          // try to set a writing lock
          TransactionServerMain.lockManager.lock(account, transaction, Lock.LockType.WRITE);
          // update balance if successful (after waiting or no deadlock)
          account.setBalance(balance);
      }
  }
  