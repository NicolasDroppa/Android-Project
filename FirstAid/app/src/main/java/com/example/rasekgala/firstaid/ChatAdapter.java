package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rasekgala on 2016/09/23.
 */
public class ChatAdapter extends BaseAdapter
{
    //layout inflater
    private static LayoutInflater inflater = null;
    public static ArrayList<ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> list) {

        //instantiate the inflater and the list
        chatMessageList = list;
        inflater = (LayoutInflater) activity .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return chatMessageList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //view object
        View vi;
        HolderView holder =null;

        //test if the content view is null before inflating
        if (convertView == null) {

            holder = new HolderView();

            //set the inflater to the view
            vi = inflater.inflate(R.layout.single_rows, null);

            //get the message textview from the view
            holder.message = (TextView) vi.findViewById(R.id.text_view_msg);

            //get layout of the view so that we set position of text based on doctor and patient
            holder.layout = (LinearLayout) vi.findViewById(R.id.bubble_layout);
            holder.parent_layout = (LinearLayout) vi.findViewById(R.id.bubble_layout_parent);


            //set the tag to the view
            vi.setTag(holder);
        }else {

            //else the contentview is not null, get the tag from the view.
            vi = convertView;
            holder = (HolderView) vi.getTag();
        }

        //create the Chatmessage and initialize it with the item from the list
        ChatMessage message = (ChatMessage) chatMessageList.get(position);

        //set the message to the textview
        holder.message.setText(message.message);
        holder.message.setTextColor(Color.BLACK);

        // if message is mine then align to right
        if (message.getSender().equals("owner")) {

            holder.layout.setBackgroundResource(R.drawable.bubble2);
            holder.parent_layout.setGravity(Gravity.END);
            holder.message.setTextColor(Color.MAGENTA);

        } else{

            // If not mine then align to left
            holder.layout.setBackgroundResource(R.drawable.bubble1);
            holder.parent_layout.setGravity(Gravity.LEFT);
            holder.message.setTextColor(Color.BLACK);
        }

        //return the view

        return vi;
    }

    /*
    *This method add the Chat Message to the arraylist
    * receive the ChatMessage object
     */
    public void add(ChatMessage object) {
        chatMessageList.add(object);
    }


    /*
    *This Holder View object is used to hold the views
     */
   class HolderView
    {
        //create the views needed to our getView method
        LinearLayout layout;
        TextView message;
        LinearLayout parent_layout;
    }
}


