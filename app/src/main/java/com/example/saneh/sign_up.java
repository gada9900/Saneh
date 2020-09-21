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
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button mSignupBtn;
    EditText mFullName, mEmail, mPassword1, mPassword2;
    RadioGroup mRadioGroup;
    RadioButton mStudentRadio, mInstructorRadio;
    String userType;
    ProgressBar progressBar ;
    ImageView gotologin;
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

        gotologin = findViewById(R.id.gotologin);


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
                final String fullName = mFullName.getText().toString().trim();



                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                final  String selectedText = (String) radioButton.getText();

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

                if (!isValidPassword(password1)){
                    mPassword1.setError("Password's Regulations: \n -Must contains at least one digit \n -Must contains at least one lowercase character \n -Must contains at least one uppercase character \n -Must contains at least one special symbol (@#$%) \n -The length >= 8");
                    return;
                }

                if (!password1.equals(password2)){
                    mPassword2.setError("Your password doesn't match the confirmed password!");
                    return;
                }

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
                            user.put("userType",selectedText);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user account created for " + userId);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), profile.class));
                            finish();

                        }else {
                            Toast.makeText(sign_up.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up.this, Login.class));
                finish();
            }
        });



    }

    private boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void setContentView(Bundle saveInstanceState) {
     }

}
