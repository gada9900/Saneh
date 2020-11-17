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
    private EditText name, nPassword,conPassnew;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthSL;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userId ;
    private Button UpdateBu1,updateBu2;
    private FirebaseFirestore fStore;
    final String[] oldName = new String[1];




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_info);

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        nPassword = (EditText) findViewById(R.id.editTextTextPassword3);
        conPassnew =(EditText) findViewById(R.id.editTextTextPassword2);

        UpdateBu1 = (Button) findViewById(R.id.updateBu2);
        updateBu2 = (Button) findViewById(R.id.updateBu);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getUid();


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("fName"));

            }
        });
        //display name

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        oldName[0] = document.getString("fName");

                    }
                }
            }
        });


        UpdateBu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
       updateBu2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               update2(v);
           }
       });
        final ImageView returnPrf = findViewById(R.id.gotoProfile);
        returnPrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), profile.class);
                startActivity(i);
            }
        });


    }

    // private void showAllUserData() {
    //   Intent i =getIntent();

    //}

    public void update(View view) {
        if (isNameChanged() ) {
            Toast.makeText(this, " Name has been updated!", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Name is remain the same", Toast.LENGTH_LONG).show();
        /*if(isPasswordChanged()){
            Toast.makeText(this, "Password has been updated!", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Password is same.", Toast.LENGTH_LONG).show();
    */
    }
         public void update2(View view){
             if(isPasswordChanged()){
                 Toast.makeText(this, "Password has been updated!", Toast.LENGTH_LONG).show();
             } else
                 Toast.makeText(this, "Password is remain the same", Toast.LENGTH_LONG).show();
         }

    private boolean isPasswordChanged() {
        String newPassword = nPassword.getText().toString().trim();
        String conPas = conPassnew.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            nPassword.setError("Password is required!");
            return false;
        }
        if (!isValidPassword(newPassword)) {
            nPassword.setError("Password's Regulations: \n -Must contains at least one digit \n -Must contains at least one lowercase character \n -Must contains at least one uppercase character \n -Must contains at least one special symbol (@#$%) \n -The length >= 8");
            return false;
        }
        if (!newPassword.equals(conPas)){
            conPassnew.setError("Your password doesn't match the confirmed password!");
            return false;
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
        return true;
    }

    private boolean isNameChanged() {
        String newName = name.getText().toString().trim();

        if(oldName[0].equals(newName)){
            return false;
        }

        if (TextUtils.isEmpty(newName)) {
            name.setError("Full Name is required!");
            return false;
        }
        if (newName.length()>20){
            name.setError("the name length should be less than 20 characters.");
            return false;
        }
        if (stringContainsNumber(newName) || stringContainsSymbols(newName)){
            name.setError("the full name contains only characters.");
            return false;
        }
        if (newName.length() < 3){
            name.setError("the full name length should be greater than 3 characters.");
            return false;
        }

        String userId1 = user.getUid();

        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(userId1);
        Map<String, Object> map = new HashMap<>();
        map.put("fName", newName);
        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Updated successfully! ");

            }
        });
return true;
    }

    private boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    public boolean stringContainsNumber( String s )
    {
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }
    public boolean stringContainsSymbols( String s )
    {
        return Pattern.compile( "[@#$%^&+=]" ).matcher( s ).find();
    }
}
