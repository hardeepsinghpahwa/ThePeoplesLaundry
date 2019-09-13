package com.example.hardeep.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.hardeep.myproject.user.order_items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRejected extends AppCompatActivity {

    String orderid;
    RecyclerView recyclerView;
    TextView total_amoun;
    List<order_items> itemsList;
    DatabaseReference databaseReference;
    AdapterClass adapterClass;
    TextView reason;

    String shirtno;
    String jeanno;
    String bedsheetno;
    String sareeno;
    String coatno;
    String blanketno;
    String socksno;
    String suitno;
    String sweatero;
    String ungarmno;
    String shirtprice;
    String jeanprice;
    String bedsheetprice;
    String sareeprice;
    String coatprice;
    String blanketprice;
    String socksprice;
    String suitprice;
    String sweaterprice;
    String ungarmprice;
    String other1no;
    String other1price;
    String other2no;
    String other2price;
    String other3no;
    String other3price;
    String other1name;
    String other2name;
    String other3name;
    String total_amount;
    String reas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rejected);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderid = getIntent().getStringExtra("orderid");
        Log.i("id", orderid);

        recyclerView = findViewById(R.id.viewrejectedrecyclerview);
        reason = findViewById(R.id.reason);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("2").child("Rejected Orders").child(orderid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shirtno = dataSnapshot.child("shirt_count").getValue(String.class);
                jeanno = dataSnapshot.child("jeans_count").getValue(String.class);
                bedsheetno = dataSnapshot.child("bedsheet_count").getValue(String.class);
                sareeno = dataSnapshot.child("saree_count").getValue(String.class);
                coatno = dataSnapshot.child("coat_count").getValue(String.class);
                blanketno = dataSnapshot.child("blanket_count").getValue(String.class);
                socksno = dataSnapshot.child("socks_count").getValue(String.class);
                suitno = dataSnapshot.child("suit_count").getValue(String.class);
                sweatero = dataSnapshot.child("sweater_count").getValue(String.class);
                ungarmno = dataSnapshot.child("undergarments_count").getValue(String.class);
                other1no = dataSnapshot.child("other1_count").getValue(String.class);
                other1price = dataSnapshot.child("other1_price").getValue(String.class);
                other2no = dataSnapshot.child("other2_count").getValue(String.class);
                other2price = dataSnapshot.child("other2_price").getValue(String.class);
                other3no = dataSnapshot.child("other3_count").getValue(String.class);
                other3price = dataSnapshot.child("other3_price").getValue(String.class);
                other1name = dataSnapshot.child("other1_name").getValue(String.class);
                other2name = dataSnapshot.child("other2_name").getValue(String.class);
                other3name = dataSnapshot.child("other3_name").getValue(String.class);
                total_amount = dataSnapshot.child("totalamount").getValue(String.class);
                shirtprice = dataSnapshot.child("shirt_price").getValue(String.class);
                jeanprice = dataSnapshot.child("jeans_price").getValue(String.class);
                bedsheetprice = dataSnapshot.child("bedsheet_price").getValue(String.class);
                sareeprice = dataSnapshot.child("saree_price").getValue(String.class);
                coatprice = dataSnapshot.child("coat_price").getValue(String.class);
                blanketprice = dataSnapshot.child("blanket_price").getValue(String.class);
                socksprice = dataSnapshot.child("socks_price").getValue(String.class);
                suitprice = dataSnapshot.child("suit_price").getValue(String.class);
                sweaterprice = dataSnapshot.child("sweater_price").getValue(String.class);
                ungarmprice = dataSnapshot.child("undergarments_price").getValue(String.class);
                reas = dataSnapshot.child("reason").getValue(String.class);

                itemsList = new ArrayList<>();
                itemsList.clear();

                if (!shirtprice.equals("0")) {
                    itemsList.add(new order_items("Shirts", shirtno, shirtprice));
                }
                if (!jeanprice.equals("0")) {
                    itemsList.add(new order_items("Jean", jeanno, jeanprice));
                }
                if (!bedsheetprice.equals("0")) {
                    itemsList.add(new order_items("Bedsheet", bedsheetno, bedsheetprice));
                }
                if (!sareeprice.equals("0")) {
                    itemsList.add(new order_items("Saree", sareeno, sareeprice));
                }
                if (!coatprice.equals("0")) {
                    itemsList.add(new order_items("Coat", coatno, coatprice));
                }
                if (!blanketprice.equals("0")) {
                    itemsList.add(new order_items("Blanket", blanketno, blanketprice));
                }
                if (!socksprice.equals("0")) {
                    itemsList.add(new order_items("Socks", socksno, socksprice));
                }
                if (!suitprice.equals("0")) {
                    itemsList.add(new order_items("Suit", suitno, suitprice));
                }
                if (!ungarmprice.equals("0")) {
                    itemsList.add(new order_items("Under Garments", ungarmno, ungarmprice));
                }
                if (!sweaterprice.equals("0")) {
                    itemsList.add(new order_items("Sweaters", sweatero, sweaterprice));
                }
                if (!other1no.equals("")) {
                    itemsList.add(new order_items(other1name, other1no, other1price));
                }
                if (!other2no.equals("")) {
                    itemsList.add(new order_items(other2name, other2no, other2price));
                }
                if (!other3no.equals("")) {
                    itemsList.add(new order_items(other3name, other3no, other3price));
                }

                total_amoun = findViewById(R.id.total);

                reason.setText(reas);
                total_amoun.setText(total_amount);
                recyclerView = findViewById(R.id.viewrejectedrecyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapterClass = new AdapterClass(getApplicationContext(), itemsList);
                recyclerView.setAdapter(adapterClass);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        overridePendingTransition(R.anim.fromright,R.anim.toright);
    }
}
