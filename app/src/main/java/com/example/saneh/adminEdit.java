package com.example.saneh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class adminEdit extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    private FirebaseFirestore firebaseFirestore;
    private ImageView edit, settings, add;
    static PopupWindow popupWindow ;
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit);

        firebaseFirestore = FirebaseFirestore.getInstance();

        settings = findViewById(R.id.imageView8);
        edit = findViewById(R.id.imageView9);
        add = findViewById(R.id.imageView17);

        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminEdit.class);
                startActivity(i);
            }
        });*/

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_settings.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminAddList.class);
                startActivity(i);
            }
        });

        classesColor();
        clickableClass();

    }

    public void classesColor(){

        final int green = Color.parseColor("#3EBB49");
        final int gray = Color.parseColor("#8b97ac");
        //TextView classID;
        //coloring Floor G

        for( int i = 3 ; i < 52 ; i++) {

            if (i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26 || i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45)
                continue;

            int id = getResources().getIdentifier("class6G" + i, "id", getPackageName());
            final String DBClassID = "6G" + i;
            final TextView classID = (TextView) findViewById(id);

            DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "Document exists!");
                            classID.setBackgroundColor(green);
                        } else {
                            //Log.d(TAG, "Document does not exist!");
                            classID.setBackgroundColor(gray);
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

        }

        //coloring Floor F

        for( int i = 1 ; i < 57 ; i++) {

            if( i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 ||i == 31 ||i == 32 ||i == 33 ||i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 ||i == 46 ||i == 47 )
                continue;


            int id = getResources().getIdentifier("class6F" + i, "id", getPackageName());
            final String DBClassID = "6F" + i;
            final TextView classID = (TextView) findViewById(id);

            DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Log.d(TAG, "Document exists!");
                            classID.setBackgroundColor(green);
                        } else {
                            //Log.d(TAG, "Document does not exist!");
                            classID.setBackgroundColor(gray);
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

        }

    }



    public void clickableClass(){


        /////// initiate a clickable classes in floor G
        for( int i = 3 ; i < 52 ; i++) {
            if( i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 ||i == 24 ||i == 25 ||i == 26 ||i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45 )
                continue;

            int id = getResources().getIdentifier("class6G" + i, "id", getPackageName());
            final String DBClassID = "6G" + i;
            final TextView classID = (TextView) findViewById(id);

            classID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    //Log.d(TAG, "Document exists!");
                                    Intent i = new Intent(getApplicationContext(), editClassInfo.class);
                                    i.putExtra("classID", DBClassID);
                                    i.putExtra("type", "old");
                                    startActivity(i);
                                } else {
                                    //Log.d(TAG, "Document does not exist!");
                                    onButtonShowPopupWindowClick(v , DBClassID);
                                }
                            } else {
                                Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });
                }
            });

        }



        /////// initiate a clickable classes in floor F
        for( int i = 1 ; i < 57 ; i++) {

            if( i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 ||i == 31 ||i == 32 ||i == 33 ||i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 ||i == 46 ||i == 47 )
                continue;

            int id = getResources().getIdentifier("class6F"+i, "id", getPackageName());
            final String DBClassID = "6F"+i;
            final TextView classID = (TextView) findViewById(id);

            classID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    //Log.d(TAG, "Document exists!");
                                    Intent i = new Intent(getApplicationContext(), editClassInfo.class);
                                    i.putExtra("classID", DBClassID);
                                    i.putExtra("type", "old");
                                    startActivity(i);
                                } else {
                                    //Log.d(TAG, "Document does not exist!");
                                    onButtonShowPopupWindowClick(v , DBClassID);
                                }
                            } else {
                                Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });
                }
            });

        }


    }

    public void onButtonShowPopupWindowClick(View view , final String classID) {

        android.app.AlertDialog.Builder alert = new AlertDialog.Builder(adminEdit.this);
        alert.setTitle("Add Class");
        alert.setMessage("This class is unavailable, Do you want to add it?");

        alert.setPositiveButton("Add class", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(adminEdit.this,adminAdd.class);
                //getApplicationContext(), editClassInfo.class);
                i.putExtra("classID", classID);
                i.putExtra("type", "new");
                startActivity(i);
                finish();
            }
        });
        alert.setNegativeButton("Cancel",null);

        alert.show();



/*
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.adminwindow, null);

        //calling attrbuite which in class view info
        Button AddClass = popupView.findViewById(R.id.AddClass);
        ImageView close = popupView.findViewById(R.id.close);
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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popupWindow.dismiss();
            }
        });


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, 500, 700, focusable);
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


*/
    }


}
