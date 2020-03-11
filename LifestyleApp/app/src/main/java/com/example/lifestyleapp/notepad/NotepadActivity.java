package com.example.lifestyleapp.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lifestyleapp.MainActivity;
import com.example.lifestyleapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotepadActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private Context context = this;
    private Toolbar myToolbar;

    private NoteDatabase dbHelper;
    private NoteAdapter adapter;

    private FloatingActionButton fab;
    private ListView lv;
    private List<Note> noteList = new ArrayList<>();  // 首先需要从数据库读出，存储在noteList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {  // Toolbar 返回键返回主界面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotepadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new NoteAdapter(getApplicationContext(), noteList);
        refreshListView();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotepadActivity.this, NoteEditActivity.class);
                intent.putExtra("mode", 4);    // mode=4, 新建笔记
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notepad_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                if (noteList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "当前没有任何笔记！", Toast.LENGTH_SHORT).show();
                    break;
                }
                new AlertDialog.Builder(NotepadActivity.this)
                        .setMessage("确认清空所有笔记？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new NoteDatabase(context);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                // notes是数据库的TABLE_NAME
                                db.delete("notes", null, null);
                                db.execSQL("update sqlite_sequence set seq=0 where name='notes'"); // 使id归零
                                refreshListView();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        int returnMode;
        long noteId;
        returnMode = data.getExtras().getInt("mode", -1);
        noteId = data.getExtras().getLong("id", 0);

        if (returnMode == 1) {          // 修改了一个历史笔记
            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);
            Note updatedNote = new Note(content, time, tag);
            updatedNote.setId(noteId);
            CRUD op = new CRUD(context);
            op.open();
            op.updateNote(updatedNote);
            op.close();
        } else if (returnMode == 0) {   // 新建了一个非空笔记
            String content = data.getExtras().getString("content");
            String time = data.getExtras().getString("time");
            int tag = data.getExtras().getInt("tag", 1);
            Note newNote = new Note(content, time, tag);
            CRUD op = new CRUD(context);
            op.open();
            op.addNote(newNote);
            op.close();
        } else if (returnMode == 2) {   // 某一条历史笔记被删除
            long idOfDeletedNote = data.getExtras().getLong("id");
            CRUD op = new CRUD(context);
            op.open();
            Note tempNote = new Note();
            tempNote.setId(idOfDeletedNote);
            op.removeNote(tempNote);
            op.close();
        }

        refreshListView();
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv:
                Note currNote = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(NotepadActivity.this, NoteEditActivity.class);
                intent.putExtra("content", currNote.getContent());
                intent.putExtra("id", currNote.getId());
                intent.putExtra("time", currNote.getTime());
                intent.putExtra("mode", 3);    // openMode=3，点击一个历史笔记进入EditActivity
                intent.putExtra("tag", currNote.getTag());
                startActivityForResult(intent, 1);
                break;
        }
    }
}
