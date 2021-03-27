/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_3.transactionserver_team_3;

/**
 *
 * @author kevinimlay
 */
public class ResponseMessage extends Message
{
    MessageType msgType;
    int returnIntVal;
    
    public ResponseMessage(MessageType msgType)
    {
        this.msgType = msgType;
    }
    
    public ResponseMessage(MessageType msgType, int value)
    {
        this.msgType = msgType;
        this.returnIntVal = value;
    }
}
