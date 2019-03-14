package com.example.hardeep.myproject.admin.Add_order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.Main;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class add_order extends AppCompatActivity {


    DatabaseReference databaseReference;
    TextView setname, setusername, setemail;
    ImageButton imageButton;
    RelativeLayout order, profile;
    ImageView imageView;
    RelativeLayout relativeLayout;
    EditText search, shirt, jeans, bedsheet, saree, coat, blanket, socks, sweater, suit, undergarments, other1, other2, other3, other1price, other2price, other3price, other1name, other2name, other3name;
    Button proceed;
    TextView shirtprice;
    float amount = 0;

    int shirt_cost, jeans_cost, bed_cost, saree_cost, coat_cost, blanket_cost, socks_cost, sweater_cost, suit_cost, underg_cost, other1_cost, other2_cost, other3_cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        shirtprice = findViewById(R.id.shirtprice);
        imageView = findViewById(R.id.imageprofile);
        search = findViewById(R.id.searchtext);
        imageButton = findViewById(R.id.searchbutton);
        setemail = findViewById(R.id.setemail);
        profile = findViewById(R.id.displayprofile);
        relativeLayout = findViewById(R.id.layoutid);
        order = findViewById(R.id.order);
        setname = findViewById(R.id.setname);
        setusername = findViewById(R.id.setusername);
        search.setText("Hardeeppahwa");
        proceed = findViewById(R.id.proceed);
        other1price = findViewById(R.id.other1price);
        other2price = findViewById(R.id.other2price);
        other3price = findViewById(R.id.other3price);
        other1name = findViewById(R.id.other1name);
        other2name = findViewById(R.id.other2name);
        other3name = findViewById(R.id.other3name);


        shirt = findViewById(R.id.shirt);
        jeans = findViewById(R.id.jeans);
        bedsheet = findViewById(R.id.bedsheet);
        saree = findViewById(R.id.saree);
        coat = findViewById(R.id.coat);
        blanket = findViewById(R.id.blanket);
        socks = findViewById(R.id.socks);
        sweater = findViewById(R.id.sweater);
        suit = findViewById(R.id.ladiessuit);
        undergarments = findViewById(R.id.undergarments);
        other1 = findViewById(R.id.other1);
        other2 = findViewById(R.id.other2);
        other3 = findViewById(R.id.other3);


        shirt.setText("");
        saree.setText("");
        bedsheet.setText("");
        coat.setText("");
        blanket.setText("");
        suit.setText("");
        sweater.setText("");
        undergarments.setText("");
        jeans.setText("");
        socks.setText("");
        other1.setText("");
        other2.setText("");
        other3.setText("");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("1").child("User details");


        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {

                    int i = 0;
                    final ProgressDialog progressDialog = ProgressDialog.show(add_order.this, "Please Wait", "Searching");

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String u = dataSnapshot1.getKey();
                            String username = dataSnapshot1.child("username").getValue(String.class);
                            String name = dataSnapshot1.child("name").getValue(String.class);
                            String email = dataSnapshot1.child("email").getValue(String.class);
                            String image = dataSnapshot1.child("image").getValue(String.class);

                            if (search.getText().toString().equals(username)) {
                                progressDialog.dismiss();
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                Toast.makeText(add_order.this, "Search Found", Toast.LENGTH_SHORT).show();
                                setname.setText(name);
                                setemail.setText(email);
                                setusername.setText(username);
                                Picasso.with(add_order.this).load(image).fit().centerCrop().into(imageView);
                                profile.setVisibility(View.VISIBLE);
                                order.setVisibility(View.VISIBLE);
                                i = 1;
                                break;
                            }

                        }
                        if (i == 0) {
                            progressDialog.dismiss();
                            Toast.makeText(add_order.this, "Search Not Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!other1.getText().toString().matches("")) {
                    if (other1name.getText().toString().matches("")) {
                        other1name.setError("Item's name required");
                        other1name.requestFocus();
                    }

                    if (other1price.getText().toString().matches("")) {
                        other1price.setError("Total amount required");
                        other1price.requestFocus();
                    }
                }

                if (!other2.getText().toString().matches("")) {
                    if (other2name.getText().toString().matches("")) {
                        other2name.setError("Item's name required");
                        other2name.requestFocus();
                    }

                    if (other2price.getText().toString().matches("")) {
                        other2price.setError("Total amount required");
                        other2price.requestFocus();
                    }
                }
                if (!other3.getText().toString().matches("")) {
                    if (other3name.getText().toString().matches("")) {
                        other3name.setError("Item's name required");
                        other3name.requestFocus();
                    }

                    if (other3price.getText().toString().matches("")) {
                        other3price.setError("Total amount required");
                        other3price.requestFocus();
                    }
                }

                if (other1price.getError() == null && other1name.getError() == null && other2price.getError() == null && other2name.getError() == null && other3price.getError() == null && other3name.getError() == null) {

                    final ProgressDialog progressDialog1 = new ProgressDialog(add_order.this, R.style.MyAlertDialogStyle);
                    progressDialog1.setMessage("Processing");
                    progressDialog1.setTitle("Please Wait");
                    progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog1.show();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            progressDialog1.dismiss();
                            if (!shirt.getText().toString().matches("")) {
                                shirt_cost = 5 * Integer.parseInt(shirt.getText().toString());
                                amount += shirt_cost;
                            }
                            if (!jeans.getText().toString().matches("")) {
                                jeans_cost = 7 * Integer.parseInt(jeans.getText().toString());
                                amount += jeans_cost;
                            }
                            if (!bedsheet.getText().toString().matches("")) {
                                bed_cost = 10 * Integer.parseInt(bedsheet.getText().toString());
                                amount += bed_cost;
                            }
                            if (!saree.getText().toString().matches("")) {
                                saree_cost = 20 * Integer.parseInt(saree.getText().toString());
                                amount += saree_cost;
                            }
                            if (!sweater.getText().toString().matches("")) {
                                sweater_cost = 10 * Integer.parseInt(sweater.getText().toString());
                                amount += sweater_cost;
                            }
                            if (!socks.getText().toString().matches("")) {
                                socks_cost = 2 * Integer.parseInt(socks.getText().toString());
                                amount += socks_cost;
                            }
                            if (!suit.getText().toString().matches("")) {
                                suit_cost = 10 * Integer.parseInt(suit.getText().toString());
                                amount += suit_cost;
                            }
                            if (!undergarments.getText().toString().matches("")) {
                                underg_cost = 3 * Integer.parseInt(undergarments.getText().toString());
                                amount += underg_cost;
                            }
                            if (!coat.getText().toString().matches("")) {
                                coat_cost = 50 * Integer.parseInt(coat.getText().toString());
                                amount += coat_cost;
                            }
                            if (!blanket.getText().toString().matches("")) {
                                blanket_cost = 200 * Integer.parseInt(blanket.getText().toString());
                                amount += blanket_cost;
                            }


                            Intent i = new Intent(add_order.this, Order_list.class);
                            i.putExtra("username", setusername.getText());
                            i.putExtra("name",setname.getText());

                            i.putExtra("shirt_no", shirt.getText().toString());
                            i.putExtra("shirt_price", Integer.toString(shirt_cost));

                            i.putExtra("jeans_no", jeans.getText().toString());
                            i.putExtra("jeans_price", Integer.toString(jeans_cost));

                            i.putExtra("bedsheet_no", bedsheet.getText().toString());
                            i.putExtra("bedsheet_price", Integer.toString(bed_cost));

                            i.putExtra("saree_no", saree.getText().toString());
                            i.putExtra("saree_price", Integer.toString(saree_cost));

                            i.putExtra("coat_no", coat.getText().toString());
                            i.putExtra("coat_price", Integer.toString(coat_cost));

                            i.putExtra("blanket_no", blanket.getText().toString());
                            i.putExtra("blanket_price", Integer.toString(blanket_cost));

                            i.putExtra("socks_no", socks.getText().toString());
                            i.putExtra("socks_price", Integer.toString(socks_cost));

                            i.putExtra("sweater_no", sweater.getText().toString());
                            i.putExtra("sweater_price", Integer.toString(sweater_cost));

                            i.putExtra("suit_no", suit.getText().toString());
                            i.putExtra("suit_price", Integer.toString(suit_cost));

                            i.putExtra("ungarm_no", undergarments.getText().toString());
                            i.putExtra("ungarm_price", Integer.toString(underg_cost));

                            i.putExtra("other1_no", other1.getText().toString());
                            i.putExtra("other1_price", other1price.getText().toString());

                            i.putExtra("other2_no", other2.getText().toString());
                            i.putExtra("other2_price", other2price.getText().toString());

                            i.putExtra("other3_no", other3.getText().toString());
                            i.putExtra("other3_price", other3price.getText().toString());

                            i.putExtra("other1_name", other1name.getText().toString());

                            i.putExtra("other2_name", other2name.getText().toString());

                            i.putExtra("other3_name", other3name.getText().toString());

                            i.putExtra("amount", Float.toString(amount));

                            startActivity(i);


                            t.cancel();
                        }
                    }, 3000);
                }
            }
        });


    }
}
