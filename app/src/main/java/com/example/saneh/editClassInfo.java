package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class editClassInfo extends AppCompatActivity {

    //global variabls
    String _classID;
    long _Capacity, currentCap, updatedCap;
    boolean _Projector, _InterActive, currentPro, currentInter, updatedPro, updatedInter;

    List<Boolean> s, m, t, w, th;


    private static final String TAG = "DocSnippets";

    FirebaseFirestore firebaseFirestore;
    static PopupWindow popupWindow;
    TextView classID;
    TextView Capacity;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch Projector;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch InterActive;

    CheckBox S8_9, S9_10, S10_11, S11_12, S12_1, S1_2, S2_3;
    CheckBox M8_9, M9_10, M10_11, M11_12, M12_1, M1_2, M2_3;
    CheckBox T8_9, T9_10, T10_11, T11_12, T12_1, T1_2, T2_3;
    CheckBox W8_9, W9_10, W10_11, W11_12, W12_1, W1_2, W2_3;
    CheckBox Th8_9, Th9_10, Th10_11, Th11_12, Th12_1, Th1_2, Th2_3;

    Button Edit , Delete;
    ImageView gotoadminEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_class_info);

        //prevent bottom toolbar from moving (its important)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        firebaseFirestore = FirebaseFirestore.getInstance();

        gotoadminEdit = findViewById(R.id.gotoadminEdit);
        gotoadminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(editClassInfo.this, adminEdit.class));
                finish();
            }
        });


        //Hooks
        classID = findViewById(R.id.classID);
        Capacity = findViewById(R.id.Capacity);
        Projector = findViewById(R.id.Projector_switch);
        InterActive = findViewById(R.id.interactive_switch);
        Edit = findViewById(R.id.EditClass);


        Th8_9 = findViewById(R.id.Th8_9);
        Th9_10 = findViewById(R.id.Th9_10);
        Th10_11 = findViewById(R.id.Th10_11);
        Th11_12 = findViewById(R.id.Th11_12);
        Th12_1 = findViewById(R.id.Th12_1);
        Th1_2 = findViewById(R.id.Th1_2);
        Th2_3 = findViewById(R.id.Th2_3);
        S8_9 = findViewById(R.id.S8_9);
        S9_10 = findViewById(R.id.S9_10);
        S10_11 = findViewById(R.id.S10_11);
        S11_12 = findViewById(R.id.S11_12);
        S12_1 = findViewById(R.id.S12_1);
        S1_2 = findViewById(R.id.S1_2);
        S2_3 = findViewById(R.id.S2_3);
        W8_9 = findViewById(R.id.W8_9);
        W9_10 = findViewById(R.id.W9_10);
        W10_11 = findViewById(R.id.W10_11);
        W11_12 = findViewById(R.id.W11_12);
        W12_1 = findViewById(R.id.W12_1);
        W1_2 = findViewById(R.id.W1_2);
        W2_3 = findViewById(R.id.W2_3);
        M8_9 = findViewById(R.id.M8_9);
        M9_10 = findViewById(R.id.M9_10);
        M10_11 = findViewById(R.id.M10_11);
        M11_12 = findViewById(R.id.M11_12);
        M12_1 = findViewById(R.id.M12_1);
        M1_2 = findViewById(R.id.M1_2);
        M2_3 = findViewById(R.id.M2_3);
        T8_9 = findViewById(R.id.T8_9);
        T9_10 = findViewById(R.id.T9_10);
        T10_11 = findViewById(R.id.T10_11);
        T11_12 = findViewById(R.id.T11_12);
        T12_1 = findViewById(R.id.T12_1);
        T1_2 = findViewById(R.id.T1_2);
        T2_3 = findViewById(R.id.T2_3);


        final String classIDPassed, type;
        Intent intent = getIntent();
        Bundle valueFromFirstActivity = intent.getExtras();
        classIDPassed = valueFromFirstActivity.getString("classID");
        type = valueFromFirstActivity.getString("type");
        if (type.equalsIgnoreCase("old")) {
            ShowData(classIDPassed);
            UpdateClass(classIDPassed);

        }

    }

    private void ShowData(String classIDPassed) {

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
                        s = (List<Boolean>) document.get("s");
                        m = (List<Boolean>) document.get("m");
                        t = (List<Boolean>) document.get("t");
                        w = (List<Boolean>) document.get("w");
                        th = (List<Boolean>) document.get("th");

                        //show Capacity
                        Capacity.setText(currentCap + "");
                        //show Projector
                        Projector.setChecked(currentPro);
                        //show Interactive
                        InterActive.setChecked(currentInter);


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

    public void UpdateClass(final String classIDPassed) {

        Delete = findViewById(R.id.deleteClass);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("classes").document(classIDPassed)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Toast.makeText(editClassInfo.this, "class successfully deleted!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                                Toast.makeText(editClassInfo.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Edit = findViewById(R.id.EditClass);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DocumentReference documentReference = firebaseFirestore.collection("classes").document(classIDPassed);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch projector = findViewById(R.id.Projector_switch);
                @SuppressLint("UseSwitchCompatOrMaterialCode") Switch interactive = findViewById(R.id.interactive_switch);
                // Get a new write batch
                //Get all the values
                _Capacity = Long.parseLong(Capacity.getEditableText().toString());

                if (projector.isChecked())
                    _Projector = true;
                else
                    _Projector = false;

                if (interactive.isChecked())
                    _InterActive = true;
                else
                    _InterActive = false;

                s.set(0, S8_9.isChecked());
                s.set(1, S9_10.isChecked());
                s.set(2, S10_11.isChecked());
                s.set(3, S11_12.isChecked());
                s.set(4, S12_1.isChecked());
                s.set(5, S1_2.isChecked());
                s.set(6, S2_3.isChecked());

                m.set(0, M8_9.isChecked());
                m.set(1, M9_10.isChecked());
                m.set(2, M10_11.isChecked());
                m.set(3, M11_12.isChecked());
                m.set(4, M12_1.isChecked());
                m.set(5, M1_2.isChecked());
                m.set(6, M2_3.isChecked());

                t.set(0, T8_9.isChecked());
                t.set(1, T9_10.isChecked());
                t.set(2, T10_11.isChecked());
                t.set(3, T11_12.isChecked());
                t.set(4, T12_1.isChecked());
                t.set(5, T1_2.isChecked());
                t.set(6, T2_3.isChecked());

                w.set(0, W8_9.isChecked());
                w.set(1, W9_10.isChecked());
                w.set(2, W10_11.isChecked());
                w.set(3, W11_12.isChecked());
                w.set(4, W12_1.isChecked());
                w.set(5, W1_2.isChecked());
                w.set(6, W2_3.isChecked());

                th.set(0, Th8_9.isChecked());
                th.set(1, Th9_10.isChecked());
                th.set(2, Th10_11.isChecked());
                th.set(3, Th11_12.isChecked());
                th.set(4, Th12_1.isChecked());
                th.set(5, Th1_2.isChecked());
                th.set(6, Th2_3.isChecked());


                //updates the values
                Map<String, Object> editedClass = new HashMap<>();
                editedClass.put("roomNum", classIDPassed);
                editedClass.put("capacity", _Capacity);
                editedClass.put("projector", _Projector);
                editedClass.put("interactive", _InterActive);
                editedClass.put("s", s);
                editedClass.put("m", m);
                editedClass.put("t", t);
                editedClass.put("w", w);
                editedClass.put("th", th);


                documentReference.set(editedClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(editClassInfo.this, "class edited sucessfully", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(editClassInfo.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });
    }

    public void onButtonShowPopupWindowClick(View view, final String classID) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.adminwindow, null);

        //calling attrbuite which in class view info
        Button AddClass = popupView.findViewById(R.id.AddClass);
        AddClass.setText(" Go Back ");
        // change vlaues in class view info
        AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(editClassInfo.this, adminAdd.class);
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



