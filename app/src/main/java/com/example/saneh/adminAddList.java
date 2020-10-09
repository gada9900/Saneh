package com.example.saneh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminAddList extends AppCompatActivity {

    private static final String TAG = "DocSnippets";
    FirebaseFirestore firebaseFirestore ;

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

        firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firebaseFirestore.collection("classes");

        gotoadminEdit = findViewById(R.id.gotoadminEdit2);
        gotoadminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminAddList.this, adminEdit.class));
                finish();
            }
        });

        final List<String> classesList = new ArrayList<>();
        classesList.add("Select class");


        //Floor G
        for (int i = 3; i < 52; i++) {

            if (i == 8 || i == 10 || i == 17 || i == 19 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26 || i == 27 || i == 28 || i == 29 || i == 32 || i == 33 || i == 34 || i == 39 || i == 45)
                continue;

            int id = getResources().getIdentifier("class6G" + i, "id", getPackageName());
            final String DBClassID = "6G" + i;

            DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (!document.exists()) {
                            classesList.add(DBClassID);
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });
        }

        //Floor F
        for (int i = 1; i < 57; i++) {

            if (i == 18 || i == 22 || i == 23 || i == 28 || i == 29 || i == 30 || i == 31 || i == 32 || i == 33 || i == 34 || i == 39 || i == 40 || i == 41 || i == 42 || i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
                continue;

            int id = getResources().getIdentifier("class6F" + i, "id", getPackageName());
            final String DBClassID = "6F" + i;

            DocumentReference docRef = firebaseFirestore.collection("classes").document(DBClassID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            classesList.add(DBClassID);
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

        }

            Collections.sort(classesList);

        //selectedClass = (Spinner) findViewById(R.id.classesList);
        final Spinner spinner = (Spinner) findViewById(R.id.classesList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, classesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        adapter.notifyDataSetChanged();


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

        //final String[] classIDPassed = new String[1];
        final String[] classIDPassed = new String[1];

        AddClass = findViewById(R.id.AddClass2);
        AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Capacity = findViewById(R.id.addCapacity2);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch projector = findViewById(R.id.Projector_switch_add2);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch interactive = findViewById(R.id.interactive_switch_add2);


                if (spinner.getSelectedItemPosition() > 0) {
                    // get selected item value
                    classIDPassed[0] = String.valueOf(spinner.getSelectedItem());
                } else {
                    // set error message on spinner
                    TextView errorTextview = (TextView) spinner.getSelectedView();
                    errorTextview.setError("Class ID is required");
                    return;
                }

                String capCheck = Capacity.getText().toString().trim();
                if (TextUtils.isEmpty(capCheck)) {
                    Capacity.setError("Capacity is required!");
                    return;
                }
                if (!TextUtils.isDigitsOnly(capCheck)) {
                    Capacity.setError("Capacity accepts digits only!");
                    return;
                }

                int capCheck2 = Integer.parseInt(capCheck);
                if (capCheck2 > 40 ) {
                    Capacity.setError("max capacity is 40!");
                    return;
                }

                newCap = Long.parseLong(Capacity.getText().toString());
                if (projector.isChecked())
                    newPro = true;
                else
                    newPro = false;

                if (interactive.isChecked())
                    newInter = true;
                else
                    newInter = false;


                //adding classes


                //String classIDPassed2 = classIDPassed[0];
                DocumentReference documentReference = firebaseFirestore.collection("classes").document(classIDPassed[0]);

                Map<String, Object> newClass = new HashMap<>();
                newClass.put("roomNum", classIDPassed[0]);
                newClass.put("capacity", newCap);
                newClass.put("projector", newPro);
                newClass.put("interactive", newInter);
                newClass.put("s", Arrays.asList(S8_9.isChecked(), S9_10.isChecked(), S10_11.isChecked(), S11_12.isChecked(), S12_1.isChecked(), S1_2.isChecked(), S2_3.isChecked()));
                newClass.put("m", Arrays.asList(M8_9.isChecked(), M9_10.isChecked(), M10_11.isChecked(), M11_12.isChecked(), M12_1.isChecked(), M1_2.isChecked(), M2_3.isChecked()));
                newClass.put("t", Arrays.asList(T8_9.isChecked(), T9_10.isChecked(), T10_11.isChecked(), T11_12.isChecked(), T12_1.isChecked(), T1_2.isChecked(), T2_3.isChecked()));
                newClass.put("w", Arrays.asList(W8_9.isChecked(), W9_10.isChecked(), W10_11.isChecked(), W11_12.isChecked(), W12_1.isChecked(), W1_2.isChecked(), W2_3.isChecked()));
                newClass.put("th", Arrays.asList(Th8_9.isChecked(), Th9_10.isChecked(), Th10_11.isChecked(), Th11_12.isChecked(), Th12_1.isChecked(), Th1_2.isChecked(), Th2_3.isChecked()));


                final android.app.AlertDialog.Builder alert = new AlertDialog.Builder(adminAddList.this);
                alert.setTitle("Add Class");

                alert.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(adminAddList.this, adminEdit.class));
                        finish();
                    }
                });

                alert.setCancelable(false);

                documentReference.set(newClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                alert.setMessage("class added successfully");
                                alert.show();
                                //Toast.makeText(adminAdd.this, "class added successfully", Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //  Toast.makeText(adminAdd.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                                alert.setMessage("Error! : " + e);
                                alert.show();

                            }
                        });
            }
        });


    }
}
