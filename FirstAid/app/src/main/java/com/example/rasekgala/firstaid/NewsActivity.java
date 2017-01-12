package com.example.rasekgala.firstaid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends Activity {

    private ListView lvDisplay;
    private MyAdapter adapter;
    private ArrayList<News> list = new ArrayList<News>();
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        this.setTitle("Headlines");
        //find the listview
        lvDisplay = (ListView) findViewById(R.id.listView);

        db = new MyDatabase(getApplicationContext());
        SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREFF), Context.MODE_PRIVATE);
        // SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),Context.MODE_PRIVATE);
        String recent_token = FirebaseInstanceId.getInstance().getToken();


            String token = sharedPreference.getString(getString(R.string.FCM_TOKEN),"Non found..");
            Toast.makeText(this, "Device key is : "+token+" ANOTHER ONE "+FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_LONG ).show();
        Log.d("Not v","Token ["+token+"]");

        //long newsId, String body, String title
        list.add(new News(10, "Swin Flu is very dangerious and it will kill you because it comes from pigs, please avoid eating the pork!!!", "Swin Flu can kill you","Dr PP Rasekgala","10-12-2016",""));
        list.add(new News(45, "Please avoid using some of the cosmetics as they will cause cancer.. most of you want to be yellow bone but risk with your life.", "Skin Cancer is here","Dr GN Monyebodi","10-12-2016", "read"));
        list.add(new News(36, "Doctors have finally found the cure for Aids and it is going to be expensive", "HIV AND AIDS has a cure","Dr JK Molepo","22-02-2016", ""));
        list.add(new News(37, "Condoms help you from getting STIs, and by fathering unprepared children", "Use Condoms"," Dr TN Rasekgala","03-12-2015", "read"));
        list.add(new News(38, "Condoms help you from getting STIs, and by fathering unprepared children", "Use Medication"," Dr TN Mankga","03-12-2015", ""));
        list.add(new News(39, "Condoms help you from getting STIs, and by fathering unprepared children", "Use Protection"," Dr TN Mokgobu","03-12-2015", "read"));

        //change list view divider
        ColorDrawable colorDrawable = new ColorDrawable(this.getResources().getColor(R.color.reds));
        lvDisplay.setDivider(colorDrawable);
        lvDisplay.setDividerHeight(1);


        //adapter to set the content of the listview
        adapter = new MyAdapter(this, R.layout.listciew_file, list);



        //set adapter
        lvDisplay.setAdapter(adapter);

        //set the onclick listener of the listview
        lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //start news content activity and set the news title and the body along with the intent
                Intent in =  new Intent(NewsActivity.this, NewsContentActivity.class);
                News news = list.get(position);

                in.putExtra("news", news);

                //start activity
                startActivity(in);
                NewsActivity.this.finish();
            }
        });

        registerForContextMenu(lvDisplay);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        if(id == R.id.action_delete)
        {
            try {

                News news = adapter.getItem(info.position);
                db.deleteNews(news.getNewsId());

                Toast.makeText(this,"Deleted..",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                //recreate();

            }catch (Exception ex)
            {
                Toast.makeText(this,"Something went wrong, try again.",Toast.LENGTH_LONG).show();
            }


        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater infact = getMenuInflater();

        infact.inflate(R.menu.menu_news_content,menu);
    }

    @Override
    public void onBackPressed() {
        //chage the behaviour of back button
        startActivity(new Intent(this, AllMessageActivity.class));
        this.finish();
    }

    private class MyAdapter extends ArrayAdapter<News>
    {
        private LayoutInflater inflater;
        private int resource;
        private ArrayList<News> list;

        public MyAdapter(Context context, int resource, List<News> objects) {
            super(context, resource, objects);

            this.list = (ArrayList<News>) objects;
            this.resource = resource;
            inflater = NewsActivity.this.getLayoutInflater();
        }

        HolderView holderView;
        View view;
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null)
            {
                holderView = new HolderView();
                view = inflater.inflate( resource, parent, false);

                holderView.textNews = (TextView) view.findViewById(R.id.newsText);
                holderView.textReporter = (TextView) view.findViewById(R.id.reporter);
                holderView.textDate = (TextView) view.findViewById(R.id.date);

                view.setTag(holderView);

            }else {
                view = convertView;
                holderView = (HolderView) view.getTag();
            }

            News news = list.get(position);

            holderView.textNews.setText(news.getTitle());
            holderView.textReporter.setText(news.getReporter());
            holderView.textDate.setText(news.getDate());

            if(news.getStatus().equals("read"))
            {
                holderView.textNews.setTextColor(Color.BLACK);

            }else{
                holderView.textNews.setTextColor(Color.RED);

            }


            return view;
        }

        class HolderView
        {
            TextView textNews;
            TextView textReporter;
            TextView textDate;
        }
    }
}
