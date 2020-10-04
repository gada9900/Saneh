package com.example.saneh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class admin_settings extends AppCompatActivity {
    private FirebaseAuth firebaseAuth ;
    private Button  logout;
    private ImageView edit, settings, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.LogOut);
        edit = findViewById(R.id.imageView32);
        settings = findViewById(R.id.imageView27);
        add = findViewById(R.id.imageView21);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminEdit.class);
                startActivity(i);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_settings.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(admin_settings.this,Login.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminAdd.class);
                startActivity(i);
            }
        });
    }
}