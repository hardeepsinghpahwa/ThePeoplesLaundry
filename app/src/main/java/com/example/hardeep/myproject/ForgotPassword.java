package com.example.hardeep.myproject;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class ForgotPassword extends AppCompatActivity {

    String email;
    TextView resetpasswordtext;
    Button reset;
    EditText email1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Reset Your Password");
        resetpasswordtext = findViewById(R.id.passwordresetsent);

        email = getIntent().getStringExtra("email");
        reset = findViewById(R.id.reset);
        email1 = findViewById(R.id.emailreset);

        if (!email.equals("")) {
            email1.setText(email);
        }


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("1").child("User details").orderByChild("email").equalTo(email1.getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            final AlertDialog alertDialog = new SpotsDialog.Builder()
                                    .setContext(ForgotPassword.this)
                                    .setMessage("Sending Password Reset Email")
                                    .setCancelable(false)
                                    .setTheme(R.style.Custom)
                                    .build();
                            alertDialog.show();

                            FirebaseAuth.getInstance().sendPasswordResetEmail(email1.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                reset.setClickable(false);
                                                resetpasswordtext.setVisibility(View.VISIBLE);
                                                alertDialog.dismiss();
                                            }
                                            else {
                                                Toast.makeText(ForgotPassword.this, "Failed to Send Reset Link", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        } else {
                            email1.setError("Email Not Registered");
                            email1.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_out, R.anim.right_in);
    }
}
