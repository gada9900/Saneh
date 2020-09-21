package com.example.saneh;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignupBtn,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    public static final String TAG = "TAG";
    String type = null ;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

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
                                                Intent intent = new Intent(getApplicationContext(), profile.class);
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
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_LONG).show();
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
}