package com.example.android.itday.UselessPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;

import com.example.android.itday.R;
import com.example.android.itday.RequestService;

import org.json.JSONException;

import org.json.JSONObject;

import java.io.IOException;

import im.delight.android.location.SimpleLocation;
import okhttp3.MediaType;

import okhttp3.OkHttpClient;

import okhttp3.Request;

import okhttp3.RequestBody;

import okhttp3.Response;

public class Login extends AppCompatActivity {
    public static final String MyPREFERENCES = "com.example.android.itday" ;

    SharedPreferences sharedpreferences;

    public String url_client ="http://52.66.168.240:3000/api/Clients/login";

    public String url_contractors ="http://52.66.168.240:3000/api/Contractors/login";

    public static final MediaType JSON

            = MediaType.parse("application/json; charset=utf-8");

    private EditText eid;

    private  EditText password;

    private TextView eid_field;

    private TextView pass_field;

    private Button contractor;

    private  Button client;

    private Button submit_button;

    private String user;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        eid = (EditText)findViewById(R.id.login_eid);

        password =(EditText)findViewById(R.id.login_pass);

        eid_field =(TextView)findViewById(R.id.login_eid_field);

        pass_field=(TextView)findViewById(R.id.login_pass_field);

        contractor =(Button)findViewById(R.id.login_contractor_button);

        client=(Button)findViewById(R.id.login_client_button);

        submit_button=(Button)findViewById(R.id.login_submit_button);

    }

    public void forward_client_login(View view) {

        eid.setVisibility(View.VISIBLE);

        password.setVisibility(View.VISIBLE);

        eid_field.setVisibility(View.VISIBLE);

        pass_field.setVisibility(View.VISIBLE);

        submit_button.setVisibility(View.VISIBLE);

        user="client";

        contractor.setVisibility(View.INVISIBLE);

        client.setVisibility(View.INVISIBLE);

    }

    public void forward_contractor_login(View view) {

        eid.setVisibility(View.VISIBLE);

        password.setVisibility(View.VISIBLE);

        eid_field.setVisibility(View.VISIBLE);

        pass_field.setVisibility(View.VISIBLE);

        submit_button.setVisibility(View.VISIBLE);

        user="contractor";

        contractor.setVisibility(View.INVISIBLE);

        client.setVisibility(View.INVISIBLE);

    }

    public void login(View view) {

        String eid_no=eid.getText().toString();

        String password_entered= password.getText().toString();

        if(user.equals("contractor"))

        {

            JSONObject to_send= new JSONObject();

            try {

                to_send.put("username",eid_no);

                to_send.put("password",password_entered);

                Log.v("Test",to_send.toString());

                new Contractor_login().execute(to_send);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

        else if(user.equals("client"))

        {

            JSONObject to_send= new JSONObject();

            try {

                to_send.put("username",eid_no);

                to_send.put("password",password_entered);

                Log.v("Test",to_send.toString()+"client");

                new Client_login().execute(to_send);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

    }

    public class Client_login extends AsyncTask<JSONObject,Void,String> {

        @Override

        protected String doInBackground(JSONObject... params) {

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,params[0].toString());

            Request request = new Request.Builder()

                    .url(url_client)

                    .post(body)

                    .build();

            Response response = null;

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

            Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();

            Log.v("test",result);
            JSONObject get_data= null;
            try {
                get_data = new JSONObject(result);

            String user_id = get_data.getString("userId");
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Client_id",user_id);
                Log.v("test",user_id);


                Intent i = new Intent(Login.this, RequestService.class);
                i.putExtra("Client_key",get_data.getString("id")) ;
                i.putExtra("Client_id",user_id);
                editor.apply();

                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public class Contractor_login extends AsyncTask<JSONObject,Void,String> {

        @Override

        protected String doInBackground(JSONObject... params) {

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,params[0].toString());

            Request request = new Request.Builder()

                    .url(url_contractors)

                    .post(body)

                    .build();

            Response response = null;

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

            Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();

            Log.v("test",result);
            JSONObject get_data= null;
            try {
                get_data = new JSONObject(result);

                String user_id = get_data.getString("userId");
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Client_id",user_id);
                editor.apply();

                Log.v("test",user_id);
                Intent i = new Intent(Login.this, Contractor_Panel.class);
                i.putExtra("Contractor_key",get_data.getString("id")) ;
                i.putExtra("Contractor_id",user_id);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}