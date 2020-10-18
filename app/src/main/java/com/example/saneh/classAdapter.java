package com.example.saneh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class classAdapter extends FirestoreRecyclerAdapter<reservations, classAdapter.classHolder> {

    public classAdapter(@NonNull FirestoreRecyclerOptions<reservations> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final classHolder holder, final int position, @NonNull reservations model) {
        holder.textViewClassID.setText("Room ID: "+model.getClassID());
        holder.textViewDate.setText("Date: "+model.getDate());
        holder.textViewTime.setText("Time: "+model.getTime());
        holder.textViewRoomType.setText("Room type: "+ model.getRoomType());
        holder.textViewConfirmedMsg.setText("Confirmed");
        holder.textViewConfirmedMsg.setVisibility(View.GONE);

        if(model.isConfirmed()){
            holder.confirm.setVisibility(View.GONE);
            //holder.textViewConfirmedMsg.setText("Confirmed");
            holder.textViewConfirmedMsg.setVisibility(View.VISIBLE);
            return;
        }
        else {
            holder.confirm.setVisibility(View.VISIBLE);
        }

        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Confirm reservation");
                alert.setMessage("To confirm your reservation, you must be at the reserved room\n\nAre you in the reserved room right now?");

                alert.setPositiveButton("Yes, confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmItem(position);
                        //holder.confirm.setVisibility(View.GONE);
                        //holder.textViewConfirmedMsg.setVisibility(View.VISIBLE);
                    }
                });
                alert.setNegativeButton("No, cancel",null);
                alert.show();

            }
        });
    }

    @NonNull
    @Override
    public classHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,
                parent, false);
        return new classHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void confirmItem(int position){
        getSnapshots().getSnapshot(position).getReference().update("confirmed", true);
    }

    class classHolder extends RecyclerView.ViewHolder {
        TextView textViewResID;
        TextView textViewClassID;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewRoomType;
        TextView textViewConfirmedMsg;
        Button confirm, cancel;

        public classHolder(View itemView) {
            super(itemView);
            textViewClassID = itemView.findViewById(R.id.class_id);
            textViewDate = itemView.findViewById(R.id.res_date);
            textViewTime = itemView.findViewById(R.id.res_time);
            textViewRoomType = itemView.findViewById(R.id.res_room_type);
            textViewConfirmedMsg = itemView.findViewById(R.id.confirmed_msg);
            confirm = itemView.findViewById(R.id.confirm_button);
            /*cancel = itemView.findViewById(R.id.cancel_button);
            <Button
                        android:id="@+id/cancel_button"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_below="@+id/confirm_button"
                        android:layout_marginTop="8dp"
                        android:background="@drawable/rectangle_2004_shape"
                        android:text="Cancel"
                        android:textColor="#FDFCFC"
                        android:layout_alignParentRight="true"/> */


        }
    }
}