package com.example.hardeep.myproject.user;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.get_details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

public class editpassword extends AppCompatActivity {

    EditText oldpass,newpass,renewpass;
    Button save;
    String userid,password;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);

        firebaseAuth=FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        renewpass = findViewById(R.id.renewpass);
        save = findViewById(R.id.chngpass);

        dataref = FirebaseDatabase.getInstance().getReference().child("1");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(userid);

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    get_password d = new get_password();
                    d.setPassword(dataSnapshot1.child(userid).getValue(get_details.class).getPassword());
                    password=d.getPassword().toString();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if(password.equals(oldpass.getText().toString()))
                {
                    if(!Validate_password(newpass.getText().toString()))
                    {
                        newpass.setError("Password must be greater than 6 characters");
                        newpass.requestFocus();
                    }
                    else {
                        if(!newpass.getText().toString().equals(renewpass.getText().toString()))
                        {
                            renewpass.setError("Passwords do not match");
                        }
                        else
                        {
                            final ProgressDialog progressDialog=ProgressDialog.show(editpassword.this,"Please wait","Updating Password");
                            firebaseAuth.getCurrentUser().updatePassword(renewpass.getText().toString());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("password").setValue(renewpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(editpassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else
                {oldpass.setError("Wrong Old Password"); oldpass.requestFocus();}


            }

        });
    }
    private boolean Validate_password(String s) {
        if (s != null && s.length() > 6)
            return true;
        else
            return false;
    }

    }


class get_password
        {
            String password;

            public get_password(String password) {
                this.password = password;
            }

            public get_password() {
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }