package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lifestyleapp.notepad.NotepadActivity;

public class MainActivity extends AppCompatActivity {

    //保存注册成功的用户名和密码
    String userName;
    String password;

    private Button btnNotepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将LoginFragment作为首页
        Button buttonIn=(Button)findViewById(R.id.buttonIn);
        buttonIn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                LoginFragment fragment=new LoginFragment();
                fragmentTransaction.add(R.id.fragment_container,fragment);
                fragmentTransaction.commit();
            }
        });

        btnNotepad = (Button) findViewById(R.id.buttonNotepad);
        btnNotepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotepadActivity.class);
                startActivity(intent);
            }
        });
    }
}
