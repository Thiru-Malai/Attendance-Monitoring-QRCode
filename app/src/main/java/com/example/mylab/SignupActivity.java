package com.example.mylab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import com.android.volley.toolbox.HttpHeaderParser;

public class SignupActivity extends AppCompatActivity {
    public static String pageName1;
    public static String regnum1,roll_num1,lab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        JSONObject json = new JSONObject();

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://lab-attendence-mgmnt.herokuapp.com/users"; // your URL
        Button signup = (Button) findViewById(R.id.signup);
        int statuscode;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText signup_email = (EditText) findViewById(R.id.email);
                EditText signup_username = (EditText) findViewById(R.id.username);
                EditText signup_security = (EditText) findViewById(R.id.securitypin);
                EditText signup_rollnumber = (EditText) findViewById(R.id.rollnum);
                EditText signup_registernum = (EditText) findViewById(R.id.regnum);
                int sec_pin = Integer.parseInt(signup_security.getText().toString());
                int roll_n = Integer.parseInt(signup_rollnumber.getText().toString());
                try{
                    json.put("userName",signup_username.getText().toString());
                    json.put("email",signup_email.getText().toString());
                    json.put("securityPin",sec_pin);
                    json.put("rollNumber",roll_n);
                    json.put("regNumber",signup_registernum.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Couldn't add Details", Toast.LENGTH_SHORT).show();
                }
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //NetworkResponse networkResponse = response.

                                try{
                                    regnum1 = response.getJSONObject("user").getString("regNumber");
                                    pageName1 = response.getJSONObject("user").getString("userName");
                                    roll_num1 = response.getJSONObject("user").getString("rollNumber");
                                    lab1 = response.getJSONObject("user").getString("currentLab");
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Couldn't update",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){

                };
                queue.add(jsObjRequest);
                Snackbar.make(v, "Please Login To Continue", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}

