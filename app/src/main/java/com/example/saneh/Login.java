package com.example.saneh;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignupBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    public static final String TAG = "TAG";
    String type = null ;
    String id ;
    String _classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
            refreshReservations();
        mEmail = findViewById(R.id.emailLOGIN);
        mPassword = findViewById(R.id.passwordLOGIN);
        progressBar = findViewById(R.id.progressBarLOGIN);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.LOGINbutton);
        mSignupBtn = findViewById(R.id.SIGNUPbutton);
        forgotTextLink = findViewById(R.id.forgotPassword);

        progressBar.setVisibility(View.GONE);

        forgotTextLink.setPaintFlags(forgotTextLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mSignupBtn.setPaintFlags(mSignupBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), sign_up.class);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                // must review by group members *****************************************

                /*if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }*/



                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                 id = user.getUid();
                            DocumentReference docRef = fStore.collection("users").document(id);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            type = document.getString("userType");

                                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_LONG).show();

                                            if(!type.equals("Admin")) {
                                                Intent intent = new Intent(getApplicationContext(), search.class);
                                                startActivity(intent);
                                                finish();
                                            }else{

                                                Intent intent = new Intent(getApplicationContext(), admin_settings.class);
                                                startActivity(intent);
                                                finish();
                                            }



                                        } else {
                                            Log.d(TAG, "No such document");
                                            Toast.makeText(Login.this, "your account very old please create a new acount ", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);

                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                        Toast.makeText(Login.this, "wrong log in try again later ", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });








                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });



        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to received reset link:");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset link sent to your email.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! reset link is not sent" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });


    }
    public void refreshReservations() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Task<QuerySnapshot> querySnapshotTask2 = FirebaseFirestore.getInstance()
                .collection("reservations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            int myListOfDocumentsLen = myListOfDocuments.size();
                            String resDate;
                            String time;
                            boolean isConfirmed;
                            for (int i = 0; i < myListOfDocumentsLen; i++) {
                                resDate = myListOfDocuments.get(i).getString("date");
                                time = myListOfDocuments.get(i).getString("time");
                                isConfirmed = myListOfDocuments.get(i).getBoolean("confirmed");

                                String reservationTime = time ;
                                String nextReservationTime1 = time.substring(time.indexOf("-")+2);

                                if (reservationTime.charAt(1) == ':') {
                                    reservationTime = "0" + reservationTime.substring(0, 1);
                                } else {
                                    reservationTime = reservationTime.substring(0, 2);
                                }
                                if (nextReservationTime1.charAt(1) == ':') {
                                    nextReservationTime1 = "0" + nextReservationTime1.substring(0, 1);
                                } else {
                                    nextReservationTime1 = nextReservationTime1.substring(0, 2);
                                }
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm aa");
                                Util g12 = new Util();
                                final long ff = g12.getCurrentNetworkTime();
                                String noww= simpleDateFormat.format(ff);
                                //String noww = simpleDateFormat.format(new Date().getTime());
                                long now = 0;
                                long res = 0;
                                long date2 = 0;
                                String aa2 ;
                                String aa ;
                                if(reservationTime.equals("12") || reservationTime.equals("01") ||reservationTime.equals("02") ) {
                                    aa = " PM";
                                }else{ aa= " AM";}
                                if(nextReservationTime1.equals("12") || nextReservationTime1.equals("01") ||nextReservationTime1.equals("02") || nextReservationTime1.equals("03")  ) {
                                    aa2 = " PM";
                                }else{ aa2= " AM";}

                                String reDate = resDate+ " " + reservationTime +time.substring(time.indexOf(":"),time.indexOf(":")+3)+aa ;
                                String Date2 = resDate+" "+nextReservationTime1+":00"+aa2;
                                try {
                                    now = simpleDateFormat.parse(noww).getTime();
                                    res = simpleDateFormat.parse(reDate).getTime();
                                    date2 = simpleDateFormat.parse(Date2).getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if(now > res){
                                    _classID = "class" + myListOfDocuments.get(i).getString("classID");
                                    int id = getResources().getIdentifier(_classID, "id", getPackageName());
                                    TextView room = (TextView) findViewById(id);

                                    long remining2 = date2 - now  ;
                                    long minutesTomillies = TimeUnit.MINUTES.toMillis(15);
                                    long total = res + minutesTomillies ;

                                    if(now > total )
                                        if(!isConfirmed){
                                            myListOfDocuments.get(i).getReference().delete();
                                            room.setBackgroundColor(getResources().getColor(R.color.grean));

                                        }
                                    if(remining2 <= 0){
                                        myListOfDocuments.get(i).getReference().delete();
                                        room.setBackgroundColor(getResources().getColor(R.color.grean));


                                    }
                                }



                            } // for loop close

                        }// if (task successful ) close

                    }

                });

    }
}