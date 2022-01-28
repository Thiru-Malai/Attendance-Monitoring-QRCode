package com.example.mylab.ui.slideshow;

import static com.example.mylab.LoginActivity.auth;

import static io.realm.Realm.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylab.LogoutActivity;
import com.example.mylab.MainActivity;
import com.example.mylab.QRScanActivity;
import com.example.mylab.R;
import com.example.mylab.databinding.FragmentSlideshowBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    String url1 = "https://lab-attendence-mgmnt.herokuapp.com/logout";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // b = (Button) inflater.inflate(R.layout.logout, container, false).findViewById(R.id.logout);
        //b.setOnClickListener(this);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        final Button button = binding.button2;
        final Button exit = binding.exitlab;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                textView.setText(s);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LogoutActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Scan to exit", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), QRScanActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}