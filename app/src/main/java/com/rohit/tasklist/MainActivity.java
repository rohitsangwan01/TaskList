 package com.rohit.tasklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.an.customfontview.CustomTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rohit.tasklist.RoomDb.MyAppDatabase;
import com.rohit.tasklist.RoomDb.Notes;

import java.util.Collections;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    public static MyAppDatabase myAppDatabase;
     String info = "";
     public static CustomTextView empty_view;
     public static Button btnList;
     public static TextView txtTitle,txtDes;
     public static ImageView imgDlt;
     public static  List<Notes> notes;
     public static TextView txtTask;
     public static LinearLayout LinearNote;
     public static RecyclerView TaskRecycler;
     boolean btnClicked = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        empty_view = findViewById(R.id.empty_view);
        btnList = findViewById(R.id.btnList);
        txtTitle = findViewById(R.id.txtTitle);
        txtDes = findViewById(R.id.txtDes);
        imgDlt = findViewById(R.id.imgDlt);
        txtTask = findViewById(R.id.txtTask);
        LinearNote = findViewById(R.id.LinearNote);
        TaskRecycler = findViewById(R.id.TaskRecycler);

        btnList.setVisibility(View.INVISIBLE);
        empty_view.setVisibility(View.VISIBLE);
        LinearNote.setVisibility(View.GONE);
        TaskRecycler.setVisibility(View.GONE);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,"notesDb").allowMainThreadQueries().build();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddTaskActivity.class));
            }
        });


        imgDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes notes = new Notes();
                notes.setTitle(txtTitle.getText().toString());
                MainActivity.myAppDatabase.dao().DeleteNote(notes);
                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                new AgentAsyncTask().execute();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnClicked){
                    btnList.setText("Hide All");
                    TaskRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    Collections.reverse(notes);
                    TaskRecycler.setAdapter(new Adapter(notes,MainActivity.this));

                    empty_view.setVisibility(View.GONE);
                    LinearNote.setVisibility(View.GONE);
                    TaskRecycler.setVisibility(View.VISIBLE);

                    btnClicked = false;
                }else{
                    btnList.setText("Show All");
                    empty_view.setVisibility(View.GONE);
                    LinearNote.setVisibility(View.VISIBLE);
                    TaskRecycler.setVisibility(View.GONE);

                    Collections.reverse(notes);
                    btnClicked = true;
                }


            }
        });

    }


     @Override
     protected void onResume() {
         super.onResume();
         btnClicked = true;
         btnList.setText("Show All");
         new AgentAsyncTask().execute();
     }

     private static class AgentAsyncTask extends AsyncTask<Void, Void, Void> {

         @Override
         protected Void doInBackground(Void... params) {
             notes = myAppDatabase.dao().getNotes();
             return null;
         }

         @Override
         protected void onPostExecute(Void result) {
             super.onPostExecute(result);

             if(notes.size() > 0){
                 btnList.setVisibility(View.VISIBLE);
                 empty_view.setVisibility(View.GONE);
                 LinearNote.setVisibility(View.VISIBLE);
                 TaskRecycler.setVisibility(View.GONE);

                 txtTask.setText("Tasks   "+notes.size());
                 Notes LastNote = notes.get(notes.size()-1);
                 txtTitle.setText(LastNote.getTitle());
                 txtDes.setText(LastNote.getDescription());
             }else{
                 txtTask.setText("Tasks   0");
                 btnList.setVisibility(View.INVISIBLE);
                 empty_view.setVisibility(View.VISIBLE);
                 LinearNote.setVisibility(View.GONE);
             }


         }
     }
 }