package com.example.android.itday.UselessPages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.itday.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register_Client extends AppCompatActivity {

    public String url ="http://52.66.168.240:3000/api/Clients/";

    public static final MediaType JSON

            = MediaType.parse("application/json; charset=utf-8");

        private EditText et_name;
        private EditText et_location;
        private EditText et_sector;
        private EditText et_password;
        private EditText et_phone_number;
        private EditText et_username;
        private EditText et_email;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__client);



    }

    public void register_client(View view) {
        String name = ((EditText) findViewById(R.id.client_name2)).getText().toString();
        String location = ((EditText) findViewById(R.id.client_address2)).getText().toString();
        //String sector = ((Spinner) findViewById(R.id.client_spinner)).getSelectedItem().toString();
        String password = ((EditText) findViewById(R.id.client_password2)).getText().toString();
        String phone_number = ((EditText) findViewById(R.id.client_phone_no2)).getText().toString();
        String username = ((EditText) findViewById(R.id.client_eid)).getText().toString();
        String email = ((EditText) findViewById(R.id.client_email)).getText().toString();


        JSONObject to_send = new JSONObject();
        try {
            to_send.put("name",name);
            to_send.put("phone_number",phone_number);
            to_send.put("location",location);
            to_send.put("sector","private");
            to_send.put("username",username);
            to_send.put("email",email);
            to_send.put("password",password);
            to_send.put("image","test");
            //to_send.put("",);
            // Log.i("test", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new TheTask().execute(to_send);
    }    public class TheTask extends AsyncTask<JSONObject,Void,String> {
        @Override
        protected String doInBackground(JSONObject... params) {OkHttpClient client = new OkHttpClient();RequestBody body = RequestBody.create(JSON,params[0].toString());            Request request = new Request.Builder()
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
        }        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
            Log.v("test",result);
        }


    }


}
