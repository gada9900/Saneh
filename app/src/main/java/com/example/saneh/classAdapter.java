package com.example.saneh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class classAdapter extends FirestoreRecyclerAdapter<reservations, classAdapter.classHolder> {

    public classAdapter(@NonNull FirestoreRecyclerOptions<reservations> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull classHolder holder, int position, @NonNull reservations model) {
        holder.textViewClassID.setText(model.getClassID());
        holder.textViewDate.setText("Date: "+model.getDate());
        holder.textViewTime.setText("Time: "+model.getTime());
    }

    @NonNull
    @Override
    public classHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,
                parent, false);
        return new classHolder(v);
    }

    class classHolder extends RecyclerView.ViewHolder {
        TextView textViewClassID;
        TextView textViewDate;
        TextView textViewTime;

        public classHolder(View itemView) {
            super(itemView);
            textViewClassID = itemView.findViewById(R.id.class_id);
            textViewDate = itemView.findViewById(R.id.res_date);
            textViewTime = itemView.findViewById(R.id.res_time);
        }
    }
}