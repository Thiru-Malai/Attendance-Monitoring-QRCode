package com.example.mylab;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView view_email;
    private String filename = "demoFile.txt";
    //private File filePath = new File(Environment.getExternalStorageDirectory() + "/Authentication.txt");
    public static String pageName;
    public static String regnum,roll_num,auth;
    public static Boolean log = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Login = (Button) findViewById(R.id.login);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final RequestQueue queue1 = Volley.newRequestQueue(this);
        final String url = "https://lab-attendence-mgmnt.herokuapp.com/users/login"; // your URL
        final String url1 = "https://lab-attendence-mgmnt.herokuapp.com/records";
        JSONObject json = new JSONObject();

        TextView createaccount = (TextView) findViewById(R.id.textView2);
        view_email = (TextView) findViewById(R.id.email_view);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Login_Rollnumber = (EditText) findViewById(R.id.loginrollnumber);
                EditText Login_SecurityPin = (EditText) findViewById(R.id.loginsecuritypin);
                int num_roll = Integer.parseInt(Login_Rollnumber.getText().toString());
                int num_pin = Integer.parseInt(Login_SecurityPin.getText().toString());
                try {
                    json.put("rollNumber", num_roll);
                    json.put("securityPin", num_pin);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't add", Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    //auth = response.getString("token");

                                    //Toast.makeText(getApplicationContext(), auth, Toast.LENGTH_SHORT).show();
                                    regnum = response.getJSONObject("user").getString("regNumber");
                                    pageName = response.getJSONObject("user").getString("userName");
                                    roll_num = response.getJSONObject("user").getString("rollNumber");
                                    //lab = response.getJSONObject("user").getString("currentLab");
                                    auth = response.getString("token");
                                    log = true;
                                    Toast.makeText(getApplicationContext(), "Welcome " + pageName, Toast.LENGTH_SHORT).show();
                                    writeData();

                                    Intent intent = new Intent(LoginActivity.this,QRScanActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //view_email.setText("ARUN");
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "Couldn't update", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
                //view_email.setText(pageName)
            }
        });
    }
    private void writeData()
    {
        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String data = auth;
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(),"writing to file " + filename + "completed...",Toast.LENGTH_SHORT).show();
    }


}