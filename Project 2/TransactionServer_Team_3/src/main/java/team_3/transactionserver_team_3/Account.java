/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  /**
    * Account will be manipulated by Transactions and managed by AccountManager.
    * An Account object holds within it a balance and account number.
    */
  public class Account
  {
      // Accounts are assigned the value that counter currently holds
      static int counter = 0;
      
      // Account object specific values
      int accountNum;
      int balance;
  
      Account(int balanceAmount)
      {
          // Initialize Account fields
  
          // Set the configurable balance
          this.balance = balanceAmount;
          // Account's number will be initialized to current counter value
          this.accountNum = counter;
          // Advance current counter value for next Account creation
          counter ++;
      }
      
      public int getBalance()
      {
          return balance;
      }
      
      public void setBalance(int newBalance)
      {
          balance = newBalance;
      }
  }
  