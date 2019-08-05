package com.example.hardeep.myproject.user.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hardeep.myproject.Order_details_request;
import com.example.hardeep.myproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderRequest extends Fragment {
    DatabaseReference databaseReference;
    RelativeLayout order;
    EditText  shirt, jeans, bedsheet, saree, coat, blanket, socks, sweater, suit, undergarments, other1, other2, other3, other1price, other2price, other3price, other1name, other2name, other3name;
    Button proceed;
    TextView shirtprice;
    float amount = 0;

    int shirt_cost, jeans_cost, bed_cost, saree_cost, coat_cost, blanket_cost, socks_cost, sweater_cost, suit_cost, underg_cost, other1_cost, other2_cost, other3_cost;


    public OrderRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_request, container, false);
        shirtprice = v.findViewById(R.id.shirtprice);
        order = v.findViewById(R.id.order);
        proceed = v.findViewById(R.id.proceed);
        other1name = v.findViewById(R.id.other1name);
        other2name = v.findViewById(R.id.other2name);
        other3name = v.findViewById(R.id.other3name);


        shirt = v.findViewById(R.id.shirt);
        jeans = v.findViewById(R.id.jeans);
        bedsheet = v.findViewById(R.id.bedsheet);
        saree = v.findViewById(R.id.saree);
        coat = v.findViewById(R.id.coat);
        blanket = v.findViewById(R.id.blanket);
        socks = v.findViewById(R.id.socks);
        sweater = v.findViewById(R.id.sweater);
        suit = v.findViewById(R.id.ladiessuit);
        undergarments = v.findViewById(R.id.undergarments);
        other1 = v.findViewById(R.id.other1);
        other2 = v.findViewById(R.id.other2);
        other3 = v.findViewById(R.id.other3);


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

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!other1.getText().toString().matches("")) {
                    if (other1name.getText().toString().matches("")) {
                        other1name.setError("Item's name required");
                        other1name.requestFocus();
                    }

                }

                if (!other2.getText().toString().matches("")) {
                    if (other2name.getText().toString().matches("")) {
                        other2name.setError("Item's name required");
                        other2name.requestFocus();
                    }

                }
                if (!other3.getText().toString().matches("")) {
                    if (other3name.getText().toString().matches("")) {
                        other3name.setError("Item's name required");
                        other3name.requestFocus();
                    }
                }

                if ( other1name.getError() == null  && other2name.getError() == null && other3name.getError() == null) {

                    final ProgressDialog progressDialog1 = new ProgressDialog(getActivity(), R.style.MyAlertDialogStyle);
                    final AlertDialog alertDialog = new SpotsDialog.Builder()
                            .setContext(getActivity())
                            .setMessage("Processing")
                            .setCancelable(false)
                            .setTheme(R.style.Custom)
                            .build();
                    alertDialog.show();
                    final Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            alertDialog.dismiss();


                            Intent i = new Intent(getActivity(), Order_details_request.class);

                            alertDialog.dismiss();
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

                            i.putExtra("other2_no", other2.getText().toString());

                            i.putExtra("other3_no", other3.getText().toString());

                            i.putExtra("other1_name", other1name.getText().toString());

                            i.putExtra("other2_name", other2name.getText().toString());

                            i.putExtra("other3_name", other3name.getText().toString());

                            startActivity(i);


                            t.cancel();
                        }
                    }, 2000);
                }
        }

    });
        return v;
    }

}
