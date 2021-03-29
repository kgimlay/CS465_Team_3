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
     
    /** Create a new ResponseMessage object.
     * 
     * @param msgType - specified response message type
     */
    public ResponseMessage(MessageType msgType)
    {
        this.msgType = msgType;
        this.message = "";
    }

    /** Create a new ResponseMessage object.
     * 
     * @param msgType - specified response message type
     * @param message - a specified message to send as a response
     */    
    public ResponseMessage(MessageType msgType, String message)
    {
        this.msgType = msgType;
        this.message = message;
    }

    /** Create a new ResponseMessage object.
     * 
     * @param msgType - specified response message type
     * @param value - the return integer value 
     */    
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
