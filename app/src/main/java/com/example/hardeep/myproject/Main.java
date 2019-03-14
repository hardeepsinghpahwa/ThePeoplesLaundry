package com.example.hardeep.myproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.admin.Admin;
import com.example.hardeep.myproject.user.The_user_profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends AppCompatActivity {

    TextView newacc;
    EditText password,email;
    Button button;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        button = findViewById(R.id.login);
        newacc = findViewById(R.id.newacc);

        email.setText("hardeep123.in@gmail.com");
        password.setText("iamadmin");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(email.getText().toString().equals("hardeep123.in@gmail.com") && password.getText().toString().equals("iamadmin"))
                {

                    final ProgressDialog progressDialog1 = new ProgressDialog(Main.this, R.style.MyAlertDialogStyle);
                    progressDialog1.setMessage("Logging You In");
                    progressDialog1.setTitle("Welcome ADMIN");
                    progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog1.show();
                    final Timer t = new Timer();
                            t.schedule(new TimerTask() {
                                public void run() {
                                    progressDialog1.dismiss();
                                    startActivity(new Intent(Main.this,Admin.class));
                                    t.cancel();
                                }
                            }, 3000);



                }
                else {
                    if (!Validate_email(email.getText().toString())) {
                        email.setError("Email format Incorrect");
                        email.requestFocus();
                    } else if (!Validate_password(password.getText().toString())) {
                        password.setError("Password format Incorrect");
                        email.requestFocus();
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(Main.this, R.style.MyAlertDialogStyle);
                        progressDialog.setMessage("Processing");
                        progressDialog.setTitle("Please wait");
                        progressDialog.show();

                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressDialog.dismiss();

                                        if (task.isSuccessful()) {
                                            Toast.makeText(Main.this, "Login Successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), The_user_profile.class));
                                        } else {
                                            Toast.makeText(Main.this, "Email And Password mismatch", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
                    }
                }   }});

    }


    public void Clickme(View view) {
        {
            Intent intent=new Intent(this,newaccount.class);
            startActivity(intent);
        }
    }

    private boolean Validate_password(String s) {
        if (password != null && password.length() > 6)
            return true;
        else
            return false;
    }

    private boolean Validate_email(String s) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email.getText().toString());

        return matcher.matches();
    }

}