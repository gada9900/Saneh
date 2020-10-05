package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminAdd extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    TextView addClassID;

    Long newCap;
    boolean newPro, newInter;

    CheckBox S8_9, S9_10, S10_11, S11_12, S12_1, S1_2, S2_3;
    CheckBox M8_9, M9_10, M10_11, M11_12, M12_1, M1_2, M2_3;
    CheckBox T8_9, T9_10, T10_11, T11_12, T12_1, T1_2, T2_3;
    CheckBox W8_9, W9_10, W10_11, W11_12, W12_1, W1_2, W2_3;
    CheckBox Th8_9, Th9_10, Th10_11, Th11_12, Th12_1, Th1_2, Th2_3;

    Button AddClass;
    ImageView gotoadminEdit;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_admin);

        gotoadminEdit = findViewById(R.id.gotoadminEdit);
        gotoadminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminAdd.this, adminEdit.class));
                finish();
            }
        });

        final String classIDPassed;
        Intent intent=getIntent();
        Bundle valueFromFirstActivity = intent.getExtras();
        classIDPassed = valueFromFirstActivity.getString("classID");
        addClassID = findViewById(R.id.addClassID);
        addClassID.setText(classIDPassed);



        Th8_9 = findViewById(R.id.Th8_9A);
        Th9_10 = findViewById(R.id.Th9_10A);
        Th10_11 = findViewById(R.id.Th10_11A);
        Th11_12 = findViewById(R.id.Th11_12A);
        Th12_1 = findViewById(R.id.Th12_1A);
        Th1_2 = findViewById(R.id.Th1_2A);
        Th2_3 = findViewById(R.id.Th2_3A);
        S8_9 = findViewById(R.id.S8_9A);
        S9_10 = findViewById(R.id.S9_10A);
        S10_11 = findViewById(R.id.S10_11A);
        S11_12 = findViewById(R.id.S11_12A);
        S12_1 = findViewById(R.id.S12_1A);
        S1_2 = findViewById(R.id.S1_2A);
        S2_3 = findViewById(R.id.S2_3A);
        W8_9 = findViewById(R.id.W8_9A);
        W9_10 = findViewById(R.id.W9_10A);
        W10_11 = findViewById(R.id.W10_11A);
        W11_12 = findViewById(R.id.W11_12A);
        W12_1 = findViewById(R.id.W12_1A);
        W1_2 = findViewById(R.id.W1_2A);
        W2_3 = findViewById(R.id.W2_3A);
        M8_9 = findViewById(R.id.M8_9A);
        M9_10 = findViewById(R.id.M9_10A);
        M10_11 = findViewById(R.id.M10_11A);
        M11_12 = findViewById(R.id.M11_12A);
        M12_1 = findViewById(R.id.M12_1A);
        M1_2 = findViewById(R.id.M1_2A);
        M2_3 = findViewById(R.id.M2_3A);
        T8_9 = findViewById(R.id.T8_9A);
        T9_10 = findViewById(R.id.T9_10A);
        T10_11 = findViewById(R.id.T10_11A);
        T11_12 = findViewById(R.id.T11_12A);
        T12_1 = findViewById(R.id.T12_1A);
        T1_2 = findViewById(R.id.T1_2A);
        T2_3 = findViewById(R.id.T2_3A);

        addClass(classIDPassed);

    }



    public void addClass(final String classIDPassed){


        AddClass = findViewById(R.id.AddClass);
        AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Capacity = findViewById(R.id.addCapacity);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch projector = findViewById(R.id.Projector_switch_add);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch interactive = findViewById(R.id.interactive_switch_add);


                String capCheck = Capacity.getText().toString().trim();
                if (TextUtils.isEmpty(capCheck)){
                    Capacity.setError("Capacity is required!");
                    return;
                }
                if (!TextUtils.isDigitsOnly(capCheck)){
                    Capacity.setError("Capacity accepts digits only!");
                    return;
                }

                newCap = Long.parseLong(Capacity.getText().toString());
                if(projector.isChecked())
                    newPro = true;
                else
                    newPro = false;

                if (interactive.isChecked())
                    newInter = true;
                else
                    newInter = false;



                //adding classes


                DocumentReference documentReference = firebaseFirestore.collection("classes").document(classIDPassed);

                Map<String, Object> newClass = new HashMap<>();
                newClass.put("roomNum", classIDPassed);
                newClass.put("capacity", newCap);
                newClass.put("projector", newPro);
                newClass.put("interactive", newInter);
                newClass.put("s", Arrays.asList(S8_9.isChecked(), S9_10.isChecked(), S10_11.isChecked(), S11_12.isChecked(), S12_1.isChecked(), S1_2.isChecked(), S2_3.isChecked()));
                newClass.put("m", Arrays.asList(M8_9.isChecked(), M9_10.isChecked(), M10_11.isChecked(), M11_12.isChecked(), M12_1.isChecked(), M1_2.isChecked(), M2_3.isChecked()));
                newClass.put("t", Arrays.asList(T8_9.isChecked(),T9_10.isChecked(),T10_11.isChecked(),T11_12.isChecked(),T12_1.isChecked(),T1_2.isChecked(),T2_3.isChecked()));
                newClass.put("w", Arrays.asList(W8_9.isChecked(),W9_10.isChecked(),W10_11.isChecked(),W11_12.isChecked(),W12_1.isChecked(),W1_2.isChecked(),W2_3.isChecked()));
                newClass.put("th", Arrays.asList(Th8_9.isChecked(),Th9_10.isChecked(),Th10_11.isChecked(),Th11_12.isChecked(),Th12_1.isChecked(),Th1_2.isChecked(),Th2_3.isChecked()));


                documentReference.set(newClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(adminAdd.this, "class added successfully", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminAdd.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());

                            }
                        });
            }
        });






    }
}

