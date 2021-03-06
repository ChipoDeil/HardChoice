package com.example.yaroslav.hadchoise01;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ProcessScreen extends AppCompatActivity {
    int i;
    Animation animOne;
    Button itemOne;
    Button itemTwo;
    Items[] items;
    TextView tv;
    String id;
    String[] forScore;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HardChoice");
        DBMain db = new DBMain(this);
        Intent getId = getIntent();
        id = getId.getExtras().getString("id");
        String[] itemsName = new String[db.getCountItems(id)];
        String[] itemsScore = new String[db.getCountItems(id)];
        Cursor resItems = db.getItems(id);
        boolean hasMoreItems = resItems.moveToFirst();
        i = 0;
        while(hasMoreItems){
            itemsName[i] = resItems.getString(resItems.getColumnIndex("NAME"));
            itemsScore[i] = resItems.getString(resItems.getColumnIndex("SCORE"));
            hasMoreItems = resItems.moveToNext();
            i++;
        }
        items = new Items[itemsName.length];
        for (int i = 0; i < items.length; i++){
            items[i] = new Items(itemsName[i], Integer.parseInt(itemsScore[i]));
        }
        itemOne = (Button) findViewById(R.id.itemOne);
        itemTwo = (Button) findViewById(R.id.itemTwo);
        itemOne.setText(itemsName[0]);
        itemTwo.setText(itemsName[1]);
        tv = (TextView)findViewById(R.id.textView6);
        process();
    }
    public void process(){
        itemTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = false;
                String name = itemTwo.getText().toString();
                for (int i = 0; i < items.length; i++) {
                    if (name.equals(items[i].getName())) {
                        items[i].setScore(items[i].getScore() + 1);
                    }
                }
                for (int i = 0; i < items.length; i++) {
                    for (int j = 0; j < items.length; j++) {
                        if (items[i].getScore() == items[j].getScore() && i != j) {
                            itemOne.setText(items[i].getName());
                            itemTwo.setText(items[j].getName());
                            a = true;
                            break;
                        }
                    }
                }
                if (!a) {
                    DBMain db = new DBMain(ProcessScreen.this);
                    Cursor res = db.getItems(id);
                    forScore = new String[db.getCountItems(id)];
                    boolean hasMoreItems = res.moveToFirst();
                    while (hasMoreItems) {
                        for (int j = 0; j < items.length; j++) {
                            if ((items[j].getName()).equals(res.getString(res.getColumnIndex("NAME")))) {
                                forScore[j] = res.getString(res.getColumnIndex("ID"));
                            }
                        }
                        hasMoreItems = res.moveToNext();
                    }
                    for (int i = 0; i < items.length; i++) {
                        db.updateScore(forScore[i], items[i].getScore());
                    }
                    db.updateStatus(id, 0);
                    intent = new Intent(ProcessScreen.this, FinishScreen.class);
                    intent.putExtra("ID", id);
                    startActivity(intent);
                }
            }
        });
        itemOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = false;
                String name = itemOne.getText().toString();
                for (int i = 0; i < items.length; i++) {
                    if (name.equals(items[i].getName())) {
                        items[i].setScore(items[i].getScore() + 1);
                    }
                }
                for (int i = 0; i < items.length; i++) {
                    for (int j = 0; j < items.length; j++) {
                        if (items[i].getScore() == items[j].getScore() && i != j) {
                            itemOne.setText(items[i].getName());
                            itemTwo.setText(items[j].getName());
                            a = true;
                            break;
                        }
                    }
                }
                if (!a) {
                    DBMain db = new DBMain(ProcessScreen.this);
                    Cursor res = db.getItems(id);
                    forScore = new String[db.getCountItems(id)];
                    boolean hasMoreItems = res.moveToFirst();
                    while (hasMoreItems) {
                        for (int j = 0; j < items.length; j++) {
                            if ((items[j].getName()).equals(res.getString(res.getColumnIndex("NAME")))) {
                                forScore[j] = res.getString(res.getColumnIndex("ID"));
                            }
                        }
                        hasMoreItems = res.moveToNext();
                    }
                    for (int i = 0; i < items.length; i++) {
                        db.updateScore(forScore[i], items[i].getScore());
                    }
                    db.updateStatus(id, 0);
                    intent = new Intent(ProcessScreen.this, FinishScreen.class);
                    intent.putExtra("ID", id);
                    startActivity(intent);
                }
            }
        });
    }
}
