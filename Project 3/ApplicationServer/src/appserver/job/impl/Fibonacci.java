/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver.job.impl;

import java.lang.ClassCastException;

/**
 *
 * @author matt
 */
public class Fibonacci{
    
    public Object go( Object number)
    {
        try
        {
            return Fibonacci( (Integer) number );
        }
        catch( ClassCastException castEx )
        {
            System.out.println("Error casting object to int" + castEx);
            return -1;
        }
    }
    
    public int Fibonacci( int number )
    {
        if( number == 0 )
        {
            return 0;
        }
        else if( number == 1 )
        {
            return 1;
        }
        else
        {
            return Fibonacci(number-1) + Fibonacci(number-2);
        }
    }
}
