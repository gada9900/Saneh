package com.example.saneh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class admin_settings extends AppCompatActivity {
    private FirebaseAuth firebaseAuth ;
    private Button  logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.LogOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(admin_settings.this,Login.class));
            }
        });
    }
}