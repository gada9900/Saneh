package com.example.saneh;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class search extends AppCompatActivity {


    static PopupWindow popupWindow ;
    static ConstraintLayout con ;
    TextView date;
    public static final String TAG = "TAG";
    Date inputDate;
    Spinner selectedTime;
    Button search;
    String _classID ;
    long _Capacity;
    boolean _Projector , _InterActive;
    List<Boolean> s,m,t,w,th;
    String dayOfWeek;
    TextView  alert  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


          alert = findViewById(R.id.alertSearch) ;

        //prevent bottom toolbar from moving (its important)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
          String currentTime = sdf.format(new Date());
          /// here we want to know the time of the user to change drop list atomaticly
           boolean morning = false ;
           int index = -1 ;
        switch (currentTime) {
            case "08":
                morning = true ;
                index =0 ;
                break;
            case "09":
                morning = true ;
                index =1 ;

                break;
            case "10":
                morning = true ;
                index =2 ;

                break;
            case "11":
                morning = true ;
                index =3 ;

                break;
            case "12":
                morning = true ;
                index =4 ;

                break;
            case "13":
                morning = true ;
                index =5 ;

                break;
            case "14":
                morning = true ;
                index =6 ;

                break;
            default: morning = false ;

        }


        selectedTime =(Spinner) findViewById(R.id.spinnerSearch);

        search = findViewById(R.id.searchbtn);

        date=findViewById(R.id.dateSearch);
//////////////////  current day
        Date today = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
        date.setHint(simple.format(today));
        ///////////////////////
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerSearch);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(search.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        if(morning){
    mySpinner.setSelection(index);        //////////////here we changed drop list time

////////////////////////// here we want to change class colur according to current time
            Calendar cal = Calendar.getInstance();
            int day0 = cal.get(Calendar.DAY_OF_WEEK);

            String d ="s";
            switch (day0) {
                case 1:
                    d="s";
                    break;
                case 2:
                    d="m";
                    break;
                case 3:
                    d="t";
                    break;
                case 4:
                    d="w";
                    break;
                case 5:
                    d="th";
                    break;
                case 6:
                    d="f";
                    break;
                case 7:
                    d="ss";
                    break;

            }
            final int finalTimeIndex = index;
            final String finalDay =d;
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
                                boolean b=false;




                                for(int i=0; i<myListOfDocumentsLen; i++){
                                    _classID = "class"+myListOfDocuments.get(i).getString("roomNum");


                                    int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                    TextView room = (TextView) findViewById(id);



                                    if (finalDay.equals("s")) {
                                        s = (List<Boolean>) myListOfDocuments.get(i).get("s");
                                        b = s.get(finalTimeIndex);
                                        if(b){room.setBackgroundColor(getResources().getColor(R.color.red));}else{room.setBackgroundColor(getResources().getColor(R.color.grean));}

                                    } else if (finalDay.equals("m")) {
                                        m = (List<Boolean>) myListOfDocuments.get(i).get("m");
                                        b = m.get(finalTimeIndex);
                                        if(b){room.setBackgroundColor(getResources().getColor(R.color.red));}else{room.setBackgroundColor(getResources().getColor(R.color.grean));}

                                    } else if  (finalDay.equals("t")) {
                                        t = (List<Boolean>) myListOfDocuments.get(i).get("t");
                                        b = t.get(finalTimeIndex);
                                        if(b){room.setBackgroundColor(getResources().getColor(R.color.red));}else{room.setBackgroundColor(getResources().getColor(R.color.grean));}
                                    }  else if  (finalDay.equals("w")) {
                                        w = (List<Boolean>) myListOfDocuments.get(i).get("w");
                                        b = w.get(finalTimeIndex);
                                        if(b){room.setBackgroundColor(getResources().getColor(R.color.red));}else{room.setBackgroundColor(getResources().getColor(R.color.grean));}
                                    }else if  (finalDay.equals("th")){
                                        th = (List<Boolean>) myListOfDocuments.get(i).get("th");
                                        b = th.get(finalTimeIndex);
                                        if(b){room.setBackgroundColor(getResources().getColor(R.color.red));}else{room.setBackgroundColor(getResources().getColor(R.color.grean));}
                                    }else if  (finalDay.equals("f")){
                                        alert.setVisibility(View.VISIBLE);
                                        String msg = "Happy weekend ! please search for another date today is friday ";
                                        alert.setText(msg);

                                    }else if  (finalDay.equals("ss")){
                                        alert.setVisibility(View.VISIBLE);
                                        String msg = "Happy weekend ! please search for another date today is saturday ";
                                        alert.setText(msg);
                                    }

                                }

                            }


                        }


                    });



        }//////////////////
        else{
            Calendar cal = Calendar.getInstance();
            int day0 = cal.get(Calendar.DAY_OF_WEEK);

            String d ="s";
            switch (day0) {
                case 1:
                    d="s";
                    break;
                case 2:
                    d="m";
                    break;
                case 3:
                    d="t";
                    break;
                case 4:
                    d="w";
                    break;
                case 5:
                    d="th";
                    break;
                case 6:
                    d="f";
                    break;
                case 7:
                    d="ss";
                    break;

            }
            final int finalTimeIndex = index;
            final String finalDay =d;
            if  (finalDay.equals("f")){
                alert.setVisibility(View.VISIBLE);
                String msg = "Happy weekend ! please search for another date today is friday ";
                alert.setText(msg);

            }else if  (finalDay.equals("ss")){
                alert.setVisibility(View.VISIBLE);
                String msg = "Happy weekend ! please search for another date today is saturday ";
                alert.setText(msg);
            }else{
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH : mm a");
            String currentTime2 = sdf2.format(new Date());
            alert.setVisibility(View.VISIBLE);
            String msg = "There are no classes at this time "+currentTime2+" you must search for a suitable time. ";
            alert.setText(msg);}

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
                Intent i = new Intent(getApplicationContext(),profile.class);
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
                String sdate ;
               if(date.length() == 0 ) { // if the user did not enter a date it will be by default today's date for the user's device
                   Date today = new Date();
                   SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
                   date.setText(simple.format(today));
                   sdate = simple.format(today);
                   //
               }
                 sdate = date.getText().toString().trim();
                int year=Integer.parseInt(sdate.substring(6,10));
                int month=Integer.parseInt(sdate.substring(3,5));;
                int day=Integer.parseInt(sdate.substring(0,2));;


                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, day); //Set Day of the Month, 1..31
                cal.set(Calendar.MONTH,month); //Set month, starts with JANUARY = 0
                cal.set(Calendar.YEAR,year); //Set year
                int day0 = cal.get(Calendar.DAY_OF_WEEK);
                String d ="s";


                switch (day0) {
                    case 1:
                        d="th";
                        break;
                    case 2:
                        d="f";
                        break;
                    case 3:
                        d="ss";
                        break;
                    case 4:
                        d="s";
                        break;
                    case 5:
                        d="m";
                        break;
                    case 6:
                        d="t";
                        break;
                    case 7:
                        d="w";
                        break;


                }

                final String finalDay =d;

                if (finalDay.equals("f")|| finalDay.equals("ss")){

                    AlertDialog.Builder alert = new AlertDialog.Builder(search.this);
                    alert.setTitle("wrong date");
                    if (finalDay.equals("f")){
                    alert.setMessage("Friday! the weekend is not supported");}
                    else if (finalDay.equals("ss")){
                        alert.setMessage("Saturday! the weekend is not supported");}
                    alert.setPositiveButton("OK",null);
                    alert.show();

                }else {


                    //time input
                    String stime = selectedTime.getSelectedItem().toString();
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
                                            }

                                        }

                                    }


                                }




                            });
                }//if the day not f or ss
            }});


       init();
    }

    private void showDateDialog(final TextView date) {
        final Calendar calendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                date.setText(simpleDateFormat.format(calendar.getTime()));


            }


        };

        DatePickerDialog datePickerDialog = new  DatePickerDialog(search.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void onButtonShowPopupWindowClick(View view  ) {

        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        final int colour = color;
        if(color == -7628884) {
            Toast.makeText(search.this, "This class is unavailable", Toast.LENGTH_LONG).show();

        }else {
            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.viewinfo, null);

            //calling attrbuite which in class view info
            final Button book = popupView.findViewById(R.id.bookINFO);
            TextView roomN = popupView.findViewById(R.id.roomNViewInfo);
            final TextView capacityV = popupView.findViewById(R.id.CapacityViewInfo);
            final TextView availableT = popupView.findViewById(R.id.availableTimeViewInfo);

            if(colour == -1754827) {//// this code to avoid delay to hide book button
                availableT.setText("Booked up");
                availableT.setTextColor(Color.parseColor("#E53935"));
                book.setVisibility(View.GONE);
               // TextView avai = popupView.findViewById(R.id.textView11);// here to make text view in view info layout "available time :" invisibale
               // avai.setVisibility(View.INVISIBLE);
            }/////
            // change vlaues in class view info thats do not need a database
            roomN.setText("" + view.getResources().getResourceEntryName(view.getId()).substring(5));
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), search.class);
                    startActivity(i);
                }
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
                            cal.set(Calendar.MONTH, month); //Set month, starts with JANUARY = 0
                            cal.set(Calendar.YEAR, year); //Set year
                            int day0 = cal.get(Calendar.DAY_OF_WEEK);
                            String d = "s";

                            switch (day0) {
                                case 1:
                                    d = "th";
                                    break;
                                case 2:
                                    d = "f";
                                    break;
                                case 3:
                                    d = "ss";
                                    break;
                                case 4:
                                    d = "s";
                                    break;
                                case 5:
                                    d = "m";
                                    break;
                                case 6:
                                    d = "t";
                                    break;
                                case 7:
                                    d = "w";
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

                                    printTime(popupView , s, finalTimeIndex, selectedT, timeNext, availableT); // method that prints available time

                                } else if (finalDay.equals("m")) {
                                    m = (List<Boolean>) document.get("m");

                                    printTime(popupView , m, finalTimeIndex, selectedT, timeNext, availableT);

                                } else if (finalDay.equals("t")) {
                                    t = (List<Boolean>) document.get("t");

                                    printTime(popupView, t, finalTimeIndex, selectedT, timeNext, availableT);

                                } else if (finalDay.equals("w")) {
                                    w = (List<Boolean>) document.get("w");

                                    printTime(popupView ,w, finalTimeIndex, selectedT, timeNext, availableT);

                                } else if (finalDay.equals("th")) {
                                    th = (List<Boolean>) document.get("th");

                                    printTime(popupView , th, finalTimeIndex, selectedT, timeNext, availableT);


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

    public void init(){
        TextView classes ;

        /////// initiate a clickable classes in floor 1
        for( int i = 1 ; i < 57 ; i++) {

            if( i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 ||i == 31 ||i == 32 ||i == 33 ||i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 ||i == 46 ||i == 47 )
                continue;
            int id = getResources().getIdentifier("class6F"+i, "id", getPackageName());
            classes = (TextView) findViewById(id);

            classes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonShowPopupWindowClick(view);

                }
            });

        }

        /////// initiate a clickable classes in floor G
        for( int i = 3 ; i < 52 ; i++) {

            if( i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 ||i == 24 ||i == 25 ||i == 26 ||i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45 )
                continue;
            int id = getResources().getIdentifier("class6G"+i, "id", getPackageName());
            classes = (TextView) findViewById(id);

            classes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonShowPopupWindowClick(view);
                }
            });

        }



    }
