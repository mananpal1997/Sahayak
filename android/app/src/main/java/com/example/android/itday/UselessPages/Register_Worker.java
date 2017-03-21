package com.example.android.itday.UselessPages;

import android.content.Intent;

import android.graphics.Bitmap;

import android.os.AsyncTask;

import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Base64;

import android.util.Log;

import android.view.View;

import android.widget.EditText;

import android.widget.Spinner;

import android.widget.Toast;

import com.example.android.itday.R;

import org.json.JSONException;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import im.delight.android.location.SimpleLocation;
import okhttp3.MediaType;

import okhttp3.OkHttpClient;

import okhttp3.Request;

import okhttp3.RequestBody;

import okhttp3.Response;

import static android.R.attr.x;

public class Register_Worker extends AppCompatActivity {
    private SimpleLocation location1;

    private String image=" ";

    public String url_worker_register ="http://52.66.168.240:3000/service";

    public static final MediaType JSON

            = MediaType.parse("application/json; charset=utf-8");

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register__worker);

    }

    public void register_contractor(View view) {

        String name= ((EditText)findViewById(R.id.client_name2)).getText().toString();

        String username = ((EditText)findViewById(R.id.client_eid)).getText().toString();

        String age = ((EditText)findViewById(R.id.client_age)).getText().toString();

        String gender= ((Spinner)findViewById(R.id.client_gender)).getSelectedItem().toString();

        String location= ((EditText)findViewById(R.id.client_address2)).getText().toString();

        String work_type= ((Spinner)findViewById(R.id.client_work_type)).getSelectedItem().toString();

        String phone_no = ((EditText)findViewById(R.id.client_phone_no2)).getText().toString();

        String email = ((EditText)findViewById(R.id.client_email)).getText().toString();

        String pass = ((EditText)findViewById(R.id.client_password2)).getText().toString();
        char service =(((Spinner)findViewById(R.id.client_spinner2)).getSelectedItem().toString()).charAt(0);

        JSONObject to_send = new JSONObject();

        try {

            to_send.put("name",name);

            to_send.put("age",age);

            to_send.put("gender",gender);

            to_send.put("image",image);


            to_send.put("phone_number",phone_no);

            to_send.put("work_type",work_type);

            to_send.put("parent_org","null");

            to_send.put("username",username);

            to_send.put("email",email);

            to_send.put("password",pass);

            Log.v("test",to_send.toString());

            location1 = new SimpleLocation(Register_Worker.this);

            url_worker_register= "http://52.66.168.240:3000/api/Services/"+service+"/workers";
            location1.beginUpdates();
            final double latitude = location1.getLatitude();
            final double longitude = location1.getLongitude();
            String lati = Double.toString(latitude);
            String longi = Double.toString(longitude);
            lati= lati.substring(0,8);
            longi = longi.substring(0,8);
            Log.v("locn ",lati+ " "+longi);
            to_send.put("location",lati+","+longi);


        } catch (JSONException e) {

            e.printStackTrace();

        }

        new Worker_Register().execute(to_send);

    }

    public void take_picture(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, 1);

        }

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

            byte[] b = baos.toByteArray();

            String image = Base64.encodeToString(b, Base64.DEFAULT);

            Log.v("Image is",image.toString());

        }

    }

    public class Worker_Register extends AsyncTask<JSONObject,Void,String> {

        @Override

        protected String doInBackground(JSONObject... params) {

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON,params[0].toString());

            Request request = new Request.Builder()

                    .url(url_worker_register)

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

            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();

            try{

                Log.v("test",result);





            }

            catch (Exception e)

            {e.printStackTrace();}

        }

    }




}