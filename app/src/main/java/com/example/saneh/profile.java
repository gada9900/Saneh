package com.example.saneh;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile  extends AppCompatActivity {
    private FirebaseAuth firebaseAuth ;
    private FirebaseFirestore fStore;
    private Button  logout , Edit;
    private TextView EMail,fullName;
    private String userId;
    //  private Button Help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile1);
        EMail=findViewById(R.id.EMailProf);
        fullName=findViewById(R.id.fullName);
        Button Help=findViewById(R.id.textView3);
        Edit =findViewById(R.id.EDITPROf);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),editProfileInfo.class);
                startActivity(i);
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try {
                Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:" + "appsaneh@gmail.com"));
                startActivity(intent);
                //}catch(Exception e){
                //    Toast.makeText(this, "Sorry we can not find the email application",Toast.LENGTH_LONG).show();
                //  }
               /* Intent i=new Intent(getApplicationContext(),send_email.class);
                startActivity(i);*/
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userId=firebaseAuth.getUid();
        DocumentReference documentReference=fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fullName.setText(value.getString("fName"));
                EMail.setText(value.getString("email"));
            }
        });

        logout = (Button)findViewById(R.id.LogOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profile.this,Login.class));
            }
        });


    }
}
