/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_3.transactionserver;

/**
 *
 * @author kevinimlay
 */

// An enum of the different types of messages
public enum MessageType {
    OPEN_TRANSACTION_MESSAGE, CLOSE_TRANSACTION_MESSAGE, WRITE_MESSAGE, 
    READ_MESSAGE, ERROR_MESSAGE
}
