package com.example.lifestyleapp.notepad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.lifestyleapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotepadActivity extends AppCompatActivity {

    private Context context = this;

    private NoteDatabase dbHelper;
    private NoteAdapter adapter;

    private FloatingActionButton fab;
    private ListView lv;
    private List<Note> noteList = new ArrayList<>();  // 首先需要从数据库读出，存储在noteList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        lv = (ListView) findViewById(R.id.lv);
        adapter = new NoteAdapter(getApplicationContext(), noteList);
        refreshListView();
        lv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotepadActivity.this, NoteEditActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String content = data.getStringExtra("content");
        String time = data.getStringExtra("time");
        Note note = new Note(content, time, 1);
        CRUD op = new CRUD(context);
        op.open();
        op.addNote(note);
        op.close();
        refreshListView();
    }

    public void refreshListView() {
        CRUD op = new CRUD(context);
        op.open();
        if (noteList.size() > 0) {
            noteList.clear();
        }
        noteList.addAll(op.getAllNotes());
        op.close();
        adapter.notifyDataSetChanged();
    }
}
