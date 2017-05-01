package com.example.nishat.dictionarymultipleactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int REQ_CODE_ADD_RESULT = 1234; //0-65535
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playTheQuizClick(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("Favorite class","CS 193A");
        intent.putExtra("MartyAge",19);
        startActivity(intent);
    }

    public void addAWordClick(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_ADD_RESULT && resultCode==RESULT_OK) {
            String word = data.getStringExtra("word");
            String def = data.getStringExtra("definition");
            Toast.makeText(this, "Word is '"+word+"', def is '"+def+"'",Toast.LENGTH_SHORT).show();
        }
    }
}
