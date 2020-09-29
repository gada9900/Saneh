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
        CollectionReference classes =firebaseFirestore.collection("classesTest");

        /*Map<String, Object> data1 = new HashMap<>();
        data1.put("capacity", 25);
        data1.put("interactive", true);
        data1.put("projector", true);

        classes.document("F22").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("capacity", 25);
        data2.put("interactive", true);
        data2.put("projector", true);

        classes.document("G54").set(data1);*/

        //final Query query = firebaseFirestore.collection("classesTest");

        TextView class47 = findViewById(R.id.class6G47);
        class47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //onButtonShowPopupWindowClick(view);
            }
        });
    }
}
