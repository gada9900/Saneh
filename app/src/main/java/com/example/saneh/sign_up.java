package com.example.saneh;

/*import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saneh.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.saneh.R;*/
//////////////////////////////////////////////


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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

public class sign_up extends AppCompatActivity {

    Button mSignupBtn;
    EditText mFullName, mEmail, mPassword;
    RadioButton mStudentRadio, mInstructorRadio;
    ProgressBar progressBar ;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.sign_up);

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password1);
        mSignupBtn = findViewById(R.id.signupBtn);
        mStudentRadio = findViewById(R.id.studentRadio);
        mInstructorRadio = findViewById(R.id.instructorRadio);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        //if the user already has an account
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), sign_up.class));
            finish();
        }

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required !");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Password must be >= 6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Register

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(sign_up.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), sign_up.class));

                        }else {
                            Toast.makeText(sign_up.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



    }

    private void setContentView(Bundle saveInstanceState) {
     }

}
