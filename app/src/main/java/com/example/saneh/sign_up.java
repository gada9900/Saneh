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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button mSignupBtn;
    EditText mFullName, mEmail, mPassword1, mPassword2;
    RadioGroup mRadioGroup;
    RadioButton mStudentRadio, mInstructorRadio;
    String userType;
    ProgressBar progressBar ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.sign_up);

        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword1 = findViewById(R.id.password1);
        mPassword2 = findViewById(R.id.password2);
        mSignupBtn = findViewById(R.id.signupBtn);
        mRadioGroup = findViewById(R.id.radioGroup);
        mStudentRadio = findViewById(R.id.studentRadio);
        mInstructorRadio = findViewById(R.id.instructorRadio);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
/*
        //if the user already has an account
       if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), sign_up.class));
            finish();
        }

*/

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                RadioButton typeRadioGroup =findViewById(mRadioGroup.getCheckedRadioButtonId());
                userType = typeRadioGroup.getText().toString();

                if (TextUtils.isEmpty(fullName)){
                    mFullName.setError("Full Name is required!");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password1)){
                    mPassword1.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(password2)){
                    mPassword2.setError("Confirm your Password!");
                    return;
                }
                if (!password1.equals(password2)){
                    mPassword2.setError("Your password doesn't match the confirmed password!");
                    return;
                }
                if (password1.length() < 6){
                    mPassword1.setError("Password must be >= 6 characters");
                    return;
                }
                // check Radio
                progressBar.setVisibility(View.VISIBLE);


                //Register

                fAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(sign_up.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            // Radio
                            //user.put("type", )
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user account created for " + userId);
                                }
                            });
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
