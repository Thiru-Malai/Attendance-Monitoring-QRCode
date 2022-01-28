package com.example.mylab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import static com.example.mylab.QRScanActivity.labid;
import static com.example.mylab.QRScanActivity.sessionid;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.mylab.LoginActivity.auth;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecordCreationActivity extends AppCompatActivity {
    String url1 = "https://lab-attendence-mgmnt.herokuapp.com/records";
    JSONObject record = new JSONObject();
    RequestQueue queue1 = Volley.newRequestQueue(this);
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Record Creation Success", Toast.LENGTH_SHORT).show();
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                    record.put("labID",Integer.parseInt(labid));
                    record.put("session",Integer.parseInt(sessionid));
                    String requestBody = jsonBody.toString();
                    return requestBody.getBytes("utf-8");
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
        Log.d("string", stringRequest.toString());
        queue1.add(stringRequest);
    }
}
