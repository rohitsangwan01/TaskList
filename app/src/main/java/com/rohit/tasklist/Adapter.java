package com.rohit.tasklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohit.tasklist.RoomDb.Notes;

import java.util.Collections;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Notes> notes ;
    Context context;

    public Adapter(List<Notes> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.note_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.item_title.setText(notes.get(position).getTitle());
        holder.item_desc.setText(notes.get(position).getDescription());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes n = new Notes();
                n.setTitle(notes.get(position).getTitle());
                MainActivity.myAppDatabase.dao().DeleteNote(n);
                notes.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title,item_desc;
        ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.txtTitle);
            item_desc = itemView.findViewById(R.id.item_desc);
            btnDelete = itemView.findViewById(R.id.imgDlt);
        }
    }
}