public void printTime(View popupView ,List<Boolean> s , int finalTimeIndex , String selectedT , String timeNext ,TextView  availableT){
    int i= 0 ;

    TextView t8 =popupView.findViewById(R.id.viewInfoTime8);


    TextView t9 = popupView.findViewById(R.id.viewInfoTime9);
    TextView t10 = popupView.findViewById(R.id.viewInfoTime10);
    TextView t11 = popupView.findViewById(R.id.viewInfoTime11);
    TextView t12 = popupView.findViewById(R.id.viewInfoTime12);
    TextView t1 = popupView.findViewById(R.id.viewInfoTime1);
    TextView t2 = popupView.findViewById(R.id.viewInfoTime2);

    String avTime = selectedT+"-";
    for( i = 0 ; i < 7 ; i++){
        Boolean b = s.get(i);


        switch (i){
            case 0 :
                if(b)
                    t8.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                break;

            case 1 :
                if(b)
                    t9.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                break;

            case 2 :
                if(b)
                    t10.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                   break;
            case 3 :
                if(b)
                    t11.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                break;

            case 4 :
                if(b)
                    t12.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                break;

            case 5 :
                if(b)
                    t1.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;

            case 6 :
                if(b)
                    t2.setPaintFlags(t8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                break;


        }//end switch

    }



}


}