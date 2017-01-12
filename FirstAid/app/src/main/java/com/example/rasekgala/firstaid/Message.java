package com.example.rasekgala.firstaid;

/**
 * Created by Rasekgala on 2016/09/17.
 */
public class Message
{
    private long message_id;
    private long respond_id;
    private String response;
    private String message;

    private String date;

    public Message()
    {

    }

    public Message(long message_id, String response, String date,String message, long respond_id) {
        this.message_id = message_id;
        this.response = response;

        this.date = date;
        this.message = message;
        this.respond_id = respond_id;
    }

    public long getRespond_id() {
        return respond_id;
    }

    public void setRespond_id(long respond_id) {
        this.respond_id = respond_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}
