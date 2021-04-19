package appserver.job.impl;

import appserver.job.Tool;
import java.lang.ClassCastException;

/**
 * Fibonacci tool to recursively calculate nth Fibonacci number
 *
 * @author matt
 */
public class Fibonacci implements Tool {

    @Override
    public Object go(Object number) {
//  attempt to calculate nth Fibonacci number
        try {
            return Fibonacci((Integer) number);
        } catch (ClassCastException castEx) {
            System.out.println("Error casting object to int" + castEx);
            return -1;
        }
    }

//    recursive function to calculate the nth fibonacci number whose sum is 
//    the preceding 2 numbers. calculates down to the base case
    public int Fibonacci(int number) {
        if (number == 0) {
            return 0;
        } else if (number == 1) {
            return 1;
        } else {
            return Fibonacci(number - 1) + Fibonacci(number - 2);
        }
    }
}
