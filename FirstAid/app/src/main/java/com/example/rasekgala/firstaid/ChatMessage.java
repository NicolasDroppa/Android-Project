package com.example.rasekgala.firstaid;

import java.util.Random;

/**
 * Created by Rasekgala on 2016/09/23.
 * This class is responsible for carrying messages to  database and getting them
 * we use it to transfer messages to sqlite database
 */
public class ChatMessage
{
    public String sender;
    public String receiver;
    public String message;
    public String Date, Time;
    public String msgid;
    public boolean isMine;// Did I send the message.

    public ChatMessage() {
    }


    public ChatMessage (String sender,String receiver,String message,String msgid,boolean isMine) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.msgid = msgid;
        this.isMine = isMine;
    }

    public void setMsgID() {
        msgid += "-" + String.format("%02d", new Random().nextInt(100));
        ;
    }

    //get the sender
    public String getSender() {
        return sender;
    }

    //get receiver
    public void setSender(String sender) {
        this.sender = sender;
    }

    //get the receiver
    public String getReceiver() {
        return receiver;
    }

    //set the receiver
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    //get the message
    public String getMessage() {
        return message;
    }

    //set message
    public void setMessage(String message) {
        this.message = message;
    }

    //get the date of the message sent
    public String getDate() {
        return Date;
    }

    //set the date
    public void setDate(String date) {
        Date = date;
    }

    //get the time the message was send
    public String getTime() {
        return Time;
    }

    //set the time
    public void setTime(String time) {
        Time = time;
    }

    //get message id
    public String getMsgid() {
        return msgid;
    }

    //set message id
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    //this method is used to determine if the message is for the owner or doctor
    public boolean isMine() {
        return isMine;
    }

    //set who owns the message
    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }
}
