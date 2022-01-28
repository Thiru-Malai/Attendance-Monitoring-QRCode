package com.example.mylab;

import static com.example.mylab.LoginActivity.auth;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;
import static com.example.mylab.LoginActivity.pageName;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Nullable;

public class QRScanActivity extends AppCompatActivity {
    public static String labid = null;
    public static String sessionid = null;
    private String filename = "demoFile.txt";
    Boolean cond = false;
    String ans = null;
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Toast.makeText(getApplicationContext(),intentResult.getContents(), Toast.LENGTH_SHORT).show();
                String lab1 = intentResult.getContents();
                String[] ans1 = lab1.split(" ");
               // StringTokenizer ans = new StringTokenizer(lab1," ");
                //lab = ans.nextToken();
                labid = ans1[0] ;
                sessionid = ans1[1];
                //Toast.makeText(getApplicationContext(),"Lab ID "+labid + "\n"+"Session ID "+sessionid,Toast.LENGTH_SHORT).show();
                record();
                if(pageName == null){
                    Toast.makeText(getApplicationContext(),"User NUll",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(this,UserActivity.class);
                    startActivity(in);
                }
                Intent intent = new Intent(QRScanActivity.this,FragmentsClass.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Please Scan QR to Create Session For You",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QRScanActivity.this,FragmentsClass.class);
            startActivity(intent);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void record(){
        readData();
        if(cond){
            auth = ans;
        }
        String url1 = "https://lab-attendence-mgmnt.herokuapp.com/records";
        JSONObject record = new JSONObject();
        RequestQueue queue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Your Time Has Been Recorded", Toast.LENGTH_SHORT).show();
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error in Creation",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    // request body goes here
                    JSONObject jsonBody = new JSONObject();
                    record.put("labID",labid);
                    record.put("session",sessionid);


                    String requestBody = record.toString();
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (Exception uee) {
                    // VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+auth);
                //params.put("id", "oneapp.app.com");
                //params.put("key", "fgs7902nskagdjs");

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        queue1.add(stringRequest);
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
            //Toast.makeText(getApplicationContext(),temp.toString(),Toast.LENGTH_SHORT).show();
            cond = true;
            fin.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       // Toast.makeText(getApplicationContext(),"reading to file " + filename + " completed..",Toast.LENGTH_SHORT).show();
    }
}
