/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_3.transactionserver_team_3;

import java.net.Socket;

/**
 *
 * @author Randy Duerinck
 */
public class ClientWorker implements Runnable
{
    //this will be a thread that runs the actual transaction
    //constructor that can take in 2 acct numbers, amt to tranfer btw them, socketobject
    
    
    //A single one of these will tell the proxy what to do
    // we will make as many of these as we do clients
    
    public ClientWorker(int acctNum1, int acctNum2, int transamt, Socket socketobject)
    {
     int account1 = acctNum1;
     int account2 = acctNum2;
     int amount = transamt;
     Socket socketobj = socketobject;
     
    }
            
    @Override
    public void run()
    {
        
    }
    
    
    
    
}
