package com.example.rasekgala.firstaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Rasekgala on 2016/09/17.
 */
public class MyDatabase extends SQLiteOpenHelper
{
    /*
    * Below are all databse fields name, table names and
    * database name and version*/

    private static final String DATAbASE_NAME ="doctorDBase";
    private static final int DATAbASE_VERSION =1;

    private static final String MESSAGE_ID ="message_id";
    private static final String PERSON_ID ="person_id";
    private static final String DOCTOR_CODE ="doctor_code";
    private static final String RESPOND ="response";
    private static final String MESSAGE ="message";
    private static final String IDNUMBER ="idNumber";
    private static final String RESPOND_ID ="response_id";
    //private static final String DATE ="date";

    private static final String TABLE_MESSAGE ="tblMessage";
    private static final String TABLE_RESPOND ="tblrespond";
    private static final String TABLE_ID ="tblpatient";
    private static final String TABLE_NEWS ="tblnews";

    private static final String BODY ="body";
    private static final String RECIEVER ="reciever";
    private static final String SENDER ="sender";
    private static final String DATE ="date";
    private static final String TIME ="time";
    private static final String MSGID ="msg_id";
    private static final String IS_MINE ="isMine";
    private static final String DOCTOR_PHONE ="doctorPhone";

    private static final String NEWS_ID ="news_id";
    private static final String NEWS_BODY ="news_body";
    private static final String TITLE ="title";
    private static final String REPORTER ="reporter";
    private static final String STATUS ="status";

    public MyDatabase(Context context) {
        super(context, DATAbASE_NAME, null, DATAbASE_VERSION);
    }

    /*
    *The following method is called always when the MyDatabase object is created
    *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //sql statements to create the database tables
        String sql2 = "CREATE TABLE "+TABLE_MESSAGE+"("+MESSAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+BODY+" TEXT NOT NULL, "+DATE+" TEXT NOT NULL,"+RECIEVER+" TEXT NOT NULL, "+SENDER+" TEXT NOT NULL, "+IS_MINE+" TEXT NOT NULL, "+MSGID+" TEXT NOT NULL, "+TIME+" TEXT NOT NULL)";
        String sql = "CREATE TABLE "+TABLE_RESPOND+"("+RESPOND_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+RESPOND+" TEXT NOT NULL, "+DATE+" TEXT NOT NULL)";
        String sql1 = "CREATE TABLE "+TABLE_ID+"("+PERSON_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+IDNUMBER+" INTEGER NOT NULL,"+DOCTOR_CODE+" INTEGER NOT NULL)";//, "+DOCTOR_PHONE+" TEXT NOT NULL
       String sql3 = "CREATE TABLE "+TABLE_NEWS+"("+NEWS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NEWS_BODY+" TEXT NOT NULL,"+TITLE+" TEXT NOT NULL, "+REPORTER+" TEXT NOT NULL, "+DATE+" TEXT NOT NULL,"+STATUS+" TEXT)";
        db.execSQL(sql3);
        db.execSQL(sql2);
        db.execSQL(sql1);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //upgrade database for any changes
        db.execSQL("DROP TABLE  IF EXISTS "+TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ID);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RESPOND);
       db.execSQL("DROP TABLE IF EXISTS "+TABLE_NEWS);
        onCreate(db);
    }

    /*
    *This method stores the messages in the database
    * receive the message object and pass to contentvalues
    * return the id of the row inserted
     */

    public long receiveMessage(Message message)
    {
        long id =0;
        SQLiteDatabase db = this.getWritableDatabase();//create instance of SQliteDatabase

        ContentValues cv = new ContentValues();
        cv.put(RESPOND, message.getResponse());//put respond values into the contentvalue object

        cv.put(DATE, message.getDate()); //put date into content value

        return db.insert(TABLE_RESPOND, null,cv);//insert the contet values into database using insert method
    }

    /*
    *This method stores the messages in the database
    * receive the message object
    * return the id of the row inserted
     */
    public long storeMessage(Message message)
    {

        //create the instance of SQliteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the Content Value object and put the values into the object
        ContentValues cv = new ContentValues();
        cv.put(MESSAGE, message.getMessage());
        cv.put(DATE, message.getDate());

        //insert the row using contentvalue object and return the id.
        return db.insert(TABLE_MESSAGE, null,cv);
    }

