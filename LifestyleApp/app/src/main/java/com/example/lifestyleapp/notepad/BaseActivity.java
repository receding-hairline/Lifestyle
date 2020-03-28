package com.example.lifestyleapp.notepad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifestyleapp.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNightMode();
    }

    public void setNightMode() {
        setTheme(R.style.DayTheme);
    }
}
