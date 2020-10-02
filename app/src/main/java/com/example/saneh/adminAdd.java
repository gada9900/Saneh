package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adminAdd extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    TextView addClassID;
    TextView addCapacity;
    Switch Projector_switch_add;
    Switch InterActive_switch_add;

    Long newCap;
    boolean newPro, newInter;

    CheckBox S8_9, S9_10, S10_11, S11_12, S12_1, S1_2, S2_3;
    CheckBox M8_9, M9_10, M10_11, M11_12, M12_1, M1_2, M2_3;
    CheckBox T8_9, T9_10, T10_11, T11_12, T12_1, T1_2, T2_3;
    CheckBox W8_9, W9_10, W10_11, W11_12, W12_1, W1_2, W2_3;
    CheckBox Th8_9, Th9_10, Th10_11, Th11_12, Th12_1, Th1_2, Th2_3;

    Button AddClass;
    ImageView settings, gotoadminEdit;

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
    }



    public void addClass(View view){

                EditText Capacity = findViewById(R.id.addCapacity);
                Switch projector = findViewById(R.id.Projector_switch_add);
                Switch interactive = findViewById(R.id.interactive_switch_add);

                TextView addClassID = findViewById(R.id.addClassID);
                String classID = addClassID.toString();

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

                Map<String, Object> newClass = new HashMap<>();
                newClass.put("capacity", newCap);
                newClass.put("projector", newPro);
                newClass.put("interactive", newInter);

                firebaseFirestore.collection("classes").document(classID).set(newClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(adminAdd.this, "class added sucessfully", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminAdd.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
                //classes newClass = new classes(classIDPassed, newCap, newInter, newPro);





    }
}
