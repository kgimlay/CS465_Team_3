/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver;

  /**
    * Account will be manipulated by Transactions and managed by AccountManager.
    * An Account object holds within it a balance and account number.
    */
  public class Account
  {  
      // Account object specific values
      int accountNum;
      int balance;
      
     /** Create a new Account object. 
     * 
     * @param accountNum - The account's number, aka its ID.
     * @param balanceAmount - What would be the account's initial balance.
     */
      Account(int accountNum, int balanceAmount)
      {
          // Initialize Account fields
          
          // set account number
          this.accountNum = accountNum;
          // Set the configurable balance
          this.balance = balanceAmount;
      }
      
      /** Gets the account's current balance.
      * 
      * @return int - The account's balance
      */
      public int getBalance()
      {
          return balance;
      }
      
      /** Update's the account's balance with the new balance specified.
      * 
      * @param newBalance - The account's new balance to be set
      */
      public void setBalance(int newBalance)
      {
          balance = newBalance;
      }
  }
  