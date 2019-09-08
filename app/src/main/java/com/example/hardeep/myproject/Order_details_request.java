package com.example.hardeep.myproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hardeep.myproject.user.The_user_profile;
import com.example.hardeep.myproject.user.order_items;
import com.example.hardeep.myproject.user.store_order_details;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Order_details_request extends AppCompatActivity {

    RecyclerView itemlistrecyview;
    List<order_items> itemsList;
    AdapterClass adapterClass;
    EditText address1, address2;
    RadioButton add1, add2;
    final String data = "012345AFGHILPQUVZ";
    String address, user_id;
    Button edit1, edit2;
    Button requestorder;
    DatabaseReference databaseReference;
    String order_id;
    DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemlistrecyview = findViewById(R.id.orderlistrecyclerview);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        add1 = findViewById(R.id.radioadd1);
        add2 = findViewById(R.id.radioadd2);
        requestorder = findViewById(R.id.request);
        edit1 = findViewById(R.id.editaddress1);
        edit2 = findViewById(R.id.editaddress2);
        address1.setEnabled(false);
        address2.setEnabled(false);
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataref = FirebaseDatabase.getInstance().getReference().child("1").child("User details").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("Requested Orders");

        Intent i = getIntent();
        itemlistrecyview.setLayoutManager(new LinearLayoutManager(this));

        itemsList = new ArrayList<>();


        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("address1").getValue(String.class) == null) {
                    address1.setText("");
                } else {
                    address1.setText(dataSnapshot.child("address1").getValue(String.class));
                }


                if (dataSnapshot.child("address2").getValue(String.class) == null) {
                    address2.setText("");
                } else {
                    address2.setText(dataSnapshot.child("address2").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final String shirtno = i.getStringExtra("shirt_no");
        final String shirtprice = i.getStringExtra("shirt_price");
        final String jeanno = i.getStringExtra("jeans_no");
        final String jeanprice = i.getStringExtra("jeans_price");
        final String bedsheetno = i.getStringExtra("bedsheet_no");
        final String bedsheetprice = i.getStringExtra("bedsheet_price");
        final String sareeno = i.getStringExtra("saree_no");
        final String sareeprice = i.getStringExtra("saree_price");
        final String coatno = i.getStringExtra("coat_no");
        final String coatprice = i.getStringExtra("coat_price");
        final String blanketno = i.getStringExtra("blanket_no");
        final String blanketprice = i.getStringExtra("blanket_price");
        final String socksno = i.getStringExtra("socks_no");
        final String socksprice = i.getStringExtra("socks_price");
        final String suitno = i.getStringExtra("suit_no");
        final String sweatero = i.getStringExtra("sweater_no");
        final String sweaterprice = i.getStringExtra("sweater_price");
        final String suitprice = i.getStringExtra("suit_price");
        final String ungarmno = i.getStringExtra("ungarm_no");
        final String ungarmprice = i.getStringExtra("ungarm_price");
        final String other1no = i.getStringExtra("other1_no");
        final String other2no = i.getStringExtra("other2_no");
        final String other3no = i.getStringExtra("other3_no");
        final String other1name = i.getStringExtra("other1_name");
        final String other2name = i.getStringExtra("other2_name");
        final String other3name = i.getStringExtra("other3_name");


        if (!shirtno.equals("")) {
            itemsList.add(new order_items("Shirts", shirtno, shirtprice));
        }
        if (!jeanno.equals("")) {
            itemsList.add(new order_items("Jean", jeanno, jeanprice));
        }
        if (!bedsheetno.equals("")) {
            itemsList.add(new order_items("Bedsheet", bedsheetno, bedsheetprice));
        }
        if (!sareeno.equals("")) {
            itemsList.add(new order_items("Saree", sareeno, sareeprice));
        }
        if (!coatno.equals("")) {
            itemsList.add(new order_items("Coat", coatno, coatprice));
        }
        if (!blanketno.equals("")) {
            itemsList.add(new order_items("Blanket", blanketno, blanketprice));
        }
        if (!socksno.equals("")) {
            itemsList.add(new order_items("Socks", socksno, socksprice));
        }
        if (!suitno.equals("")) {
            itemsList.add(new order_items("Suit", suitno, suitprice));
        }
        if (!ungarmno.equals("")) {
            itemsList.add(new order_items("Under Garments", ungarmno, ungarmprice));
        }
        if (!sweatero.equals("")) {
            itemsList.add(new order_items("Sweaters", sweatero, sweaterprice));
        }
        if (!other1no.equals("")) {
            itemsList.add(new order_items(other1name, other1no, ""));
        }
        if (!other2no.equals("")) {
            itemsList.add(new order_items(other2name, other2no, ""));
        }
        if (!other3no.equals("")) {
            itemsList.add(new order_items(other3name, other3no, ""));
        }

        adapterClass = new AdapterClass(this, itemsList);
        itemlistrecyview.setAdapter(adapterClass);

        int count = 0;
        count = itemlistrecyview.getAdapter().getItemCount();
        if (count == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setTitle("No items").setCancelable(false).setMessage("There are no items in the list").show();

        }

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add2.isChecked()) {
                    add2.setChecked(false);
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (add1.isChecked()) {
                    add1.setChecked(false);
                }

            }
        });


        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit1.getText().toString().matches("Edit")) {
                    address1.requestFocus();
                    address1.setEnabled(true);
                    address1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            edit1.setText("Save");
                        }
                    });
                } else if (edit1.getText().toString().matches("Save")) {
                    dataref.child("address1").setValue(address1.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            address1.setEnabled(false);
                            edit1.setText("Edit");
                            Toast.makeText(Order_details_request.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit2.getText().toString().matches("Edit")) {
                    address2.requestFocus();
                    address2.setEnabled(true);

                    address2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            edit2.setText("Save");
                        }
                    });
                } else if (edit2.getText().toString().matches("Save")) {
                    dataref.child("address2").setValue(address2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            address2.setEnabled(false);
                            edit2.setText("Edit");
                            Toast.makeText(Order_details_request.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        requestorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (add1.isChecked()) {
                    address = address1.getText().toString();
                } else if (add2.isChecked()) {
                    address = address2.getText().toString();
                }
                if (!add1.isChecked() && !add2.isChecked()) {
                    Toast.makeText(Order_details_request.this, "Select Any Address First", Toast.LENGTH_SHORT).show();
                }
                else if(address.matches(""))
                {
                    Toast.makeText(Order_details_request.this, "Address can't be empty", Toast.LENGTH_SHORT).show();
                }

                else {

                    final android.app.AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(Order_details_request.this)
                            .setMessage("Sending Order Request")
                            .setCancelable(false)
                            .setTheme(R.style.Custom)
                            .build();
                    alertDialog.show();

                    int p = data.length();
                    StringBuilder b = new StringBuilder();
                    while (p-- != 0) {
                        int character = (int) (Math.random() * data.length());
                        b.append(data.charAt(character));
                    }
                    order_id = b.toString();

                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                    Log.i("date",currentDateTimeString);

                    store_order_details thedetails = new store_order_details(shirtno, jeanno, bedsheetno, sareeno, coatno, blanketno, socksno, sweatero, suitno, ungarmno, other1no, other2no, other3no, other1name, other2name, other3name, user_id, shirtprice, jeanprice, bedsheetprice, sareeprice, coatprice, blanketprice, socksprice, sweaterprice, suitprice, ungarmprice, address,currentDateTimeString);
                    databaseReference.child(order_id).setValue(thedetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            alertDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), The_user_profile.class));
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            Toast.makeText(Order_details_request.this, "Order Request Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Order Request Failed to send", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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
        overridePendingTransition(R.anim.fromright, R.anim.toright);
    }
}
