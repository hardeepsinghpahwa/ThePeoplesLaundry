package com.example.hardeep.myproject.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.Main;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Admin;
import com.example.hardeep.myproject.get_details;
import com.example.hardeep.myproject.newaccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {

    public void Choose_the_image(View view) {

        //opening gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            u = data.getData();
            CropImage.activity(u).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(200,200).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultu = result.getUri();
                image.setImageURI(resultu);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private EditText name, username;
    TextView email;
    Uri u,resultu,rr;
    private static final int GALLERY_REQUEST = 1;
    ImageButton image;
    DatabaseReference databaseReference,dataref;
    FirebaseAuth firebaseAuth;
    String userid, uri;
    Button button;
    StorageReference storageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        name = findViewById(R.id.name1);
        email = findViewById(R.id.email1);
        username = findViewById(R.id.username1);
        button = findViewById(R.id.update);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("1");
        storageref= FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        image = findViewById(R.id.image1);

        dataref = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(userid);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                display(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (resultu != null) {
                final ProgressDialog pd= ProgressDialog.show(editprofile.this, "Please Wait", "Updating Details");
                storageref = storageref.child("Profile Pictures/" + UUID.randomUUID().toString());
                storageref.putFile(resultu).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        rr = taskSnapshot.getDownloadUrl();
                        pd.dismiss();

                        dataref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                                dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                if(rr!=null)
                                {
                                    dataSnapshot.getRef().child("image").setValue(rr.toString());
                                    Toast.makeText(editprofile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(editprofile.this,"Error Updating profile picture",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
            else{

                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final ProgressDialog progressDialog1=ProgressDialog.show(editprofile.this,"Please Wait","Updating details");
                        final Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            public void run() {
                                finish();
                                progressDialog1.dismiss();
                                t.cancel();

                            }
                        }, 3000);

                        dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }

    });
    }


    private void display(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            get_details d = new get_details();
                            d.setName(dataSnapshot1.child(userid).getValue(get_details.class).getName());
                            d.setEmail(dataSnapshot1.child(userid).getValue(get_details.class).getEmail());
                            d.setImage(dataSnapshot1.child(userid).getValue(get_details.class).getImage());
                            d.setUsername(dataSnapshot1.child(userid).getValue(get_details.class).getUsername());
                            uri = d.getImage();
                            name.setText(d.getName());
                            email.setText(d.getEmail());
                            username.setText(d.getUsername());
                            Picasso.with(editprofile.this).load(uri).fit().centerCrop().into(image);
                        }
                    }
        }
