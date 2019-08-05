package com.example.hardeep.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;


public class newaccount extends AppCompatActivity {


    public void Choose_image(View view) {

        //opening gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST);
    }


    //profile image picking and uploading
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            uri = data.getData();

            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(200, 200).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profile_image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public Uri image;
    String emailfromdb, usernamefromdb;
    TextView t;
    int c = 0, o = 0;
    private static final int GALLERY_REQUEST = 1;
    private RelativeLayout layout;
    private Button button, verifyemail;
    private StorageReference storageref;
    private String user_id;
    private ImageButton profile_image;
    private Uri uri, resultUri;
    int p = 0;
    private EditText Name, password, username, email, reenterpassword;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);

        Name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        profile_image = findViewById(R.id.image);
        button = findViewById(R.id.createacc);
        layout = findViewById(R.id.layout);
        verifyemail = findViewById(R.id.verifybutton);
        reenterpassword = findViewById(R.id.reenterpassword);

        storageref = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Email and password validation
                if (!Validate_email(email.getText().toString())) {
                    email.setError("Incorrect Email");
                    email.requestFocus();

                } else if (!Validate_password(password.getText().toString())) {
                    password.setError("Password must be greater than 6 characters");
                    password.requestFocus();
                } else if (!password.getText().toString().equals(reenterpassword.getText().toString())) {
                    reenterpassword.setError("Password does not match");
                    reenterpassword.requestFocus();

                } else {

                    p=0;
                    final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("1").child("User details");


                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            databaseReference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        usernamefromdb = dataSnapshot1.child("username").getValue(String.class);
                                        if (username.getText().toString().equals(usernamefromdb)) {
                                            if (o == 0) {
                                                username.setError("Already In Use");
                                                username.requestFocus();
                                                p = 1;
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            int a = 0;
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                emailfromdb = dataSnapshot1.child("email").getValue(String.class);
                                if (email.getText().toString().equals(emailfromdb)) {
                                    if (c == 0)
                                        email.setError("Already registered");
                                    email.requestFocus();
                                    a = 1;
                                    break;
                                }
                            }
                            if (a == 0 && p == 0) {
                                c = 1;
                                o = 1;
                                if (resultUri != null) {
                                    final AlertDialog alertDialog = new SpotsDialog.Builder()
                                            .setContext(newaccount.this)
                                            .setMessage("Creating your account")
                                            .setCancelable(false)
                                            .setTheme(R.style.Custom)
                                            .build();

                                    alertDialog.show();
                                    button.setClickable(false);
                                    user_id = auth.getCurrentUser().getUid();
                                    final ProgressDialog pd = ProgressDialog.show(newaccount.this, "Please Wait", "Uploading Details");
                                    storageref = storageref.child("Profile Pictures/" + user_id);
                                    UploadTask uploadTask = storageref.putFile(resultUri);
                                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            if (!task.isSuccessful()) {
                                                throw task.getException();
                                            }

                                            // Continue with the task to get the download URL
                                            return storageref.getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()) {
                                                Uri downloadUri = task.getResult();
                                                image = downloadUri;
                                                pd.dismiss();

                                                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(user_id);

                                                        get_details details = new get_details(Name.getText().toString(), password.getText().toString(), username.getText().toString(), email.getText().toString(), image.toString());
                                                        databaseReference.setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                button.setClickable(false);
                                                                finish();
                                                                alertDialog.dismiss();
                                                                Toast.makeText(newaccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {

                                    image = Uri.parse("http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png");
                                    button.setClickable(false);
                                    final AlertDialog alertDialog = new SpotsDialog.Builder()
                                            .setContext(newaccount.this)
                                            .setMessage("Creating your account")
                                            .setCancelable(false)
                                            .setTheme(R.style.Custom)
                                            .build();

                                    alertDialog.show();
                                    auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            user_id = auth.getCurrentUser().getUid();

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(user_id);

                                            get_details details = new get_details(Name.getText().toString(), password.getText().toString(), username.getText().toString(), email.getText().toString(), image.toString());
                                            databaseReference.setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    email.setFocusable(false);
                                                    Toast.makeText(newaccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    button.setClickable(false);
                                                    FirebaseAuth.getInstance().signOut();
                                                    finish();

                                                    alertDialog.dismiss();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private boolean Validate_password(String s) {
        if (s != null && s.length() > 6)
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