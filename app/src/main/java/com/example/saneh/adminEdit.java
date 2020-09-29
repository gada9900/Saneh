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

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit);

        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference classesRef = firebaseFirestore.collection("classesTest");

        /*Map<String, Object> data1 = new HashMap<>();
        data1.put("capacity", 25);
        data1.put("interactive", true);
        data1.put("projector", true);

        classesRef.document("F22").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("capacity", 25);
        data2.put("interactive", true);
        data2.put("projector", true);

        classesRef.document("G54").set(data1);*/

        //final Query query = firebaseFirestore.collection("classesTest");

    }



        public void clickableClass(){
            TextView classes ;
            TextView unavClasses = null;

            /////// initiate a clickable classes in floor 1
            for( int i = 1 ; i < 57 ; i++) {

                if( i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 ||i == 31 ||i == 32 ||i == 33 ||i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 ||i == 46 ||i == 47 ){
                    //continue;
                    int id = getResources().getIdentifier("class6F"+i, "id", getPackageName());
                    unavClasses = (TextView) findViewById(id);
                } else{
                int id = getResources().getIdentifier("class6F"+i, "id", getPackageName());
                classes = (TextView) findViewById(id);

                classes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickShowPopupWindowClick(view);

                    }
                });
                    unavClasses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickShowPopupWindowClick(view);

                        }
                    });
            } }

            /////// initiate a clickable classes in floor G
            for( int i = 3 ; i < 52 ; i++) {

                if( i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 ||i == 24 ||i == 25 ||i == 26 ||i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45 ){
                    //continue;
                    int id = getResources().getIdentifier("class6G"+i, "id", getPackageName());
                    unavClasses = (TextView) findViewById(id);
                } else{
                    int id = getResources().getIdentifier("class6G"+i, "id", getPackageName());
                    classes = (TextView) findViewById(id);

                    classes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickShowPopupWindowClick(view);

                        }
                    });
                    unavClasses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickShowPopupWindowClick(view);

                        }
                    });
                }
                //////////////////////////////////////////////
            }
        }

//The class is not created yet
    private void onClickShowPopupWindowClick(View view) {
    }
}
