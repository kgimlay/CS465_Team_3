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
public class ResponseMessage extends Message
{
    MessageType msgType;
    int returnIntVal;
    String message;
    
    public ResponseMessage(MessageType msgType)
    {
        this.msgType = msgType;
        this.message = "";
    }
    
    public ResponseMessage(MessageType msgType, String message)
    {
        this.msgType = msgType;
        this.message = message;
    }
    
    public ResponseMessage(MessageType msgType, int value)
    {
        this.msgType = msgType;
        this.returnIntVal = value;
        this.message = "";
    }
    
    @Override
    public String toString()
    {
        return "Response type: " + msgType + " value: " + returnIntVal 
                + " message: " + message;
    }
}
