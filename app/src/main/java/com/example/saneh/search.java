package com.example.saneh;
///written by Saneh team <3
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class search extends AppCompatActivity {


    static PopupWindow popupWindow;
    static ConstraintLayout con;
    TextView date;
    public static final String TAG = "TAG";
    Date inputDate;
    Spinner selectedTime;
    Button search;
    String _classID;
    long _Capacity;
    boolean _Projector, _InterActive;
    List<Boolean> s, m, t, w, th;
    String dayOfWeek;
    TextView alert;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;
    private static final String CHANNEL_ID = "Saneh_Channel";
    private static final int NOTIFICATION_ID=001;
    CheckBox proj, inter;
    RadioGroup capaR;
    RadioButton ca20,ca30,ca40,rCheckted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        proj= findViewById(R.id.projectorSearch);
        inter= findViewById(R.id.acticityClassSearch);
        ca20 = findViewById(R.id.Search20);
        ca30=findViewById(R.id.Search30);
        ca40= findViewById(R.id.Search40);
        capaR = findViewById(R.id.radioGroupSearch);
        alert = findViewById(R.id.alertSearch);

        //prevent bottom toolbar from moving (its important)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentTime = sdf.format(new Date());
        /// here we want to know the time of the user to change drop list atomaticly
        boolean morning = false;
        int index = -1;
        switch (currentTime) {
            case "08":
                morning = true;
                index = 0;
                break;
            case "09":
                morning = true;
                index = 1;

                break;
            case "10":
                morning = true;
                index = 2;

                break;
            case "11":
                morning = true;
                index = 3;

                break;
            case "12":
                morning = true;
                index = 4;

                break;
            case "13":
                morning = true;
                index = 5;

                break;
            case "14":
                morning = true;
                index = 6;

                break;
            default:
                morning = false;

        }


        selectedTime = (Spinner) findViewById(R.id.spinnerSearch);

        search = findViewById(R.id.searchbtn);

        date = findViewById(R.id.dateSearch);
//////////////////  current day
        Date today = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
        date.setHint(simple.format(today));
        ///////////////////////
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerSearch);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(search.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        if (morning) {
            mySpinner.setSelection(index);        //////////////here we changed drop list time

////////////////////////// here we want to change class colur according to current time
            Calendar cal = Calendar.getInstance();
            int day0 = cal.get(Calendar.DAY_OF_WEEK);

            String d = "s";
            switch (day0) {
                case 1:
                    d = "s";
                    break;
                case 2:
                    d = "m";
                    break;
                case 3:
                    d = "t";
                    break;
                case 4:
                    d = "w";
                    break;
                case 5:
                    d = "th";
                    break;
                case 6:
                    d = "f";
                    break;
                case 7:
                    d = "ss";
                    break;

            }
            final int finalTimeIndex = index;
            final String finalDay = d;
            Task<QuerySnapshot> querySnapshotTask = FirebaseFirestore.getInstance()
                    .collection("classes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                int myListOfDocumentsLen = myListOfDocuments.size();
                                boolean b = false;


                                for (int i = 0; i < myListOfDocumentsLen; i++) {
                                    _classID = "class" + myListOfDocuments.get(i).getString("roomNum");


                                    int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                    TextView room = (TextView) findViewById(id);


                                    if (finalDay.equals("s")) {
                                        s = (List<Boolean>) myListOfDocuments.get(i).get("s");
                                        b = s.get(finalTimeIndex);
                                        if (b) {
                                            room.setBackgroundColor(getResources().getColor(R.color.red));
                                        } else {
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));
                                        }

                                    } else if (finalDay.equals("m")) {
                                        m = (List<Boolean>) myListOfDocuments.get(i).get("m");
                                        b = m.get(finalTimeIndex);
                                        if (b) {
                                            room.setBackgroundColor(getResources().getColor(R.color.red));
                                        } else {
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));
                                        }

                                    } else if (finalDay.equals("t")) {
                                        t = (List<Boolean>) myListOfDocuments.get(i).get("t");
                                        b = t.get(finalTimeIndex);
                                        if (b) {
                                            room.setBackgroundColor(getResources().getColor(R.color.red));
                                        } else {
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));
                                        }
                                    } else if (finalDay.equals("w")) {
                                        w = (List<Boolean>) myListOfDocuments.get(i).get("w");
                                        b = w.get(finalTimeIndex);
                                        if (b) {
                                            room.setBackgroundColor(getResources().getColor(R.color.red));
                                        } else {
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));
                                        }
                                    } else if (finalDay.equals("th")) {
                                        th = (List<Boolean>) myListOfDocuments.get(i).get("th");
                                        b = th.get(finalTimeIndex);
                                        if (b) {
                                            room.setBackgroundColor(getResources().getColor(R.color.red));
                                        } else {
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));
                                        }
                                    } else if (finalDay.equals("f")) {
                                        alert.setVisibility(View.VISIBLE);
                                        String msg = "Happy weekend ! please search for another date today is friday ";
                                        alert.setText(msg);

                                    } else if (finalDay.equals("ss")) {
                                        alert.setVisibility(View.VISIBLE);
                                        String msg = "Happy weekend ! please search for another date today is saturday ";
                                        alert.setText(msg);
                                    }

                                }

                            }


                        }


                    });

            String sdate;
            if (date.length() == 0) { // if the user did not enter a date it will be by default today's date for the user's device
                Date today1 = new Date();
                SimpleDateFormat simple1 = new SimpleDateFormat("dd-MM-yyyy");
                date.setText(simple1.format(today1));
                sdate = simple.format(today1);//
            }
            sdate = date.getText().toString().trim();
            final String finalDate1 = sdate;
            refreshAfterBooking(finalDate1);


        }//////////////////
        else {
            Calendar cal = Calendar.getInstance();
            int day0 = cal.get(Calendar.DAY_OF_WEEK);

            String d = "s";
            switch (day0) {
                case 1:
                    d = "s";
                    break;
                case 2:
                    d = "m";
                    break;
                case 3:
                    d = "t";
                    break;
                case 4:
                    d = "w";
                    break;
                case 5:
                    d = "th";
                    break;
                case 6:
                    d = "f";
                    break;
                case 7:
                    d = "ss";
                    break;

            }
            final int finalTimeIndex = index;
            final String finalDay = d;
            if (finalDay.equals("f")) {
                alert.setVisibility(View.VISIBLE);
                String msg = "Happy weekend ! please search for another date today is friday ";
                alert.setText(msg);

            } else if (finalDay.equals("ss")) {
                alert.setVisibility(View.VISIBLE);
                String msg = "Happy weekend ! please search for another date today is saturday ";
                alert.setText(msg);
            } else {
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH : mm a");
                String currentTime2 = sdf2.format(new Date());
                alert.setVisibility(View.VISIBLE);
                String msg = "There are no classes at this time " + currentTime2 + " you must search for a suitable time. ";
                alert.setText(msg);
            }

        }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(date);
            }
        });


        final ImageView profile = findViewById(R.id.imageView8);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), profile.class);
                startActivity(i);
            }
        });

        final ImageView upcoming = findViewById(R.id.imageView9);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), upcomingReservations.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                alert.setVisibility(View.INVISIBLE);
                //here we need to write a code that will take date and time and change the color of classrooms
                //date input
                String sdate;
                if (date.length() == 0) { // if the user did not enter a date it will be by default today's date for the user's device
                    Date today = new Date();
                    SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
                    date.setText(simple.format(today));
                    sdate = simple.format(today);
                    //
                }
                sdate = date.getText().toString().trim();
                final String finalDate = sdate;
                int year = Integer.parseInt(sdate.substring(6, 10));
                int month = Integer.parseInt(sdate.substring(3, 5));
                ;
                int day = Integer.parseInt(sdate.substring(0, 2));
                ;


                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, day); //Set Day of the Month, 1..31
                cal.set(Calendar.MONTH, month-1); //Set month, starts with JANUARY = 0
                cal.set(Calendar.YEAR, year); //Set year
                int day0 = cal.get(Calendar.DAY_OF_WEEK);
                String d = "s";


                switch (day0) {
                    case 1:
                        d = "s";
                        break;
                    case 2:
                        d = "m";
                        break;
                    case 3:
                        d = "t";
                        break;
                    case 4:
                        d = "w";
                        break;
                    case 5:
                        d = "th";
                        break;
                    case 6:
                        d = "f";
                        break;
                    case 7:
                        d = "ss";
                        break;


                }

                final String finalDay = d;
                if (finalDay.equals("f") || finalDay.equals("ss")) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(search.this);
                    alert.setTitle("wrong date");
                    if (finalDay.equals("f")) {
                        alert.setMessage("Friday! the weekend is not supported");
                    } else if (finalDay.equals("ss")) {
                        alert.setMessage("Saturday! the weekend is not supported");
                    }
                    alert.setPositiveButton("OK", null);
                    alert.show();

                } else {


                    //time input
                    String stime = selectedTime.getSelectedItem().toString();
                    final String finalTime = stime.substring(0, stime.indexOf(' '));

                    int timeIndex = -1;

                    switch (stime) {
                        case "8:00 AM":
                            timeIndex = 0;
                            break;

                        case "9:00 AM":
                            timeIndex = 1;
                            break;

                        case "10:00 AM":
                            timeIndex = 2;
                            break;

                        case "11:00 AM":
                            timeIndex = 3;
                            break;

                        case "12:00 PM":
                            timeIndex = 4;
                            break;

                        case "1:00 PM":
                            timeIndex = 5;
                            break;

                        case "2:00 PM":
                            timeIndex = 6;
                            break;

                    }//end switch


                    //-- here

                    //get all the Documents
                    final int finalTimeIndex = timeIndex;

                    Task<QuerySnapshot> querySnapshotTask = FirebaseFirestore.getInstance()
                            .collection("classes")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                        int myListOfDocumentsLen = myListOfDocuments.size();
                                        boolean b = false;


                                        for (int i = 0; i < myListOfDocumentsLen; i++) {
                                            _classID = "class" + myListOfDocuments.get(i).getString("roomNum");


                                            int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                            TextView room = (TextView) findViewById(id);

                                            _Projector = (boolean) myListOfDocuments.get(i).get("projector");
                                            _InterActive= (boolean) myListOfDocuments.get(i).get("interactive");
                                            _Capacity = (long) myListOfDocuments.get(i).get("capacity");
                                            int rBCh= capaR.getCheckedRadioButtonId();
                                            rCheckted=findViewById(rBCh);
                                            long Rcapa=0;
                                            if(rCheckted==ca20){
                                                Rcapa=20;
                                            }
                                            else if(rCheckted==ca30){
                                                Rcapa=30;

                                            }
                                            else if (rCheckted==ca40){
                                                Rcapa=40;
                                        }
                                            //filter
                                            /*if(proj.isChecked()&&inter.isChecked()){
                                                if(_Projector==false&&_InterActive==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }*/

                                            if (finalDay.equals("s")) {
                                                s = (List<Boolean>) myListOfDocuments.get(i).get("s");
                                                b = s.get(finalTimeIndex);
                                                    if (b) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));
                                                } else {
                                                    room.setBackgroundColor(getResources().getColor(R.color.grean));
                                                }
                                                   if(proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(proj.isChecked()&&!inter.isChecked()&&Rcapa==0){
                                                if(_Projector==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(!proj.isChecked()&&inter.isChecked()&&Rcapa==0) {
                                                     if (_InterActive == false) {
                                                         room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                     }
                                                 }

                                            } else if (finalDay.equals("m")) {
                                                m = (List<Boolean>) myListOfDocuments.get(i).get("m");
                                                b = m.get(finalTimeIndex);
                                               if (b) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));
                                                } else {
                                                    room.setBackgroundColor(getResources().getColor(R.color.grean));
                                                }
                                                     if(proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(proj.isChecked()&&!inter.isChecked()&&Rcapa==0){
                                                if(_Projector==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(!proj.isChecked()&&inter.isChecked()&&Rcapa==0){
                                                if(_InterActive==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }

                                            } else if (finalDay.equals("t")) {
                                                t = (List<Boolean>) myListOfDocuments.get(i).get("t");
                                                b = t.get(finalTimeIndex);
                                             if (b) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));
                                                } else {
                                                    room.setBackgroundColor(getResources().getColor(R.color.grean));
                                                }
                                                     if(proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(proj.isChecked()&&!inter.isChecked()&&Rcapa==0){
                                                if(_Projector==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(!proj.isChecked()&&inter.isChecked()&&Rcapa==0){
                                                if(_InterActive==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }
                                            } else if (finalDay.equals("w")) {
                                                w = (List<Boolean>) myListOfDocuments.get(i).get("w");
                                                b = w.get(finalTimeIndex);
                                               if (b) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));
                                                } else {
                                                    room.setBackgroundColor(getResources().getColor(R.color.grean));
                                                }
                                                     if(proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(proj.isChecked()&&!inter.isChecked()&&Rcapa==0){
                                                if(_Projector==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(!proj.isChecked()&&inter.isChecked()&&Rcapa==0){
                                                if(_InterActive==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }
                                            } else if (finalDay.equals("th")) {
                                                th = (List<Boolean>) myListOfDocuments.get(i).get("th");
                                                b = th.get(finalTimeIndex);
                                              if (b) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));
                                                } else {
                                                    room.setBackgroundColor(getResources().getColor(R.color.grean));
                                                }
                                                     if(proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==20){
                                                if(_Projector==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==20){
                                                if(_InterActive==false||_Capacity>20){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==30){
                                                if(_Projector==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==30){
                                                if(_InterActive==false||_Capacity>30){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(proj.isChecked()&&!inter.isChecked()&&Rcapa==40){
                                                if(_Projector==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                if(!proj.isChecked()&&inter.isChecked()&&Rcapa==40){
                                                if(_InterActive==false||_Capacity>40){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(proj.isChecked()&&!inter.isChecked()&&Rcapa==0){
                                                if(_Projector==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }else
                                                 if(!proj.isChecked()&&inter.isChecked()&&Rcapa==0){
                                                if(_InterActive==false){
                                                    room.setBackgroundColor(getResources().getColor(R.color.rectangle_116_color));
                                                }
                                            }
                                            }
                                         // Rcapa=0;
                                        }

                                    }


                                }


                            });
                    Task<QuerySnapshot> querySnapshotTask2 = FirebaseFirestore.getInstance()
                            .collection("reservations")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                        int myListOfDocumentsLen = myListOfDocuments.size();

                                        for(int i = 0; i < myListOfDocumentsLen; i++){
                                            _classID = "class" + myListOfDocuments.get(i).getString("classID");
                                            int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                            TextView room = (TextView) findViewById(id);
                                            if(finalDate.equals(myListOfDocuments.get(i).getString("date"))){
                                                if(finalTime.equals(myListOfDocuments.get(i).getString("time").substring(0,myListOfDocuments.get(i).getString("time").indexOf(' ')))) {
                                                    room.setBackgroundColor(getResources().getColor(R.color.red));



                                                }
                                            }

                                        }


                                    }
                                }
                            });
                    refreshAfterBooking(finalDate);


                }//if the day not f or ss
                refreshReservations();///here to refresh reservation

            }
        });
        refreshReservations();
        init();
    }

    private void showDateDialog(final TextView date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date.setText(simpleDateFormat.format(calendar.getTime()));


            }


        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(search.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void onButtonShowPopupWindowClick(final View view) {

        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        final int colour = color;
        if (color == -7628884) {
            Toast.makeText(search.this, "This class is unavailable", Toast.LENGTH_LONG).show();

        } else {
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.viewinfo, null);

            //calling attrbuite which in class view info
            final Button book = popupView.findViewById(R.id.bookINFO);
            TextView roomN = popupView.findViewById(R.id.roomNViewInfo);
            final TextView capacityV = popupView.findViewById(R.id.CapacityViewInfo);
            final TextView availableT = popupView.findViewById(R.id.availableTimeViewInfo);
            final View view1 = view;

            if (colour == -1754827) {//// this code to avoid delay to hide book button
                availableT.setText("Booked up");
                availableT.setTextColor(Color.parseColor("#E53935"));
                book.setVisibility(View.GONE);


                TextView t8 = popupView.findViewById(R.id.viewInfoTime8);
                TextView t9 = popupView.findViewById(R.id.viewInfoTime9);
                TextView t10 = popupView.findViewById(R.id.viewInfoTime10);
                TextView t11 = popupView.findViewById(R.id.viewInfoTime11);
                TextView t12 = popupView.findViewById(R.id.viewInfoTime12);
                TextView t1 = popupView.findViewById(R.id.viewInfoTime1);
                TextView t2 = popupView.findViewById(R.id.viewInfoTime2);

                String stime = selectedTime.getSelectedItem().toString();

                switch (stime) {
                    case "8:00 AM":
                        t8.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "9:00 AM":
                        t9.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "10:00 AM":
                        t10.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "11:00 AM":
                        t11.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "12:00 PM":
                        t12.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "1:00 PM":
                        t1.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                    case "2:00 PM":
                        t2.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;

                }//end switch


                // TextView avai = popupView.findViewById(R.id.textView11);// here to make text view in view info layout "available time :" invisibale
                // avai.setVisibility(View.INVISIBLE);
            }/////
            // change vlaues in class view info thats do not need a database
            roomN.setText("" + view.getResources().getResourceEntryName(view.getId()).substring(5));

            final String ClassID1 = view.getResources().getResourceEntryName(view.getId()).substring(5);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // H & SH code

                    final String d = date.getText().toString().trim();
                    String time = selectedTime.getSelectedItem().toString();


                    final android.app.AlertDialog.Builder alert1 = new AlertDialog.Builder(search.this);
                    alert1.setTitle("Book Class");
                    alert1.setMessage("Are you sure that you want to book this class ?");

                    final String finalTime = time;
                    alert1.setPositiveButton("Book", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           if(bookClass(ClassID1, d, finalTime)){


                             //notifications

                               notificationBefore15min(finalTime,d);
                               notificationBeforeDay(finalTime,d);

                             //end notifications

                             
                                final android.app.AlertDialog.Builder alert2 = new AlertDialog.Builder(search.this);
                                alert2.setTitle("Google calander");
                                alert2.setMessage("Do you want to save your reservation in your google calander ?");

                                alert2.setPositiveButton("save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String ftime2;
                                        // subString for the time since the format is 00:00
                                        if (finalTime.charAt(1) == ':')
                                            ftime2 = finalTime.substring(0, 1);
                                        else
                                            ftime2 = finalTime.substring(0, 2);
                                        // calendar so we can set the date in calendar to the day user want not today date
                                        final Calendar calendar = Calendar.getInstance();
                                        calendar.set(Calendar.YEAR, Integer.parseInt(d.substring(6)));
                                        calendar.set(Calendar.MONTH, Integer.parseInt(d.substring(3, 5)) - 1);
                                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d.substring(0, 2)));
                                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ftime2)==1 || Integer.parseInt(ftime2) == 2? Integer.parseInt(ftime2)+12 :Integer.parseInt(ftime2));
                                        calendar.set(Calendar.MINUTE, 00);

                                        // create an event then send it to google calendar
                                        Intent intent = new Intent(Intent.ACTION_INSERT);
                                        intent.setData(CalendarContract.Events.CONTENT_URI);
                                        intent.putExtra(CalendarContract.Events.TITLE, "My reservation in CCIS ");
                                        intent.putExtra(CalendarContract.Events.DESCRIPTION, "I booked " + view1.getResources().getResourceEntryName(view1.getId()).substring(5) + " on " + date.getText().toString().trim() + " at " + selectedTime.getSelectedItem().toString() + " using Saneh application");
                                        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, view1.getResources().getResourceEntryName(view1.getId()).substring(5));
                                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());


                                        //this if to check if the user have app can handel this action
                                        if (intent.resolveActivity(getPackageManager()) != null) {

                                            startActivity(intent);

                                        } else {
                                            alert.setVisibility(View.VISIBLE);
                                            String msg = "your reservation did not save in your application app for some issues,";
                                            alert.setText(msg);
                                        }

                                    }
                                });
                                alert2.setNegativeButton("Cancel", null);
                                alert2.setCancelable(true);

                                alert2.show();
                                popupWindow.dismiss();
                            }
                        }
                    });
                    alert1.setNegativeButton("Cancel", null);

                    alert1.setCancelable(true);

                    alert1.show();


                } // H & SH END code


            });
            //here some attrbuite values which we have to fetch it form database
            ///////////////////////////
            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            DocumentReference docRef = fStore.collection("classes").document(view.getResources().getResourceEntryName(view.getId()).substring(5));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Long capacity = document.getLong("capacity");
                            boolean proj = document.getBoolean("projector");
                            boolean activ = document.getBoolean("interactive");
                            String selectedT = selectedTime.getSelectedItem().toString();


                            //  projector.setText((proj) ? "Yes" : "No");
                            // activity.setText((activ) ? "Yes" : "No");
                            capacityV.setText(": " + capacity);
                            final ImageView projectorimg = popupView.findViewById(R.id.textView9);
                            final ImageView activityimg = popupView.findViewById(R.id.textView10);///////////********************************************************************************************
                            projectorimg.setBackgroundResource((proj) ? R.drawable.yesprogector : R.drawable.noprojectorr);
                            activityimg.setBackgroundResource((activ) ? R.drawable.yesactivity : R.drawable.noactivity);


                            String sdate;
                            if (date.length() == 0) { // if the user did not enter a date it will be by default today's date for the user's device
                                Date today = new Date();
                                SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
                                date.setText(simple.format(today));
                                sdate = simple.format(today);
                                //
                            }
                            sdate = date.getText().toString().trim();
                            int year = Integer.parseInt(sdate.substring(6, 10));
                            int month = Integer.parseInt(sdate.substring(3, 5));
                            ;
                            int day = Integer.parseInt(sdate.substring(0, 2));
                            ;


                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, day); //Set Day of the Month, 1..31
                            cal.set(Calendar.MONTH, month-1); //Set month, starts with JANUARY = 0
                            cal.set(Calendar.YEAR, year); //Set year
                            int day0 = cal.get(Calendar.DAY_OF_WEEK);
                            String d = "s";

                            switch (day0) {
                                case 1:
                                    d = "s";
                                    break;
                                case 2:
                                    d = "m";
                                    break;
                                case 3:
                                    d = "t";
                                    break;
                                case 4:
                                    d = "w";
                                    break;
                                case 5:
                                    d = "th";
                                    break;
                                case 6:
                                    d = "f";
                                    break;
                                case 7:
                                    d = "ss";
                                    break;

                            }

                            final String finalDay = d;


                            int timeIndex = -1;
                            String timeNext = " ";
                            switch (selectedT) {
                                case "8:00 AM":
                                    timeIndex = 0;
                                    timeNext = "9:00 AM";
                                    break;

                                case "9:00 AM":
                                    timeIndex = 1;
                                    timeNext = "10:00 AM";

                                    break;

                                case "10:00 AM":
                                    timeIndex = 2;
                                    timeNext = "11:00 AM";

                                    break;

                                case "11:00 AM":
                                    timeIndex = 3;
                                    timeNext = "12:00 PM";

                                    break;

                                case "12:00 PM":
                                    timeIndex = 4;
                                    timeNext = "1:00 PM";

                                    break;

                                case "1:00 PM":
                                    timeIndex = 5;
                                    timeNext = "2:00 PM";

                                    break;

                                case "2:00 PM":
                                    timeIndex = 6;
                                    timeNext = "3:00 PM";
                                    break;

                            }//end switch

                            final int finalTimeIndex = timeIndex;
                            boolean b;

                            if (finalDay.equals("s")) {
                                s = (List<Boolean>) document.get("s");

                                printTime(popupView, s, finalTimeIndex, selectedT, timeNext, availableT); // method that prints available time

                            } else if (finalDay.equals("m")) {
                                m = (List<Boolean>) document.get("m");

                                printTime(popupView, m, finalTimeIndex, selectedT, timeNext, availableT);

                            } else if (finalDay.equals("t")) {
                                t = (List<Boolean>) document.get("t");

                                printTime(popupView, t, finalTimeIndex, selectedT, timeNext, availableT);

                            } else if (finalDay.equals("w")) {
                                w = (List<Boolean>) document.get("w");

                                printTime(popupView, w, finalTimeIndex, selectedT, timeNext, availableT);

                            } else if (finalDay.equals("th")) {
                                th = (List<Boolean>) document.get("th");

                                printTime(popupView, th, finalTimeIndex, selectedT, timeNext, availableT);


                            }


                        } else {
                            Log.d(TAG, "No such document");

                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());


                    }
                }
            });
