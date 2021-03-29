/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_3.transactionserver;

/**
 * an exception for malformed command line arguments
 * @author kevinimlay
 */
public class MalformedCommandLineArgumentException extends Exception
{
    /** When this exception is thrown, the specified message will print.
     * 
     * @param message - specified message relating to the exception
     */
    public MalformedCommandLineArgumentException(String message)
    {
        super(message);
    }
}
