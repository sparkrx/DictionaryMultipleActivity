package com.example.nishat.dictionarymultipleactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private HashMap<String, String> dictionary;
    private String word;
    ArrayAdapter<String> adapter;
    ArrayList<String> definitions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        String favClass = intent.getStringExtra("Favorite class");
        int age = intent.getIntExtra("MartyAge",-1);

        //connect the ListView to an ArrayList of definitions
        ListView definitionList = (ListView) findViewById(R.id.definitionList);
        definitionList.setOnItemClickListener(this);

        //set the listview to store 5 dynamic items
        definitions = new ArrayList<>();
        adapter = new ArrayAdapter<> (
                this,
                //android.R.layout.simple_list_item_1,
                R.layout.mydictionarylistlayout,
                R.id.myTextLayout,
                definitions
        );
        definitionList.setAdapter(adapter);

        readAllDefinitions();
        pick5RandomWords();
    }

    /*
    This method reads the GRE dictionary file and converts it into a map
    where the keys are words and the values are their definitions.
    Each line of the file contains a word followed by a tab and definition
    "benign kind and gentle; mild"
     */
    private void readAllDefinitions() {
        dictionary = new HashMap<>();

        //read the pre-existing starting words from gre_word.txt
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.gre_word));
        readWordsFromFIle(scan);

        //read any added wors from new_words.txt
        try {
        Scanner scan2 = new Scanner(openFileInput(AddWordActivity.NEW_WORDS_FILE_NAME));
        readWordsFromFIle(scan2);
        } catch (Exception ex) {
            Log.wtf("No new word!", ex);
        }
    }
    private void readWordsFromFIle(Scanner scan){
        while (scan.hasNext()) {
            String line = scan.nextLine();
            String[] pieces = line.split("   ");
            //pieces ={"abate", "to lesson; to subside"}
            //Log.d("Heeelp", "dictionary = "+dictionary);
            //Log.d("Heeelp", "pieces = "+pieces);
            if (pieces.length >= 2) {
                dictionary.put(pieces[0], pieces[1]);
            }
        }
    }
    /*
    dictionary.put("asad","is a good boy");
    dictionary.put("bsad","is a good girl");
    dictionary.put("csad","is a good man");
    dictionary.put("dsad","is a good woman");
    dictionary.put("esad","is a good child");
    dictionary.put("fsad","is a good old");
    */
    public void pick5RandomWords(){
        ArrayList<String> chosenWord = new ArrayList<>(dictionary.keySet());
        Collections.shuffle(chosenWord);
        word = chosenWord.get(0);
        TextView the_word = (TextView) findViewById(R.id.the_word);
        the_word.setText(word);

        definitions.clear();
        for(int i=0; i<5; i++) {
            String defn = dictionary.get(chosenWord.get(i));
            definitions.add(defn);
        }
        Collections.shuffle(definitions);

        //update the listView to store 5 dynamic items
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
        ListView definitionList = (ListView) findViewById(R.id.definitionList);
        String text = definitionList.getItemAtPosition(position).toString();
        String correctDefinition = dictionary.get(word);
        if(correctDefinition.equals(text)){
            Toast.makeText(this, "you got it!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "LoL Berkely!!", Toast.LENGTH_SHORT).show();
        }
        pick5RandomWords();
    }

}
