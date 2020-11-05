package com.example.saneh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class classAdapter extends FirestoreRecyclerAdapter<reservations, classAdapter.classHolder> {

    public classAdapter(@NonNull FirestoreRecyclerOptions<reservations> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final classHolder holder, final int position, @NonNull final reservations model) {
        //*******************************************************
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String reservationTime = model.getTime();
        String nextReservationTime1 = model.getTime().substring(model.getTime().indexOf("-")+2);

        if (reservationTime.charAt(1) == ':') {
            reservationTime = "0" + reservationTime.substring(0, 1);
        } else {
            reservationTime = reservationTime.substring(0, 2);
        }
        if (nextReservationTime1.charAt(1) == ':') {
            nextReservationTime1 = "0" + nextReservationTime1.substring(0, 1);
        } else {
            nextReservationTime1 = nextReservationTime1.substring(0, 2);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm aa");
        Util g12 = new Util();
            final long ff = g12.getCurrentNetworkTime();
            String noww= simpleDateFormat.format(ff);
        //String noww = simpleDateFormat.format(new Date().getTime());
        long now = 0;
        long res = 0;
        long date2 = 0;
        String aa ;
        String aa2 ;
        if(reservationTime.equals("12") || reservationTime.equals("01") ||reservationTime.equals("02") ) {
            aa = " PM";
        }else{ aa= " AM";}
        if(nextReservationTime1.equals("12") || nextReservationTime1.equals("01") ||nextReservationTime1.equals("02") || nextReservationTime1.equals("03") ) {
            aa2 = " PM";
        }else{ aa2= " AM";}

        final String reDate = model.getDate() + " " + reservationTime +model.getTime().substring(model.getTime().indexOf(":"),model.getTime().indexOf(":")+3)+aa ;
        String Date2 = model.getDate()+" "+nextReservationTime1+":00"+aa2;
        try {
            now = simpleDateFormat.parse(noww).getTime();
            res = simpleDateFormat.parse(reDate).getTime();
            date2 = simpleDateFormat.parse(Date2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

            ////////////////////////////////////////******************************************
            holder.textViewClassID.setText("Room ID: " + model.getClassID());
            holder.textViewDate.setText("Date: " + model.getDate());
            holder.textViewTime.setText("Time: " + model.getTime());
            holder.textViewRoomType.setText("Room type: " + model.getRoomType());
            //holder.textViewConfirmedMsg.setText("Confirmed");
            holder.textViewConfirmedMsg.setVisibility(View.GONE);
/////*****************************************
        if(now > res){

           // long remaining = now - res;
            long remining2 = date2 - now  ;
           // long minutes = TimeUnit.MILLISECONDS.toMinutes(remaining);
            long minutesTomillies = TimeUnit.MINUTES.toMillis(15);
            long total = res + minutesTomillies ;

            if(now > total )
                if(!model.isConfirmed() ){
                    deleteItem(position);
                }
            if(remining2 <= 0){
                deleteItem(position);

            }
        }
//*************************************************

        shareInfo(holder, model);

        if (model.isConfirmed()) {
                holder.confirm.setVisibility(View.GONE);
                //holder.textViewConfirmedMsg.setText("Confirmed");
                holder.textViewConfirmedMsg.setVisibility(View.VISIBLE);
                shareInfo(holder, model);
                return;
            } else {
                holder.confirm.setVisibility(View.VISIBLE);
            }

        final long finalNow = now;
        final long finalRes = res;
        holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (finalNow >= finalRes) {

                    android.app.AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setTitle("Confirm reservation");
                    alert.setMessage("To confirm your reservation, you must be at the reserved room\n\nAre you in the reserved room right now?");

                    alert.setPositiveButton("Yes, confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirmItem(position);
                            shareInfo(holder, model);
                            //holder.confirm.setVisibility(View.GONE);
                            //holder.textViewConfirmedMsg.setVisibility(View.VISIBLE);
                        }
                    });
                    alert.setNegativeButton("No, cancel", null);
                    alert.show();
                } else {
                        android.app.AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                        alert.setTitle("Can not confirm reservation");
                        alert.setMessage("The reservation time hasn't started yet\n\nTry again at: "+reDate );

                        alert.setNegativeButton("Ok", null);
                        alert.show();
                    }
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

    public void shareInfo(classHolder holder, final reservations model) {
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSub = "My reservation in CCIS";
                String shareBody = "Join me in my CCIS reservation \n Room id: "+ model.getClassID()+"\n Date: " + model.getDate() +"\n Time: " + model.getTime() +"\n Room type: " + model.getRoomType()+"\n\nBooked via Saneh app";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share my reservation using:"));
            }
        });
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
        ImageView share;
        Button confirm, cancel;

        public classHolder(View itemView) {
            super(itemView);
            textViewClassID = itemView.findViewById(R.id.class_id);
            textViewDate = itemView.findViewById(R.id.res_date);
            textViewTime = itemView.findViewById(R.id.res_time);
            textViewRoomType = itemView.findViewById(R.id.res_room_type);
            textViewConfirmedMsg = itemView.findViewById(R.id.confirmed_msg);
            confirm = itemView.findViewById(R.id.confirm_button);
            share = itemView.findViewById(R.id.res_share);
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
class Util {
    //NTP server list: http://tf.nist.gov/tf-cgi/servers.cgi
    public static final String TIME_SERVER = "pool.ntp.org";

    public static long getCurrentNetworkTime() {
        NTPUDPClient lNTPUDPClient = new NTPUDPClient();
        lNTPUDPClient.setDefaultTimeout(3000);
        long returnTime = 0;
        try {
            lNTPUDPClient.open();
            InetAddress lInetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo lTimeInfo = lNTPUDPClient.getTime(lInetAddress);
            // returnTime =  lTimeInfo.getReturnTime(); // local time
            returnTime = lTimeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lNTPUDPClient.close();
        }

        return returnTime;
    }

}