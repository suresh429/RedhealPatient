package com.medoske.www.redhealpatient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
    RadioButton rb1,rb2,rb3;
    Button btnConfirm,btnPayNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment");

        rb1 = (RadioButton) findViewById(R.id.radioButton_card);
        rb2 = (RadioButton) findViewById(R.id.radioButton_wallet);
        rb3 = (RadioButton) findViewById(R.id.radioButton_payatClinic);

        btnConfirm=(Button)findViewById(R.id.button_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String buttonText = btnConfirm.getText().toString();
                if (rb1.isChecked()){

                    Toast.makeText(PaymentActivity.this, "button123", Toast.LENGTH_SHORT).show();

                }
                else if (rb2.isChecked()){
                    Toast.makeText(PaymentActivity.this, "button456", Toast.LENGTH_SHORT).show();
                }
                else if (rb3.isChecked()){
                Intent intent =new Intent(PaymentActivity.this,ConfirmationTicketActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                }
                else {

                    Toast.makeText(PaymentActivity.this, "please select Payment Mode", Toast.LENGTH_SHORT).show();
                }


            }
        });



        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnConfirm.setText("Pay Now");
                    rb2.setChecked(false);

                }
            }
        });

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnConfirm.setText("Pay Now");
                    rb2.setChecked(true);

                }
            }
        });


        rb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    btnConfirm.setText("Confirm");
                    rb3.setChecked(true);

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
