package com.example.android.itday.UselessPages;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.itday.RequestService;
import com.example.android.itday.UselessPages.RowItem;
import com.example.android.itday.R;
import com.example.android.itday.adapters.CustomListViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.android.itday.R.array.service;


public class TaskAssign extends Activity implements OnItemClickListener {

    public static String[] titles ;//= new String[] { "RAM", "Pashupati", "Mohan", "SHAM" };
private char service_id;
    private String location;
    public static final MediaType JSON

            = MediaType.parse("application/json; charset=utf-8");
    public static String[] descriptions ;//= new String[] {"Carpanter", "Helper", "Painter", "Contractor" };
    public String url = "http://52.66.168.240:3000/api/Clients/book_worker";

    //public static final Integer[] images = { R.drawable.contractor,
            //R.drawable.contractor, R.drawable.contractor, R.drawable.contractor };
            public static final Integer[] images={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    ListView listView;
    List<RowItem> rowItems;
    public static String[] username ;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assign);

        Intent intent = getIntent();

        titles = intent.getStringArrayExtra("name_list");
        username = intent.getStringArrayExtra("username");
        service_id=intent.getCharExtra("service",'1');
        location=intent.getStringExtra("location");

        descriptions = intent.getStringArrayExtra("rate_list");

        //images = intent.getIntArrayExtra("images");
        int length= intent.getIntExtra("size",1);
        rowItems = new ArrayList<RowItem>();
        Log.v("size",String.valueOf(titles.length));
        for (int i = 0; i < length; i++) {
            RowItem item = new RowItem(images[i], titles[i],"Rating: "+ descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        Log.i("items", Integer.toString(titles.length));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        String worker_id= username[position];
        SharedPreferences prefs = this.getSharedPreferences("com.example.android.itday", Context.MODE_PRIVATE);
        String client_id = prefs.getString("Client_id", null);
        String task_info="nothing";
        JSONObject to_send= new JSONObject();
        try {
            to_send.put("client_id",client_id);

        to_send.put("worker_id",worker_id);
        to_send.put("task_info",task_info);
        to_send.put("service_id", Integer.valueOf(service_id)-48);
        to_send.put("location",location);
        to_send.put("report_time","none");
        to_send.put("duration","none");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("sent JSON",to_send.toString()) ;
        Toast toast=Toast.makeText(getApplicationContext(),"Service Booked, Thanks for using!",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

    }



//    public class Assign_Worker extends AsyncTask<JSONObject,Void,String> {
//        @Override
//        protected String doInBackground(JSONObject... params) {
//            OkHttpClient client = new OkHttpClient();
//            RequestBody body = RequestBody.create(JSON,params[0].toString());
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();            Response response = null;
//            try {
//                response = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (response==null)
//            {
//                return null;}
//            try {
//                return response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
//            Log.v("test",result);
//
//        }
//
//
//    }
}