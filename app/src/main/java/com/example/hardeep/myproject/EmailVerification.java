package com.example.hardeep.myproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerification extends AppCompatActivity {

    Button verifyemail,checkverified;
    FirebaseUser user;
    TextView textTimer,verificationtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        verifyemail=findViewById(R.id.verifybutton);
        checkverified=findViewById(R.id.checkemailverified);
        textTimer = findViewById(R.id.timer);
        user= FirebaseAuth.getInstance().getCurrentUser();
        verificationtext=findViewById(R.id.verificationtext);


        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            verificationtext.setVisibility(View.VISIBLE);
                            textTimer.setVisibility(View.VISIBLE);
                            textTimer.setClickable(false);
                            textTimer.setTextColor(Color.WHITE);
                            final int[] time = {30};

                            new CountDownTimer(30000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    textTimer.setText("0:"+checkDigit(time[0]));
                                    time[0]--;
                                }

                                public void onFinish() {
                                    textTimer.setText("Send It Again");
                                    textTimer.setTextColor(Color.BLUE);
                                    textTimer.setClickable(true);

                                    textTimer.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            textTimer.setClickable(false);
                                            textTimer.setTextColor(Color.BLUE);
                                            verifyemail.setClickable(false);
                                            final int[] time = {30};

                                            new CountDownTimer(30000, 1000) {

                                                public void onTick(long millisUntilFinished) {
                                                    textTimer.setText("0:"+checkDigit(time[0]));
                                                    time[0]--;
                                                }

                                                public void onFinish() {
                                                    textTimer.setText("Link Already Sent");
                                                    textTimer.setTextColor(Color.BLUE);
                                                    textTimer.setClickable(false);
                                                }

                                            }.start();
                                        }
                                    });
                                }

                            }.start();

                        }
                    }
                });
            }
            public String checkDigit(int number) {
                return number <= 9 ? "0" + number : String.valueOf(number);
            }
        });


        checkverified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EmailVerification.this,Main.class));
            }
        });
    }
}
