package com.example.hardeep.myproject.admin.Add_order;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.myproject.AdapterClass;
import com.example.hardeep.myproject.Main;
import com.example.hardeep.myproject.R;
import com.example.hardeep.myproject.admin.Admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order_list extends AppCompatActivity {

    RecyclerView recyclerView;
    List<order_items> itemsList;
    AdapterClass adapterClass;
    DatabaseReference databaseReference;
    TextView totalamount;
    Button add_order_button;
    final String data = "012345AFGHILPQUVZ";
    Random random = new Random();
    String order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Intent i = getIntent();
        final String u_name = i.getStringExtra("username");
        final String name=i.getStringExtra("name");

        totalamount = findViewById(R.id.total_amount);
        add_order_button = findViewById(R.id.addorder);
        recyclerView = findViewById(R.id.item_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsList = new ArrayList<>();


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
        final String other1price = i.getStringExtra("other1_price");
        final String other2no = i.getStringExtra("other2_no");
        final String other2price = i.getStringExtra("other2_price");
        final String other3no = i.getStringExtra("other3_no");
        final String other3price = i.getStringExtra("other3_price");
        final String other1name = i.getStringExtra("other1_name");
        final String other2name = i.getStringExtra("other2_name");
        final String other3name = i.getStringExtra("other3_name");
        final String total_amount = i.getStringExtra("amount");


        int p=data.length();
            StringBuilder b = new StringBuilder();
            while (p-- != 0) {
                int character = (int)(Math.random()*data.length());
                b.append(data.charAt(character));
            }
            order_id=b.toString();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("Active Orders").child(order_id);

        Float total = Float.parseFloat(total_amount);

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
            itemsList.add(new order_items(other1name, other1no, other1price));
            total += Float.parseFloat(other1price);
        }
        if (!other2no.equals("")) {
            itemsList.add(new order_items(other2name, other2no, other2price));
            total += Float.parseFloat(other2price);
        }
        if (!other3no.equals("")) {
            itemsList.add(new order_items(other3name, other3no, other3price));
            total += Float.parseFloat(other3price);
        }

        totalamount.setText(Float.toString(total));
        adapterClass = new AdapterClass(this, itemsList);
        recyclerView.setAdapter(adapterClass);

        int count = 0;
        count = recyclerView.getAdapter().getItemCount();
        if (count == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setTitle("No items").setCancelable(false).setMessage("There are no items in the list").show();

        }

        add_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ProgressDialog progressDialog1 = new ProgressDialog(Order_list.this, R.style.MyAlertDialogStyle);
                        progressDialog1.setMessage("Adding Order");
                        progressDialog1.setTitle("Please Wait");
                        progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog1.show();
                        store_order_details thedetails = new store_order_details(shirtno, jeanno, bedsheetno, sareeno, coatno, blanketno, socksno, sweatero, suitno, ungarmno, other1no, other2no, other3no, other1name, other2name, other3name, other1price, other2price, other3price,u_name,name,totalamount.getText().toString(),shirtprice,jeanprice,bedsheetprice,sareeprice,coatprice,blanketprice,socksprice,sweaterprice,suitprice,ungarmprice);
                        databaseReference.setValue(thedetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog1.dismiss();

                                Toast.makeText(getApplicationContext(), "Order Added Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Order Failed To Be Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
