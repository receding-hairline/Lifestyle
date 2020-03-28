package com.example.lifestyleapp.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.lifestyleapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Result;

public class NoteEditActivity extends BaseActivity {

    private Toolbar myToolbar;

    private EditText et;
    private String oldContent = "";
    private String oldTime = "";
    private int oldTag = 1;
    private long id = 0;

    private int openMode = 0;
    private int tag = 1;
    private boolean tagChange = false;

    public Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        Intent getIntent = getIntent();
        openMode = getIntent.getIntExtra("mode", 0);

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        if (openMode == 3) {               // 如果是以点击历史笔记的方式进入编辑界面
            myToolbar.setTitle("笔记内容");
            setSupportActionBar(myToolbar);  // 显示删除图标
        } else if (openMode == 4) {        // 如果是新建笔记的方式进入编辑界面
            myToolbar.setTitle("新建笔记");
        }
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et = (EditText) findViewById(R.id.et);

        if (openMode == 3) {
            id = getIntent.getLongExtra("id", 0);
            oldContent = getIntent.getStringExtra("content");
            oldTime = getIntent.getStringExtra("time");
            oldTag = getIntent.getIntExtra("tag", 1);
            et.setText(oldContent);
            et.setSelection(oldContent.length());   // 设置光标的位置为文本的末端
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                new AlertDialog.Builder(NoteEditActivity.this)
                        .setMessage("确认删除此条笔记？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // mode为2代表以删除历史笔记的方式返回主界面
                                intent.putExtra("mode", 2);
                                intent.putExtra("id", id);
                                setResult(RESULT_OK, intent);
                                finish();    // 返回笔记主界面
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            autoSetMessage();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void autoSetMessage() {
        if (openMode == 4) {   // 笔记为新建笔记
            if (et.getText().toString().length() == 0) {  // 若新建笔记为空，则无需任何操作
                intent.putExtra("mode", -1);
            } else {
                intent.putExtra("mode", 0);
                intent.putExtra("content", et.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("tag", tag);
            }
        } else {          // 打开一个历史笔记
            if (et.getText().toString().equals(oldContent) && tagChange == false) {   // 没有作任何修改
                intent.putExtra("mode", -1);
            } else {          // 对历史笔记进行了修改
                intent.putExtra("mode", 1);
                intent.putExtra("content", et.getText().toString());
                intent.putExtra("time", dateToStr());
                intent.putExtra("id", id);
                intent.putExtra("tag", tag);
            }
        }
    }

    public String dateToStr(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
