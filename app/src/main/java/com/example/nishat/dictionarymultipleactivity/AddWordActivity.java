package com.example.nishat.dictionarymultipleactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;

public class AddWordActivity extends Activity {
    public static final String NEW_WORDS_FILE_NAME = "new_word.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
    }

    public void addWordOkClick(View view) {
        EditText theNewText = (EditText) findViewById(R.id.the_new_word);
        EditText theDefinitionText = (EditText) findViewById(R.id.the_definition);
        String word = theNewText.getText().toString();
        String def = theDefinitionText.getText().toString();

        try {
            //write new word/def to the file
            PrintStream out = new PrintStream(
                    openFileOutput(NEW_WORDS_FILE_NAME, MODE_APPEND)
            );

            out.println(word + "   " + def);
            out.close();

            //shut down this activity and go to the main
            Intent intent = new Intent();
            intent.putExtra("word",word);
            intent.putExtra("definition",def);
            setResult(RESULT_OK,intent);
            finish(); //calls onDestroy().......Kill meee!!!
        } catch (Exception iOE) {
            Log.wtf("Add word failed.", iOE);
        }

    }
}
