package com.example.saneh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class admin_settings extends AppCompatActivity {
    private FirebaseAuth firebaseAuth ;
    private Button  logout,help;
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
        help=findViewById(R.id.logOut2);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),adminEdit.class);
                startActivity(i);
            }
        });

        /*settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),admin_settings.class);
                startActivity(i);
            }
        });*/
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:" + "appsaneh@gmail.com"));
                startActivity(intent);*/

               /* Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.putExtra(Intent.EXTRA_SUBJECT, "I had an issue while using Saneh");
                intent.putExtra(Intent.EXTRA_TEXT, "Dear Saneh support team \n" +
                        "I had an issue on page (the page name)\n" +
                        "while I was trying to do (the action you were trying)\n" +
                        "\n" +
                        "(your name)");
                intent.setData(Uri.parse("mailto:SanehSupportTeam@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);*/

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[] { "SanehSupportTeam@gmail.com" });
                i.putExtra(Intent.EXTRA_SUBJECT, "I had an issue while using Saneh");
                i.putExtra(Intent.EXTRA_TEXT   , "Dear Saneh support team, \n" +
                        "I had an issue on page (the page name)\n" +
                        "while I was trying to do (the action you were trying)\n" +
                        "\n" +
                        "Admin: (your name)");
                try {
                    startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(admin_settings.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

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
                Intent i = new Intent(getApplicationContext(),adminAddList.class);
                startActivity(i);
            }
        });
    }
}