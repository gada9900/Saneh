package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class editClassInfo extends AppCompatActivity{

    //global variabls
    String _classID ;
    long _Capacity, currentCap;
    boolean _Projector , _InterActive, currentPro, currentInter;

    List<Boolean> s , m , t , w ,th ;



    private static final String TAG = "DocSnippets";

    FirebaseFirestore firebaseFirestore;

    TextView classID;
    TextView Capacity;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch Projector;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch InterActive;
    RadioGroup Floor;

    CheckBox S8_9, S9_10, S10_11, S11_12, S12_1, S1_2, S2_3;
    CheckBox M8_9, M9_10, M10_11, M11_12, M12_1, M1_2, M2_3;
    CheckBox T8_9, T9_10, T10_11, T11_12, T12_1, T1_2, T2_3;
    CheckBox W8_9, W9_10, W10_11, W11_12, W12_1, W1_2, W2_3;
    CheckBox Th8_9, Th9_10, Th10_11, Th11_12, Th12_1, Th1_2, Th2_3;

    Button Edit;
    ImageView settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_class_info);

        //prevent bottom toolbar from moving (its important)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
                String classIDPassedd;
                Intent intent=getIntent();
                Bundle valueFromFirstActivity = intent.getExtras();
                classIDPassedd = valueFromFirstActivity.getString("classID");
                DocumentReference classRef = firebaseFirestore.collection("classes").document(classIDPassedd);
                // Get a new write batch


                //Get all the values
                _classID =classID.getEditableText().toString();
                _Capacity =Long.parseLong(Capacity.getEditableText().toString());
                _Projector = Boolean.parseBoolean(Projector.getEditableText().toString());
                _InterActive =Boolean.parseBoolean(InterActive.getEditableText().toString());


//reference.update("capacity",_Capacity);
                /*firebaseFirestore.collection("classes").document(classIDPassedd)
                .update("roomNum",_classID,
               "interactive", _InterActive,
               "projector",Projector).addOnSuccessListener(new OnSuccessListener<Void>() {*/
                classRef.update("capacity", _Capacity);
                classRef.update("roomNum", _classID);
                classRef.update("interactive", _InterActive);
                classRef.update("projector",_Projector).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(editClassInfo.this,"Data Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editClassInfo.this,e.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private void ShowData() {

        final String classIDPassed;
        Intent intent=getIntent();
        Bundle valueFromFirstActivity = intent.getExtras();
        classIDPassed = valueFromFirstActivity.getString("classID");

        //show ClassID
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
                             s  = (List<Boolean>) document.get("s");
                             m  = (List<Boolean>) document.get("m");
                             t  = (List<Boolean>) document.get("t");
                             w  = (List<Boolean>) document.get("w");
                             th = (List<Boolean>) document.get("th");

                        //show Capacity
                        Capacity.setText(currentCap+"");
                        //show Projector
                        Projector.setChecked(currentPro);
                        //show Interactive
                        InterActive.setChecked(currentInter);
                        //show Floor
                        if (classIDPassed.contains("G")){
                            Floor.check(R.id.radioButton);
                        } else{
                            Floor.check(R.id.radioButton2);
                        }

                        //sunday checkboxes
                        S8_9.setChecked(s.get(0));
                        S9_10.setChecked(s.get(1));
                        S10_11.setChecked(s.get(2));
                        S11_12.setChecked(s.get(3));
                        S12_1.setChecked(s.get(4));
                        S1_2.setChecked(s.get(5));
                        S2_3.setChecked(s.get(6));
                        //monday checkboxes
                        M8_9.setChecked(m.get(0));
                        M9_10.setChecked(m.get(1));
                        M10_11.setChecked(m.get(2));
                        M11_12.setChecked(m.get(3));
                        M12_1.setChecked(m.get(4));
                        M1_2.setChecked(m.get(5));
                        M2_3.setChecked(m.get(6));
                        //tuesday checkboxes
                        T8_9.setChecked(t.get(0));
                        T9_10.setChecked(t.get(1));
                        T10_11.setChecked(t.get(2));
                        T11_12.setChecked(t.get(3));
                        T12_1.setChecked(t.get(4));
                        T1_2.setChecked(t.get(5));
                        T2_3.setChecked(t.get(6));
                        //wednesday checkboxes
                        W8_9.setChecked(w.get(0));
                        W9_10.setChecked(w.get(1));
                        W10_11.setChecked(w.get(2));
                        W11_12.setChecked(w.get(3));
                        W12_1.setChecked(w.get(4));
                        W1_2.setChecked(w.get(5));
                        W2_3.setChecked(w.get(6));
                        //thursday
                        Th8_9.setChecked(th.get(0));
                        Th9_10.setChecked(th.get(1));
                        Th10_11.setChecked(th.get(2));
                        Th11_12.setChecked(th.get(3));
                        Th12_1.setChecked(th.get(4));
                        Th1_2.setChecked(th.get(5));
                        Th2_3.setChecked(th.get(6));



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

    public void UpdateClass(View view){

        final String classIDPassed;
        Intent intent=getIntent();
        Bundle valueFromFirstActivity = intent.getExtras();
        classIDPassed = valueFromFirstActivity.getString("classID");
        DocumentReference classRef = firebaseFirestore.collection("classes").document(classIDPassed);

        //Get all the values
        _classID =classID.getEditableText().toString();
        _Capacity =Long.parseLong(Capacity.getEditableText().toString());
        _Projector = Boolean.parseBoolean(Projector.getEditableText().toString());
        _InterActive =Boolean.parseBoolean(InterActive.getEditableText().toString());


        classRef.update("roomNum",_classID);
        classRef.update("capacity",_Capacity);
        classRef.update("interactive", _InterActive);
        classRef.update("projector",Projector).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(editClassInfo.this,"Data Updated Successfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(editClassInfo.this,e.toString(), Toast.LENGTH_LONG).show();
            }
        });
                         /* "capacity": _Capacity ,
                          "interactive": _InterActive,
                          "projector": Projector ,
                          "s": ,
                          "m": ,
                          "t": ,
                          "w": ,
                          "th":
                  });*/

        //  classInfo obj1 = new classInfo(_classID,_Capacity,_Projector,_InterActive);
        //  refrence.child(_classID).setValue(obj1);

    }


}
