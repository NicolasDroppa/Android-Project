package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AllMessageActivity extends AppCompatActivity {

    private EditText msg_edittext;
    private String user1 = "owner";
    String user2 = "doctor";
    private Random random;
    public static ArrayList<ChatMessage> chatlist = new ArrayList<ChatMessage>();
    public static ChatAdapter chatAdapter;
    private MyDatabase database;
    ListView msgListView;
    private String getPath;

    //variables that helps to open gallery
    GalleryPhoto galleryPhoto;
    final int GALLERY_PHOTO = 1212;
    HashMap<String, String> queryValues;

    //Patient credentials to communicate with the server database
    private long IDNumber = 0;
    private long doctor_code = 0;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_message);

        //set activity title
        this.setTitle("Chat");

        //intantiate the random object
        random = new Random();
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        //find the edittext for message and listview
        msg_edittext = (EditText) findViewById(R.id.messageToserver);

        msgListView = (ListView) findViewById(R.id.listViewMessages);

        //find image button for sending text
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageBtn);

        //instantiate database object
        database= new MyDatabase(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        //get all the chat messages from the database
        chatlist = database.getAllChat();

        //instantiate chatAdapter for listview and pass context and arraylist
        chatAdapter = new ChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);//set adapter to listview

        //---Assign the ID and doctor code to the data members
        patient = database.getPatientDatails();
        //IDNumber = patient.getIdNumber();
        //doctor_code =patient.getDoctorCode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deleteAll) {
            //delete all messages from the database
            database.deleteChatMessage();

            //refresh the listview to apply changes immediately
            chatAdapter.notifyDataSetChanged();
            recreate();
        }else if(id == R.id.action_refresh)
        {
            //refresh the database for any new messages recieved
            recreate();

        }else if(id == R.id.action_aboutApp)
        {
            //start an activity that describe what the app is all about
            startActivity(new Intent(this,AboutApp.class));
            this.finish();

        }else if(id==R.id.action_image)
        {
            //here is when we attach the photo to be send
            try{
                //open and intent for result to call the gallery to browse the image
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_PHOTO);

            }catch (Exception e)
            {
                //any error handler
                Toast.makeText(getApplicationContext(),"Error, no such file",Toast.LENGTH_LONG).show();
            }
        }else if(id == R.id.action_news)
        {
            //nevigate to the news activity to read the health news
            startActivity(new Intent(this,NewsActivity.class));
            this.finish();
        }

        //chatAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            //here we get the photo path from the gallery
            Uri uri = data.getData();
            galleryPhoto.setPhotoUri(uri);//set the path to gallery
            getPath = galleryPhoto.getPath();//get the path as string
        }catch (Exception e)
        {
            //if any error occured return stop further processing
            return;
        }


        //when the result code matches

        if(requestCode == GALLERY_PHOTO)
        {
            try{
                //Bitmap bitmap = ImageLoader.init().from(getPath).requestSize(450,460).getBitmap();
                //ivImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(),"Image attached..",Toast.LENGTH_SHORT).show();

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error, please select the file again.", Toast.LENGTH_LONG).show();
            }

        }
    }

      /*
    *this method is responsible for sending messages and storing the to database
    * receive a view
     */

    public void sendTextMessage(View v) {
        //get the message from edittext
        String message = msg_edittext.getEditableText().toString();

        //test if the message is not empty
        if (!message.equalsIgnoreCase("")) {

            //create the ChatMessage object and set values
            final ChatMessage chatMessage = new ChatMessage(user1, user2 , message, "" + random.nextInt(1000), true);

            //set values to Chat Message object
            chatMessage.setMsgID();
            chatMessage.message = message;
            chatMessage.Date = CommonMethod.getCurrentDate();
            chatMessage.Time = CommonMethod.getCurrentTime();

            //store the message into the database
            database.storeChatMessage(chatMessage);

            //clear the message of  edittext
            msg_edittext.setText("");

            //add the message to the listview
            chatAdapter.add(chatMessage);

            //refresh the listview
            chatAdapter.notifyDataSetChanged();
        }
    }

    public void textMessage(View view)
    {
        //set onClick listener for sending msg
        switch (view.getId()) {
            case R.id.sendMessageBtn: sendTextMessage(view);
        }

    }

    /*
    *This method send the message to the server
    * first it confirm if the image is attached or else send the message
    * The app doesnt allow sending the image only without the message
     */

    public void send()
    {
        try{
            String ecodeImage ="";

            //create http client object
            AsyncHttpClient client = new AsyncHttpClient();

            //create the request parameter object
            RequestParams params = new RequestParams();

            //test if the image is null
            if(getPath != null)
            {
                //convert the image into Bitmap and resize
                Bitmap bitmap =  ImageLoader.init().from(getPath).requestSize(1024,1024).getBitmap();

                //encode the Bitmap image into String using the Base64 encoding
                ecodeImage = ImageBase64.encode(bitmap);
                //put the image into the Request parameters
                params.put("image",ecodeImage);
            }

            //put the message into the request parameters
            params.put("message", msg_edittext.getText().toString());

            // Make Http call to the server to upload the message

            client.post("http://192.168.42.29/doctor/upload.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    //on message success, meaning if the message is send
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(int statusCode, Throwable error, String content) {

                    //on message failure, this happen due to server down or network problems or even any other errors

                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_LONG).show();
                    if (statusCode == 404) {
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    } else if (statusCode == 500) {
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error occurred check network",Toast.LENGTH_LONG).show();
        }
    }

    public void sendSMS(String no, String msg)
    {
        try {

            //Getting intent and PendingIntent instance


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi, null);
                Toast.makeText(getApplicationContext(), "Message Sent successfully, doctor will reply soon!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }

    }
}
