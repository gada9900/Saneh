package com.example.saneh;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.rpc.context.AttributeContext;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editProfileInfo extends AppCompatActivity {
    private static final String TAG = "TAG";
    private EditText name, nPassword;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthSL;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //DatabaseReference reference;
    private FirebaseFirestore fStore;
    private Button upName, upPassword;
    //private String _Name,_Pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_info);
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        ///  cPassword=(EditText) findViewById(R.id.editTextTextPassword2);
        nPassword = (EditText) findViewById(R.id.editTextTextPassword3);
        //  cnfPassword=(EditText) findViewById(R.id.editTextTextPassword4);
        upName = (Button) findViewById(R.id.EditName);
        upPassword = (Button) findViewById(R.id.editPassword);
        //fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        //reference= FirebaseDatabase.getInstance().getReference("users");
        //Intent i = getIntent();
//        _Name = i.getStringExtra("fName");
        //  _Pass = i.getStringExtra("password");
        //  showAllUserData();



        upName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString().trim();
                if (TextUtils.isEmpty(newName)){
                    name.setError("Full Name is required!");
                    return;
                }

             String userId = user.getUid();

             final DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(userId);
             Map<String, Object> map = new HashMap<>();
             map.put("fName",newName);
             docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void aVoid) {
                     Log.d(TAG,"Updated successfully! ");

                 }
             });
               // Toast.makeText(this,"Updated successfully!",Toast.LENGTH_LONG).show();

                /*  UserProfileChangeRequest profileUpdates =
                        new UserProfileChangeRequest.Builder()
                                .setDisplayName(newName)
                                .setPhotoUri(Uri.parse(
                                        "android.resource://com.example.saneh/drawable/photo"))
                                .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }

                        });
*/
              /*  UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)

                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                      Log.d(TAG, "User profile updated.");
                                }
                            }
                        });*/

            }
        });

        upPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = nPassword.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword)){
                    nPassword.setError("Password is required!");
                    return;
                }
                if (!isValidPassword(newPassword)){
                    nPassword.setError("Password's Regulations: \n -Must contains at least one digit \n -Must contains at least one lowercase character \n -Must contains at least one uppercase character \n -Must contains at least one special symbol (@#$%) \n -The length >= 8");
                    return;
                }
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }

                            }
                        });
            }
        });

        final ImageView returnPrf = findViewById(R.id.gotoProfile);
        returnPrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),profile.class);
                startActivity(i);
            }
        });


    }

    // private void showAllUserData() {
    //   Intent i =getIntent();

    //}

    private boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    /*public void update(View view){
        if(isNameChanged()){
            Toast.makeText(this,"Data has been updated!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Data is same and can not updated.",Toast.LENGTH_LONG).show();

    }*/

    // private boolean isPasswordChanged() {
    //}

    /*private boolean isNameChanged() {
        String newName = name.getText().toString().trim();
        if (!_Name.equals(newName)){
            reference.child(_Name).setValue(newName);
            return true;
        }
        return false;
    }*/
}
