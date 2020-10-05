package com.example.saneh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class adminAddList extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Long newCap;
    boolean newPro, newInter;

    CheckBox S8_9, S9_10, S10_11, S11_12, S12_1, S1_2, S2_3;
    CheckBox M8_9, M9_10, M10_11, M11_12, M12_1, M1_2, M2_3;
    CheckBox T8_9, T9_10, T10_11, T11_12, T12_1, T1_2, T2_3;
    CheckBox W8_9, W9_10, W10_11, W11_12, W12_1, W1_2, W2_3;
    CheckBox Th8_9, Th9_10, Th10_11, Th11_12, Th12_1, Th1_2, Th2_3;

    Button AddClass;
    ImageView gotoadminEdit;
    Spinner selectedClass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_list);


        gotoadminEdit = findViewById(R.id.gotoadminEdit2);
        gotoadminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminAddList.this, adminEdit.class));
                finish();
            }
        });

        selectedClass = (Spinner) findViewById(R.id.classesList);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerSearch);
        /////////////////////////////////////////////////////////////////////////////////
        //list?

        Th8_9 = findViewById(R.id.Th8_9A2);
        Th9_10 = findViewById(R.id.Th9_10A2);
        Th10_11 = findViewById(R.id.Th10_11A2);
        Th11_12 = findViewById(R.id.Th11_12A2);
        Th12_1 = findViewById(R.id.Th12_1A2);
        Th1_2 = findViewById(R.id.Th1_2A2);
        Th2_3 = findViewById(R.id.Th2_3A2);
        S8_9 = findViewById(R.id.S8_9A2);
        S9_10 = findViewById(R.id.S9_10A2);
        S10_11 = findViewById(R.id.S10_11A2);
        S11_12 = findViewById(R.id.S11_12A2);
        S12_1 = findViewById(R.id.S12_1A2);
        S1_2 = findViewById(R.id.S1_2A2);
        S2_3 = findViewById(R.id.S2_3A2);
        W8_9 = findViewById(R.id.W8_9A2);
        W9_10 = findViewById(R.id.W9_10A2);
        W10_11 = findViewById(R.id.W10_11A2);
        W11_12 = findViewById(R.id.W11_12A2);
        W12_1 = findViewById(R.id.W12_1A2);
        W1_2 = findViewById(R.id.W1_2A2);
        W2_3 = findViewById(R.id.W2_3A2);
        M8_9 = findViewById(R.id.M8_9A2);
        M9_10 = findViewById(R.id.M9_10A2);
        M10_11 = findViewById(R.id.M10_11A2);
        M11_12 = findViewById(R.id.M11_12A2);
        M12_1 = findViewById(R.id.M12_1A2);
        M1_2 = findViewById(R.id.M1_2A2);
        M2_3 = findViewById(R.id.M2_3A2);
        T8_9 = findViewById(R.id.T8_9A2);
        T9_10 = findViewById(R.id.T9_10A2);
        T10_11 = findViewById(R.id.T10_11A2);
        T11_12 = findViewById(R.id.T11_12A2);
        T12_1 = findViewById(R.id.T12_1A2);
        T1_2 = findViewById(R.id.T1_2A2);
        T2_3 = findViewById(R.id.T2_3A2);


    }
}
