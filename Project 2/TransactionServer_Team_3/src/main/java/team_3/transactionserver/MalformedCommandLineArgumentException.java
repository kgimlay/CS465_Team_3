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
    public MalformedCommandLineArgumentException(String message)
    {
        super(message);
    }
}
