/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver;

  import java.util.ArrayList;
  
  /**
    * Implements high-level operations (read, write) on Account objects.
    * need to be calling on Lock Manager to try to set a read lock before
      it reads, and will try to set a write lock before it writes
    */
  public class AccountManager
  {
      // manages the list of accounts that will be created
      ArrayList<Account> accounts = new ArrayList<Account>();

      int numOfAccounts;
      int initialBalance;

      // logging strings
      private final String locStr = "AccountManager";
      private final String readStr = "READ ACCOUNT BALANCE";
      private final String writeStr = "WRITE ACCOUNT BALANCE";

     /** Create a new AccountManager object. 
     * 
     * @param numAccounts - number of accounts objects to create
     * @param initBalance - the initial balance of all account objects created
     */
      AccountManager(int numAccounts, int initBalance)
      {
          // initialize fields
          numOfAccounts = numAccounts;
          initialBalance = initBalance;
          
          // create number of accounts specified
          for(int num = 0; num < numOfAccounts; num++)
          {
              // add newly created account to accounts array list @ num index
              accounts.add(num, new Account(num, initialBalance));
          }
          // log number of accounts created by AccountManager
          System.out.println("Created " + numOfAccounts + " accounts.");
          
      }
      
     /** Looks for the specified a account using the account number by looping
     * through all entries in the accounts arraylist.
     *
     * @param accountNum - Number of the account to look for.
     * @return Account - The account object that was found.
     * @return null - The account was not found
     * 
     * @throws team_3.transactionserver.NonExistantAccountException
     */
      Account getAccount(int accountNum) throws NonExistantAccountException
      {
          // loop over accounts arraylist to find account w/ account num
          for(int index = 0; index < accounts.size(); index++)
          {
              if(accounts.get(index).accountNum == accountNum)
              {
                  return accounts.get(index);
              }
          }

          // account not found, throw exception
          throw new NonExistantAccountException("");
      }
     /** Looks for the account associated with the specified account number,
      * then attempts to set a read lock on that account. The account balance
      * will only be returned if we come back from that attempt after waiting
      * and/or there was no deadlock
     *
     * @param accountNum - Number of the account whose balance we want to read
     * @param transaction - The transaction that will hold the read lock
     *                      for the account
     * @return int - The account's balance
     */
      int read(int accountNum, Transaction transaction)
              throws NonExistantAccountException
      {
          // loop through accounts to find account associated w/ the accoutNum
          Account account = getAccount(accountNum);
          // try to set a reading lock
          TransactionServer.lockManager.lock(account, transaction, Lock.LockType.READ);
          // if successful (after waiting or no deadlock), return account's balance
          transaction.log(locStr, readStr + " Account #" + accountNum
            + " | Balance read $" + account.balance);
          return account.balance;
      }
      
     /** Looks for the account associated with the specified account number,
      * then attempts to set a write lock on that account. The account balance
      * will only be updated if we come back from that attempt after waiting
      * and/or there was no deadlock
     *
     * @param accountNum - Number of the account whose balance we want to update
     * @param transaction - The transaction that will hold the write lock
     *                      for the account
     * @param balance - The account's new balance
     */
      void write(int accountNum, Transaction transaction, int balance)
              throws NonExistantAccountException
      {
          // loop through accounts to find account associated w/ the accountNum
          Account account = getAccount(accountNum);
          // try to set a writing lock
          TransactionServer.lockManager.lock(account, transaction, Lock.LockType.WRITE);
          // update balance if successful (after waiting or no deadlock)
          account.setBalance(balance);
          transaction.log(locStr, writeStr + " Account #" + accountNum
            + " | Balance wrote $" + account.balance);
      }
  }
  