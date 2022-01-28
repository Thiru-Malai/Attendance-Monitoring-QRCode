package com.example.mylab.ui.home;

import static com.example.mylab.LoginActivity.pageName;
import static com.example.mylab.LoginActivity.regnum;
import static com.example.mylab.QRScanActivity.labid;
import static com.example.mylab.LoginActivity.roll_num;
import static com.example.mylab.QRScanActivity.sessionid;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mText1;
    private MutableLiveData<String> mText2;
    private MutableLiveData<String> mText3;
    private MutableLiveData<String> mText4;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Name :"+pageName);
        mText1 = new MutableLiveData<>();
        mText1.setValue("Register Number :"+regnum);
        mText2 = new MutableLiveData<>();
        mText2.setValue("Lab Number :"+labid);
        mText3 = new MutableLiveData<>();
        mText3.setValue("Roll Number :"+roll_num);
        mText4 = new MutableLiveData<>();
        mText4.setValue("Session ID :"+sessionid);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getText1(){return mText1;}
    public LiveData<String> getText2(){return mText2;}
    public LiveData<String> getText3(){return mText3;}
    public LiveData<String> getText4(){return mText4;}
}