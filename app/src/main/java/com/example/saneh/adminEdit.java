package com.example.saneh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class adminEdit extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private ImageView edit, settings;
    static PopupWindow popupWindow ;
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit);

        firebaseFirestore = FirebaseFirestore.getInstance();

        settings = findViewById(R.id.imageView8);
        edit = findViewById(R.id.imageView9);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminEdit.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_settings.class);
                startActivity(i);
            }
        });

        clickableClass();

    }



        public void clickableClass(){
            TextView classes ;

            /////// initiate a clickable classes in floor 1
            for( int i = 1 ; i < 57 ; i++) {

                if( i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 ||i == 31 ||i == 32 ||i == 33 ||i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 ||i == 46 ||i == 47 ){
                    continue;
                }
                int id = getResources().getIdentifier("class6F"+i, "id", getPackageName());
                final String classID = "6F"+i;
                classes = (TextView) findViewById(id);

                if(i == 49 || i == 50 || i == 48 || i == 25 || i == 24 || i == 21 ||i == 20 ||i == 13 ||i == 12|| i == 5 || i == 3|| i == 10 || i == 11  ) {
                    classes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onButtonShowPopupWindowClick(view , classID);
                        }
                    });
                }else {
                    classes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), editClassInfo.class);
                            i.putExtra("classID", classID);
                            i.putExtra("type", "old");
                            startActivity(i);
                        }
                    });
                }
            }

            /////// initiate a clickable classes in floor G
            for( int i = 3 ; i < 52 ; i++) {

                if( i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 ||i == 24 ||i == 25 ||i == 26 ||i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45 )
                    continue;

                int id = getResources().getIdentifier("class6G" + i, "id", getPackageName());
                final String classID = "6G" + i;
                classes = (TextView) findViewById(id);

                if(i == 18 || i == 12 || i == 15 || i == 35 || i == 41 || i == 42 ||i == 43 ||i == 44 ||i == 46 ) {
                    classes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onButtonShowPopupWindowClick(view , classID);
                        }
                    });
                }else {
                    classes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), editClassInfo.class);
                            i.putExtra("classID", classID);
                            i.putExtra("type", "old");
                            startActivity(i);
                        }
                    });
                }

            }

        }

    public void onButtonShowPopupWindowClick(View view , final String classID) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.adminwindow, null);

        //calling attrbuite which in class view info
        Button AddClass = popupView.findViewById(R.id.AddClass);

        // change vlaues in class view info
        AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(adminEdit.this,adminAdd.class);
                        //getApplicationContext(), editClassInfo.class);
                i.putExtra("classID", classID);
                i.putExtra("type", "new");
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

}
