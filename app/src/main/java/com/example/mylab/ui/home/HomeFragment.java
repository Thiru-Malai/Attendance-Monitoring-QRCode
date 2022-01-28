package com.example.mylab.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mylab.R;
import com.example.mylab.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Toast.makeText(getContext(), "Please Wait Updating Data's", Toast.LENGTH_SHORT).show();
        //Snackbar.make(getContext(), "Please Wait Updateing Data's", Snackbar.LENGTH_LONG).show();
        final TextView textView = binding.textHome;
        final TextView textView2 = binding.textHome2;
        final TextView textView3 = binding.textHome3;
        final TextView textView4 = binding.textHome4;
        final TextView textView5 = binding.textHome5;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                //textView2.setText(s);
                //textView3.setText(s);
                //textView4.setText(s);
            }
        });
        homeViewModel.getText1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                textView2.setText(s);
                //textView3.setText(s);
                //textView4.setText(s);
            }
        });
        homeViewModel.getText2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                //textView2.setText(s);
                textView3.setText(s);
                //textView4.setText(s);
            }
        });
        homeViewModel.getText3().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                //textView2.setText(s);
                //textView3.setText(s);
                textView4.setText(s);
            }
        });
        homeViewModel.getText4().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                //textView2.setText(s);
                //textView3.setText(s);
                textView5.setText(s);
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