    /*
    *This method stores the chat messages in the database
    * receive the ChatMessage object
    * return the id of the row inserted
     */
    public long storeChatMessage(ChatMessage message)
    {

        //create instance of the SQLitedatabase object
        SQLiteDatabase db = this.getWritableDatabase();

        //create the content value object and put values
        ContentValues cv = new ContentValues();
        cv.put(BODY, message.message);
        cv.put(DATE, message.Date);
        cv.put(TIME, message.Time);
        cv.put(RECIEVER, message.receiver);
        cv.put(SENDER, message.sender);
        cv.put(IS_MINE, message.isMine);
        cv.put(MSGID, message.msgid);

        //insert the record and return the id.
        return db.insert(TABLE_MESSAGE, null,cv);
    }

    /*
    *This method stores the news sent by doctors in the database
    * receive the News object
    * return the id of the row inserted
     */
    public long insertNews(News news)
    {
        //create instance of the SQLitedatabase object
        SQLiteDatabase db = this.getWritableDatabase();

        //create the content value object and put values
        ContentValues cv = new ContentValues();

        cv.put(NEWS_BODY, news.getBody());
        cv.put(TITLE, news.getTitle());
        cv.put(REPORTER, news.getReporter());
        cv.put(DATE, news.getDate());
        cv.put(STATUS, news.getStatus());

        //insert the record and return the id.
        return db.insert(TABLE_NEWS,null,cv);
    }

    /*
    *This method get all news in the database.
    * return and araylist of all the news
     */
    public ArrayList<News> getAllNews()
    {
        //sql statement to query the database
        String sql = "SELECT * FROM "+TABLE_NEWS;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create a cursor to get the query record
        Cursor c = db.rawQuery(sql,null);

        ArrayList<News> list = new ArrayList<News>();

        //test if any row found
        if(c.moveToFirst())
        {
            //do loop into the cursor and store values into the news object
            do{
                News news = new News(c.getLong(c.getColumnIndex(NEWS_ID)), c.getString(c.getColumnIndex(NEWS_BODY)), c.getString(c.getColumnIndex(TITLE)), c.getString(c.getColumnIndex(REPORTER)), c.getString(c.getColumnIndex(DATE)), c.getString(c.getColumnIndex(STATUS)));
                list.add(news);//add news to the list

            }while(c.moveToNext());
        }

        //return the list
        return list;
    }

    /*
    * This method get the news object from database based on an ID
    * Receive the ID object
    * Return the News object
     */
    public News getNews(long id)
    {
        //initialize the news object
        News news = null;
        //sql query to get the record
        String sql = "SELECT * FROM "+TABLE_NEWS+" WHERE "+NEWS_ID+" =?";
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        //create an String array of arguments that will be passes to sql query
        String[] args = {String.valueOf(id)};

        //create a cursor to get the query record
        Cursor c = db.rawQuery(sql, args);

        //test if the row is found
        if(c.moveToFirst())
        {
            //store the values inside the news object
            news = new News(c.getLong(c.getColumnIndex(NEWS_ID)), c.getString(c.getColumnIndex(NEWS_BODY)), c.getString(c.getColumnIndex(TITLE)) , c.getString(c.getColumnIndex(REPORTER)), c.getString(c.getColumnIndex(DATE)), c.getString(c.getColumnIndex(STATUS)));

        }

        //return the news object
        return news;
    }

    public void updateNews(long newsId)
    {
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(newsId)};          
        ContentValues cv = new ContentValues();
        cv.put(STATUS, "read");

