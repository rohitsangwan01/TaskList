package com.rohit.tasklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.an.customfontview.CustomTextView;
import com.rohit.tasklist.RoomDb.Notes;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    CustomTextView btnDone;
    ImageView btnCancel;
    EditText edtTitle,edtDes;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        btnDone = findViewById(R.id.btn_done);
        btnCancel = findViewById(R.id.btnCancel);
        edtDes = findViewById(R.id.edtDes);
        edtTitle =  findViewById(R.id.edtTitle);
        btnBack = findViewById(R.id.btnBack);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTitle.getText().toString().equals("")){
                    edtTitle.setError("Set Title");
                }else if(edtDes.getText().toString().equals("")){
                    edtDes.setError("Set Description");
                }else{
                    List<Notes> notes = MainActivity.myAppDatabase.dao().getNotes();
                    ArrayList Titles = new ArrayList();
                    if(notes.size() > 0){

                        for(Notes n:notes){
                            Titles.add(n.getTitle());
                        }
                        if(Titles.contains(edtTitle.getText().toString())){
                            edtTitle.setError("Same Title Existed");
                        }else{
                            String Title = edtTitle.getText().toString();
                            String Des = edtDes.getText().toString();
                            new AgentAsyncTask(Title,Des,AddTaskActivity.this).execute();
                            edtDes.setText("");
                            edtTitle.setText("");

                        }
                    }


                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTaskActivity.this,MainActivity.class));
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTaskActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Void> {

        Context context;
        private String Title;
        private String Des;

        public AgentAsyncTask(String Title, String Des,Context context) {
            this.Title = Title;
            this.Des = Des;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Notes notes = new Notes();
            notes.setDescription(Des);
            notes.setTitle(Title);
            MainActivity.myAppDatabase.dao().addNote(notes);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();

        }
    }
}