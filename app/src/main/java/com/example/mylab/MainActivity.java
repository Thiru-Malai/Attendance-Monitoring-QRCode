package com.example.mylab;

import static com.example.mylab.LoginActivity.log;
import static com.example.mylab.LoginActivity.auth;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mylab.LoginActivity;
import com.example.mylab.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private String filename = "demoFile.txt";
    String ans = null;
    Boolean cond = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
            Toast.makeText(this, "Permission not granted!\n Grant permission and restart app", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        readData();
        if(cond){
           // Toast.makeText(this,"Loaded", Toast.LENGTH_SHORT).show();
            //File dir = Environment.getExternalStorageDirectory();
            //String path = dir.getAbsolutePath();
            //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,QRScanActivity.class);
            startActivity(intent);

        }
        else{
            Intent intetn = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intetn);
        }


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
            Toast.makeText(getApplicationContext(),"User Found",Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
            cond = true;
            fin.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(),"reading to file " + filename + " completed..",Toast.LENGTH_SHORT).show();
    }
}