        db.update(TABLE_NEWS, cv, NEWS_ID +" =?", args);
    }

    /*
    * This method delete the news from the local database
     */
    public void deleteNews()
    {
        //sql statement to delete the records in the database
        String sql = "DELETE FROM "+TABLE_NEWS;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);//execute the sql query
    }

    /*
    *This method store patient id number and doctor code in the local database for
    * validation for communication
    * receive the object of Patient and return the ID of last record inserted.
     */
    public long storePatient(Patient patient)
    {
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the content values and put the values into it.
        ContentValues cv = new ContentValues();
        cv.put(IDNUMBER, patient.getIdNumber());
        cv.put(DOCTOR_CODE, patient.getDoctorCode());
       // cv.put(DOCTOR_PHONE, patient.getDocPhoneNo());
        //insert the values into the database
        return db.insert(TABLE_ID, null,cv);
    }

    /*
    *This method get all messages in the database
    * Returns the arraylist of the messages
     */
    public ArrayList<Message> getMessages()
    {
        //sql query to get all messages
        String sql = "SELECT * FROM "+TABLE_MESSAGE;
        //initialize the arraylist of messages
        ArrayList<Message> list = new ArrayList<Message>();

        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the cursor that receive a results from database
        Cursor c = db.rawQuery(sql, null);

        //test if a row exists in the cursor
        if(c.moveToFirst())
        {
            do{//loop through the cursor and set values into the message object
                Message message = new Message();
                message.setMessage(c.getString(c.getColumnIndex(MESSAGE)));
                message.setMessage_id(c.getInt(c.getColumnIndex(MESSAGE_ID)));
                message.setDate(c.getString(c.getColumnIndex(DATE)));

                //add message to the list
                list.add(message);

            }while(c.moveToNext());
        }

    //return the list
        return list;
    }

    /*
    *This method get the respond from the database
    * return the list of messages
     */
    public ArrayList<Message> getRespond()
    {
        //select the values from the database using the sql query
        String sql = "SELECT * FROM "+TABLE_RESPOND;
        ArrayList<Message> list = new ArrayList<Message>();

        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the cursor that receive a results from database
        Cursor c = db.rawQuery(sql, null);

        //test if cursor hav any record
        if(c.moveToFirst())
        {
            do{ //loop through the cursor and set values into the message object
                Message message = new Message();
                message.setResponse(c.getString(c.getColumnIndex(RESPOND)));
                message.setMessage_id(c.getInt(c.getColumnIndex(MESSAGE_ID)));
                message.setDate(c.getString(c.getColumnIndex(DATE)));
                message.setRespond_id(c.getInt(c.getColumnIndex(RESPOND_ID)));

                //add the message to the list
                list.add(message);

            }while(c.moveToNext());
        }

        //return the list object
        return list;
    }

    /*
    *This method get all the chat message from the database
    * return the list of chat messages
     */
    public ArrayList<ChatMessage> getAllChat()
    {
        //sql query to get all chat messages
        String sql = "SELECT * FROM "+TABLE_MESSAGE;
        ArrayList<ChatMessage> list = new ArrayList<ChatMessage>();
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the cursor that receive a results from database
        Cursor c = db.rawQuery(sql, null);

        //test if the cursor have anything inside
        if(c.moveToFirst())
        {
            do{
                //loop through the list and populate the ChatMessage object
                ChatMessage message = new ChatMessage(c.getString(c.getColumnIndex(SENDER)),c.getString(c.getColumnIndex(RECIEVER)),c.getString(c.getColumnIndex(BODY)),c.getString(c.getColumnIndex(MSGID)),Boolean.parseBoolean(c.getString(c.getColumnIndex(IS_MINE))));

                //add the message to the list
                list.add(message);

            }while(c.moveToNext());
        }

        //return the list of messages
        return list;
    }

    /*
    *This method get the patient from the database
    * since its always going to be one record, no need to specify any ID.
    * this method returns the patient object
     */

    public Patient getPatientDatails()
    {
        //sql statement to get the patient
        String sql = "SELECT * FROM "+TABLE_ID;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();

        //create the cursor that receive a results from database
        Cursor c = db.rawQuery(sql, null);
        Patient patient = null;

        //check if the row is really in the cursor
        if(c.moveToFirst())
        {
           // initialize the patient object and set values needed
            patient = new Patient();
            patient.setIdNumber(c.getLong(c.getColumnIndex(IDNUMBER)));
            patient.setDoctorCode(c.getLong(c.getColumnIndex(DOCTOR_CODE)));
          //  patient.setDocPhoneNo(c.getString(c.getColumnIndex(DOCTOR_PHONE)));
        }


        //return patient object
        return patient;
    }


    /*
    *This method delete the messages in the database
     */
    public void deleteMessage()
    {
        //sql query statement to delete the message
        String sql = "DELETE FROM "+TABLE_MESSAGE;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);//execute the query
    }

    /*
    *This method delete the chat messages in the database
     */
    public void deleteChatMessage()
    {
        //sql query statement to delete the chat message
        String sql = "DELETE FROM "+TABLE_MESSAGE;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);//execute the query
    }

    /*
     *This method delete the respond messages in the database
      */
    public void deleteRespond()
    {
        //sql query statement to delete the respond messages
        String sql = "DELETE FROM "+TABLE_RESPOND;
        //instantiate an object of SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);//execute the query
    }

    //delete news
    public void deleteNews(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] args = {String.valueOf(id)};

        db.delete(TABLE_NEWS, NEWS_ID +" =?",args);
    }
}
