package com.example.hardeep.myproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


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
    TextView t;
    private static final int GALLERY_REQUEST = 1;
    private RelativeLayout layout;
    private Button button;
    private StorageReference storageref;
    private String user_id;
    private ImageButton profile_image;
    private Uri uri, resultUri;
    private EditText Name, password, username, email;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseFirestore firebaseFirestore;


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
        progressBar=findViewById(R.id.progressbar1);

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
                } else {

                    if (resultUri != null) {
                        user_id = auth.getCurrentUser().getUid();
                        final ProgressDialog pd = ProgressDialog.show(newaccount.this, "Please Wait", "Uploading Details");
                        storageref = storageref.child("Profile Pictures/" + user_id);
                        storageref.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                image = taskSnapshot.getDownloadUrl();
                                pd.dismiss();
                                progressBar.setVisibility(View.VISIBLE);

                                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        finish();
                                        Toast.makeText(newaccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(user_id);

                                        get_details details = new get_details(Name.getText().toString(), password.getText().toString(), username.getText().toString(), email.getText().toString(), image.toString());
                                        databaseReference.setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                    }
                                });
                            }
                        });


                    } else {
                        image = Uri.parse("http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png");

                        progressBar.setVisibility(View.VISIBLE);
                        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.INVISIBLE);
                                finish();
                                Toast.makeText(newaccount.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                user_id = auth.getCurrentUser().getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(user_id);

                                get_details details = new get_details(Name.getText().toString(), password.getText().toString(), username.getText().toString(), email.getText().toString(), image.toString());
                                databaseReference.setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        });

                    }
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
