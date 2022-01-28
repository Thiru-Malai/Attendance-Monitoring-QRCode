package com.example.mylab;

import static android.icu.text.DisplayContext.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.nio.file.Files;
import java.nio.file.Paths;

import androidx.annotation.RequiresApi;
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogoutActivity extends AppCompatActivity {
    private String filename = "demoFile.txt";
    private File filePath = new File(Environment.getExternalStorageDirectory() + "/Authentication.txt");
    String ans = null;
    //private File filePath = new File(Environment.getExternalStorageDirectory() + "/Authentication.txt");
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        writeData();
        Intent intent = new Intent(LogoutActivity.this,LoginActivity.class);
        startActivity(intent);

    }
    private void readData()
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
            //cond = true;
            fin.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       // Toast.makeText(getApplicationContext(),"reading to file " + filename + " completed..",Toast.LENGTH_SHORT).show();
    }
    private void writeData()
    {
        File dir = getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();
        if(deleted){
            Toast.makeText(getApplicationContext(),"Logout Success",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getApplicationContext(),"writing to file " + filename + "completed...",Toast.LENGTH_SHORT).show();
    }
}
