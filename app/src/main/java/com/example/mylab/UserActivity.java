package com.example.mylab;

import static com.example.mylab.LoginActivity.auth;
import static com.example.mylab.QRScanActivity.labid;
import static com.example.mylab.QRScanActivity.sessionid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    private String filename = "demoFile.txt";
    Boolean cond = false;
    String ans = null;
    final String url = "https://lab-attendence-mgmnt.herokuapp.com/users/me";
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        readData();
        Toast.makeText(getApplicationContext(),ans,Toast.LENGTH_SHORT).show();
        final RequestQueue queue = Volley.newRequestQueue(this);

        // String result = params.toString();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ans);
                return params;
            }
        };
        queue.add(postRequest);
    }
    public void readData()
    {
        try
        {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }

            // setting text from the file.
            ans = temp.toString();
            Toast.makeText(getApplicationContext(),temp.toString(),Toast.LENGTH_SHORT).show();
            cond = true;
            fin.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(),"reading to file " + filename + " completed..",Toast.LENGTH_SHORT).show();
    }
    public void user(){
        final RequestQueue queue = Volley.newRequestQueue(this);

       // String result = params.toString();
        final String url = "https://lab-attendence-mgmnt.herokuapp.com/users/me";

// prepare the Request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+ans);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
