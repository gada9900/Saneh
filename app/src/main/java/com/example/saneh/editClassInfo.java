package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class editClassInfo extends AppCompatActivity{

    //global variabls
    String _classID ;
    long _Capacity, currentCap;
    boolean _Projector , _InterActive, currentPro, currentInter;
    long _floor;

    Bundle query;
    long id ;


    FirebaseDatabase root;
    DatabaseReference refrence;
    private static final String TAG = "DocSnippets";

    FirebaseFirestore firebaseFirestore;

    TextView classID;
    TextView Capacity;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch Projector;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch InterActive;
    RadioGroup Floor;

    CheckBox Th8_9;
    CheckBox Th9_10;
    CheckBox Th10_11;
    CheckBox Th11_12;
    CheckBox Th12_1;
    CheckBox Th1_2;
    CheckBox Th2_3;
    CheckBox S8_9;
    CheckBox S9_10;
    CheckBox S10_11;
    CheckBox S11_12;
    CheckBox S12_1;
    CheckBox S1_2;
    CheckBox S2_3;
    CheckBox W8_9;
    CheckBox W9_10;
    CheckBox W10_11;
    CheckBox W11_12;
    CheckBox W12_1;
    CheckBox W1_2;
    CheckBox W2_3;
    CheckBox M8_9;
    CheckBox M9_10;
    CheckBox M10_11;
    CheckBox M11_12;
    CheckBox M12_1;
    CheckBox M1_2;
    CheckBox M2_3;
    CheckBox T8_9;
    CheckBox T9_10;
    CheckBox T10_11;
    CheckBox T11_12;
    CheckBox T12_1;
    CheckBox T1_2;
    CheckBox T2_3;

    Button Edit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_class_info);

        firebaseFirestore = FirebaseFirestore.getInstance();
        final CollectionReference classesRef = firebaseFirestore.collection("classes");

        //Hooks

        classID = findViewById(R.id.classID);
        Capacity = findViewById(R.id.Capacity);
        Projector = findViewById(R.id.Projector_switch);
        InterActive = findViewById(R.id.interactive_switch);
        Floor = findViewById(R.id.FloorGroup);
        Edit = findViewById(R.id.EditClass);


        Th8_9   = findViewById(R.id.Th8_9);
        Th9_10  = findViewById(R.id.Th9_10);
        Th10_11 = findViewById(R.id.Th10_11);
        Th11_12 = findViewById(R.id.Th11_12);
        Th12_1  = findViewById(R.id.Th12_1);
        Th1_2   = findViewById(R.id.Th1_2);
        Th2_3   = findViewById(R.id.Th2_3);
        S8_9    = findViewById(R.id.S8_9);
        S9_10   = findViewById(R.id.S9_10);
        S10_11  = findViewById(R.id.S10_11);
        S11_12  = findViewById(R.id.S11_12);
        S12_1   = findViewById(R.id.S12_1);
        S1_2    = findViewById(R.id.S1_2);
        S2_3    = findViewById(R.id.S2_3);
        W8_9    = findViewById(R.id.W8_9);
        W9_10   = findViewById(R.id.W9_10);
        W10_11  = findViewById(R.id.W10_11);
        W11_12  = findViewById(R.id.W11_12);
        W12_1   = findViewById(R.id.W12_1);
        W1_2    = findViewById(R.id.W1_2);
        W2_3    = findViewById(R.id.W2_3);
        M8_9    = findViewById(R.id.M8_9);
        M9_10   = findViewById(R.id.M9_10);
        M10_11  = findViewById(R.id.M10_11);
        M11_12  = findViewById(R.id.M11_12);
        M12_1   = findViewById(R.id.M12_1);
        M1_2    = findViewById(R.id.M1_2);
        M2_3    = findViewById(R.id.M2_3);
        T8_9    = findViewById(R.id.T8_9);
        T9_10   = findViewById(R.id.T9_10);
        T10_11  = findViewById(R.id.T10_11);
        T11_12  = findViewById(R.id.T11_12);
        T12_1   = findViewById(R.id.T12_1);
        T1_2    = findViewById(R.id.T1_2);
        T2_3    = findViewById(R.id.T2_3);


        Edit    = findViewById(R.id.EditClass);


            ShowData();



        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root = FirebaseDatabase.getInstance();
                refrence = root.getReference("classes");
                //Get all the values
                _classID =classID.getEditableText().toString();
                _Capacity =Long.parseLong(Capacity.getEditableText().toString());
                _Projector = Boolean.parseBoolean(Projector.getEditableText().toString());
                _InterActive =Boolean.parseBoolean(InterActive.getEditableText().toString());

              //  classInfo obj1 = new classInfo(_classID,_Capacity,_Projector,_InterActive);
              //  refrence.child(_classID).setValue(obj1);

                //  classInfo obj1 = new classInfo(_classID,_Capacity,_Projector,_InterActive);
                //  refrence.child(_classID).setValue(obj1);


            }
        });
    }

    private void ShowData() {

        final String classIDPassed;
        Intent intent=getIntent();
        Bundle valueFromFirstActivity = intent.getExtras();
        classIDPassed = valueFromFirstActivity.getString("classID");
        classID.setText(classIDPassed);

        DocumentReference classRef = firebaseFirestore.collection("classes").document(classIDPassed);
        classRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        currentCap = document.getLong("capacity");
                        currentPro = document.getBoolean("projector");
                        currentInter = document.getBoolean("interactive");

                        Capacity.setText(currentCap+"");
                        Projector.setChecked(currentPro);
                        InterActive.setChecked(currentInter);
                        if (classIDPassed.contains("G")){
                            Floor.check(R.id.radioButton);
                        } else{
                            Floor.check(R.id.radioButton2);
                        }


                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(editClassInfo.this, "No class with this ID", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(editClassInfo.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void EditClass(View view){

    }


}
