package com.example.saneh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class search extends AppCompatActivity {

    static PopupWindow popupWindow ;
    static ConstraintLayout con ;
    EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        date=findViewById(R.id.dateSearch);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerSearch);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(search.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

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



       init();

    }

    private void showDateDialog(final EditText date) {
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

        new DatePickerDialog(search.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void onButtonShowPopupWindowClick(View view  ) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.viewinfo, null);

        //calling attrbuite which in class view info
        Button book = popupView.findViewById(R.id.bookINFO);
        TextView roomN = popupView.findViewById(R.id.roomNViewInfo);



        // change vlaues in class view info
        roomN.setText(""+view.getResources().getResourceEntryName(view.getId()).substring(5));
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), search.class);
                startActivity(i);
            }
        });



        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, 600, 1000, focusable);
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







}