/////////////////////////////////////////////////////

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            popupWindow = new PopupWindow(popupView, 600, 1200, focusable);
            popupWindow.setTouchable(true);


            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    popupWindow.dismiss();

                    return true;
                }
            });


        }
    }

    public void init() {
        TextView classes;

        /////// initiate a clickable classes in floor 1
        for (int i = 1; i < 57; i++) {

            if (i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 || i == 31 || i == 32 || i == 33 || i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
                continue;
            int id = getResources().getIdentifier("class6F" + i, "id", getPackageName());
            classes = (TextView) findViewById(id);

            classes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonShowPopupWindowClick(view);

                }
            });

        }

        /////// initiate a clickable classes in floor G
        for (int i = 3; i < 52; i++) {

            if (i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26 || i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45)
                continue;
            int id = getResources().getIdentifier("class6G" + i, "id", getPackageName());
            classes = (TextView) findViewById(id);

            classes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonShowPopupWindowClick(view);
                }
            });

        }


    }

    public void printTime(View popupView, List<Boolean> s, int finalTimeIndex, String selectedT, String timeNext, TextView availableT) {
        int i = 0;

        TextView t8 = popupView.findViewById(R.id.viewInfoTime8);
        TextView t9 = popupView.findViewById(R.id.viewInfoTime9);
        TextView t10 = popupView.findViewById(R.id.viewInfoTime10);
        TextView t11 = popupView.findViewById(R.id.viewInfoTime11);
        TextView t12 = popupView.findViewById(R.id.viewInfoTime12);
        TextView t1 = popupView.findViewById(R.id.viewInfoTime1);
        TextView t2 = popupView.findViewById(R.id.viewInfoTime2);

        String avTime = selectedT + "-";
        for (i = 0; i < 7; i++) {
            Boolean b = s.get(i);


            switch (i) {
                case 0:
                    if (b)
                        t8.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    break;

                case 1:
                    if (b)
                        t9.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    break;

                case 2:
                    if (b)
                        t10.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    break;
                case 3:
                    if (b)
                        t11.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    break;

                case 4:
                    if (b)
                        t12.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    break;

                case 5:
                    if (b)
                        t1.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    break;

                case 6:
                    if (b)
                        t2.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    break;


            }//end switch

        }


    }

    public boolean bookClass(final String ClassID1, String Date, String time) {
        boolean bb = true ;
        firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getUid();
        fireStore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fireStore.collection("reservations").document();

        String timecut;
        if (time.charAt(1) == ':')
            timecut = time.substring(0, 1);
        else
            timecut = time.substring(0, 2);

        //*************************
        String aa;
        if (timecut.equals("12") || timecut.equals("1") || timecut.equals("2")) {
            aa = " PM";
        } else {
            aa = " AM";
        }
        String selectedTD = Date + " " + timecut + aa;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh aa");
        String noww = simpleDateFormat.format(new Date().getTime());
        boolean b = false;
        String minutes;
        try {
            if (simpleDateFormat.parse(noww).getTime() == simpleDateFormat.parse(selectedTD).getTime()) {
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-M-yyyy hh:mm aa");
                minutes = simpleDateFormat2.format(new Date().getTime());
                minutes = minutes.substring(minutes.indexOf(':') + 1, (minutes.indexOf(':') + 3));
                noww = timecut + ":" + minutes;

                b = true;


            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


//************************************
        int timeparse = Integer.parseInt(timecut);
        if (timeparse < 12) {
            timeparse++;
            if (timeparse == 12) {
                if (b) {
                    time = noww + " - " + timeparse + ":00 " + "PM";
                } else {
                    time = time.substring(0, time.length() - 2) + " - " + timeparse + ":00 " + "PM";
                }
            } else {
                if (b) {
                    time = noww + " - " + timeparse + ":00 " + time.substring(time.length() - 2);

                } else {
                    time = time.substring(0, time.length() - 2) + " - " + timeparse + ":00 " + time.substring(time.length() - 2);
                }
            }
        } else {
            if (b) {
                time = noww + " - " + "1:00 " + "PM";
            } else {
                time = time.substring(0, time.length() - 2) + " - " + "1:00 " + "PM";
            }


        }

        Map<String, Object> newReservation = new HashMap<>();

        newReservation.put("classID", ClassID1);
        newReservation.put("confirmed", false);
        newReservation.put("date", Date);
        newReservation.put("time", time);
        newReservation.put("userID", currentUserId);
        newReservation.put("roomType", "classroom");

        if(!checkDateAndTimebeforeBoikng(time,Date)){


        documentReference.set(newReservation)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(search.this, "class booked successfully", Toast.LENGTH_LONG).show();
                        refreshAfterBooking(date.getText().toString().trim());


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Toast.makeText(adminAdd.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());


                    }
                }); }else bb = false ;
        return bb ;
    }

    public void refreshAfterBooking(String finalDate) {
        final String fd = finalDate;
        String stime = selectedTime.getSelectedItem().toString();
        final String finalTime1 = stime.substring(0, stime.indexOf(' '));
        Task<QuerySnapshot> querySnapshotTask2 = FirebaseFirestore.getInstance()
                .collection("reservations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            int myListOfDocumentsLen = myListOfDocuments.size();

                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                _classID = "class" + myListOfDocuments.get(i).getString("classID");
                                int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                TextView room = (TextView) findViewById(id);
                                if (fd.equals(myListOfDocuments.get(i).getString("date"))) {
                                    String finalT2;
                                    if (finalTime1.charAt(1) == ':') {
                                        finalT2 = "0" + finalTime1.substring(0, 1);
                                    } else {
                                        finalT2 = finalTime1.substring(0, 2);
                                    }
                                    String fromD;
                                    String fromDataBase = myListOfDocuments.get(i).getString("time").substring(0, myListOfDocuments.get(i).getString("time").indexOf(' '));
                                    if (fromDataBase.charAt(1) == ':') {
                                        fromD = "0" + fromDataBase.substring(0, 1);
                                    } else {
                                        fromD = fromDataBase.substring(0, 2);
                                    }

                                    if (finalT2.equals(fromD)) {
                                        room.setBackgroundColor(getResources().getColor(R.color.red));


                                    }
                                }

                            }


                        }
                    }
                });


    }

    public boolean checkDateAndTimebeforeBoikng(String time , String resDate){
        boolean b = false ;
        String reservationTime = time ;
        String nextReservationTime1 = time.substring(time.indexOf("-")+2);

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
        String noww = simpleDateFormat.format(new Date().getTime());
        long now = 0;
        long res = 0;
        long date2 = 0;
        String aa ;
        String aa2 ;
        if(reservationTime.equals("12") || reservationTime.equals("01") ||reservationTime.equals("02") ) {
            aa = " PM";
        }else{ aa= " AM";}
        if(nextReservationTime1.equals("12") || nextReservationTime1.equals("01") ||nextReservationTime1.equals("02") || nextReservationTime1.equals("03")) {
            aa2 = " PM";
        }else{ aa2= " AM";}



        String reDate = resDate+ " " + reservationTime +time.substring(time.indexOf(":"),time.indexOf(":")+3)+aa ;
        String Date2 = resDate+" "+nextReservationTime1+":00"+aa2;
        try {
            now = simpleDateFormat.parse(noww).getTime();
            res = simpleDateFormat.parse(reDate).getTime();
            date2 = simpleDateFormat.parse(Date2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(now > res){

            long remining2 = date2 - now  ;
            long minutesTomillies = TimeUnit.MINUTES.toMillis(15);
            long total = res + minutesTomillies ;

            if(remining2 <= 0){
                Toast.makeText(getApplication(),"Time is invalid",Toast.LENGTH_LONG).show();
                 b = true ;

            }
        }
return b ;

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Saneh Notification";
            String description = "this notification is provided by Snaeh APP when you book a class in CCIS";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void notification(String ClassID1, String d){
        //Set the notification content
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.bill)


                .setContentTitle("Saneh")
                .setContentText("you booked class "+ClassID1 + " on "+d)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public void refreshReservations() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Task<QuerySnapshot> querySnapshotTask2 = FirebaseFirestore.getInstance()
                .collection("reservations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            int myListOfDocumentsLen = myListOfDocuments.size();
                            String resDate;
                            String time;
                            boolean isConfirmed;
                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                resDate = myListOfDocuments.get(i).getString("date");
                                time = myListOfDocuments.get(i).getString("time");
                                isConfirmed = myListOfDocuments.get(i).getBoolean("confirmed");

                                String reservationTime = time ;
                                String nextReservationTime1 = time.substring(time.indexOf("-")+2);

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
                                String aa2 ;
                                String aa ;
                                if(reservationTime.equals("12") || reservationTime.equals("01") ||reservationTime.equals("02") ) {
                                    aa = " PM";
                                }else{ aa= " AM";}
                                if(nextReservationTime1.equals("12") || nextReservationTime1.equals("01") ||nextReservationTime1.equals("02") || nextReservationTime1.equals("03")  ) {
                                    aa2 = " PM";
                                }else{ aa2= " AM";}

                                String reDate = resDate+ " " + reservationTime +time.substring(time.indexOf(":"),time.indexOf(":")+3)+aa ;
                                String Date2 = resDate+" "+nextReservationTime1+":00"+aa2;
                                try {
                                    now = simpleDateFormat.parse(noww).getTime();
                                    res = simpleDateFormat.parse(reDate).getTime();
                                    date2 = simpleDateFormat.parse(Date2).getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if(now > res){
                                    _classID = "class" + myListOfDocuments.get(i).getString("classID");
                                    int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                    TextView room = (TextView) findViewById(id);

                                    long remining2 = date2 - now  ;
                                    long minutesTomillies = TimeUnit.MINUTES.toMillis(15);
                                    long total = res + minutesTomillies ;

                                    if(now > total )
                                        if(!isConfirmed){
                                             myListOfDocuments.get(i).getReference().delete();
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));

                                        }
                                    if(remining2 <= 0){
                                         myListOfDocuments.get(i).getReference().delete();
                                        room.setBackgroundColor(getResources().getColor(R.color.grean));


                                    }
                                }



                            } // for loop close

                        }// if (task successful ) close

                    }

                });

    }

    public void notificationBefore15min(final String finalTime,final String d ){
        createNotificationChannel();
        Intent intent1 = new Intent (search.this,Remainder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(search.this,0,intent1,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //get reservation time
        String ftime2;
        // subString for the time since the format is 00:00
        if (finalTime.charAt(1) == ':')
            ftime2 = finalTime.substring(0, 1);
        else
            ftime2 = finalTime.substring(0, 2);
        int timetodecrease = Integer.parseInt(ftime2);
        if(timetodecrease == 1 ||timetodecrease == 2 || timetodecrease == 3 ) {
            timetodecrease = 12 + timetodecrease;
        }
        timetodecrease= --timetodecrease;

        // calendar so we can set the date in notification to the day user booked class
        final Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.YEAR, Integer.parseInt(d.substring(6)));
        cal_alarm.set(Calendar.MONTH, Integer.parseInt(d.substring(3, 5)) - 1);
        cal_alarm.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d.substring(0, 2)));
        cal_alarm.set(Calendar.HOUR_OF_DAY, timetodecrease);
        cal_alarm.set(Calendar.MINUTE, 45);
        cal_alarm.set(Calendar.SECOND,0);

        alarmManager.set(AlarmManager.RTC,cal_alarm.getTimeInMillis(),pendingIntent);
    }
    public void notificationBeforeDay(final String finalTime,final String d ){
        createNotificationChannel();
        Intent intent1 = new Intent (search.this,Remainder2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(search.this,0,intent1,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //get reservation time
        String ftime2;
        // subString for the time since the format is 00:00
        if (finalTime.charAt(1) == ':')
            ftime2 = finalTime.substring(0, 1);
        else
            ftime2 = finalTime.substring(0, 2);
        int timeInt = Integer.parseInt(ftime2);
        if(timeInt == 1 ||timeInt == 2 || timeInt == 3 ) {
            timeInt = 12 + timeInt;
        }

        // calendar so we can set the date in notification to the day before user booked class
        final Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.YEAR, Integer.parseInt(d.substring(6)));
        cal_alarm.set(Calendar.MONTH, Integer.parseInt(d.substring(3, 5)) - 1);
        cal_alarm.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d.substring(0, 2)) - 1);
        cal_alarm.set(Calendar.HOUR_OF_DAY,  timeInt );
        cal_alarm.set(Calendar.MINUTE, 0);
        cal_alarm.set(Calendar.SECOND,0);

        alarmManager.set(AlarmManager.RTC,cal_alarm.getTimeInMillis(),pendingIntent);
    }


}