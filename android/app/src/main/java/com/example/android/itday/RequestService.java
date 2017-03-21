
package com.example.android.itday;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.itday.UselessPages.TaskAssign;
import com.google.android.gms.common.api.GoogleApiClient;
import com.schibstedspain.leku.LocationPickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.rating;
import static com.example.android.itday.R.array.service;

public class RequestService extends AppCompatActivity {
    public String id;
    public char service_no;
    public static final MediaType JSON

            = MediaType.parse("application/json; charset=utf-8");
    private String service;
    private String[] name1= new String[40];
    private String[] rating1= new String[40];
    private Integer[] images= new Integer[40];
    private String[] usernames= new String[40];
private String lati, longi;
    private double latitude,longitude;
    private String url_to_send;
    public String url = "http://52.66.168.240:3000/api/Workers/search";

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_request_service);
        id= getIntent().getExtras().get("Client_key").toString();
}

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.radioButton:
//                if (checked)
//                    url=url+"/Workers/search";
//                Log.v("test",url);
//                    break;
//            case R.id.radioButton2:
//                if (checked)
//                    Log.v("test",url);
//                    break;
//        }
//    }

public void pick_location(View view) {
    Intent i = new Intent(this, LocationPickerActivity.class);
    startActivityForResult(i, 1);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("test",String.valueOf(resultCode));
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                Log.d("LATITUDE****", String.valueOf(latitude));
                longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);

                Log.d("LONGITUDE****", String.valueOf(longitude));
                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                Log.d("ADDRESS****", String.valueOf(address));
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
                Log.d("POSTALCODE****", String.valueOf(postalcode));
                Bundle bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
                //Log.d("BUNDLE TEXT****", bundle.getString("test"));
                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                Log.d("FULL ADDRESS****", fullAddress.toString());
            }        }

    }

    public void get_worker(View view) throws JSONException {
        service = ((Spinner)findViewById(R.id.service_name_spinner)).getSelectedItem().toString();
        Log.v("Service val ",service);

        service_no=service.charAt(0);
        service= service.substring(2).toLowerCase();

        JSONObject to_send= new JSONObject();

        lati = Double.toString(latitude);
        longi = Double.toString(longitude);
        lati=lati+"000000";
        longi=longi+"000000";
        lati= lati.substring(0,8);
        longi = longi.substring(0,8);

        to_send.put("rating_filter",1);
        to_send.put("service_name",Integer.valueOf(service_no)-48);
        to_send.put("work_type","daily");
        to_send.put("location",lati+","+longi);
        //url= url+URLEncoder.encode(to_send, "UTF-8");
        Log.v("Sent json",to_send.toString());
        new Get_Worker().execute(to_send);


    }


    public class Get_Worker extends AsyncTask<JSONObject,Void,String> {
        @Override
        protected String doInBackground(JSONObject... params) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON,params[0].toString());
            Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response==null)
            {
                return null;}
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
            Log.v("test",result);
            try {
                JSONObject to_display= new JSONObject(result);

                JSONArray list_w = to_display.getJSONArray("result");
                int j=0;
                for(j=0; j< list_w.length() ; j++ ){
                    JSONObject obj = list_w.getJSONObject(j);
                    name1[j] = obj.getString("name");
                    rating1[j] = obj.getString("rating");
                    usernames[j]=obj.getString("username");
                    images[j]=1;
                }

                Intent i = new Intent(RequestService.this, TaskAssign.class);
                i.putExtra("name_list",name1);
                i.putExtra("rate_list",rating1);
                i.putExtra("size",j);
                i.putExtra("location",lati+","+longi);
                i.putExtra("service",Integer.valueOf(service_no)-48);
                i.putExtra("username",usernames);



                startActivity(i);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}