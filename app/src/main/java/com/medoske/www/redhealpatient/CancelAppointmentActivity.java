package com.medoske.www.redhealpatient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Api.Apis;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class CancelAppointmentActivity extends AppCompatActivity {
Button Submit;
    EditText etReason;
    String redHealId,appointmentRefId,reason;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cancel Appointment");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp =getSharedPreferences("sharedPrefName", 0); // 0 for private mode
         redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

         appointmentRefId = getIntent().getExtras().getString("appointmentRefId");

        Submit =(Button)findViewById(R.id.button_submit);
        etReason =(EditText)findViewById(R.id.etReason);

        checkBox = (CheckBox) findViewById(R.id.checkBoxAccept);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             CheckValidate();
            }
        });


    }

    private void cancelAppointment(){
        /*final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        */



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.CANCEL_APPOINTMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        Toast.makeText(CancelAppointmentActivity.this,response,Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(CancelAppointmentActivity.this,AppointmentsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        Toast.makeText(CancelAppointmentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("patient_redhealId",redHealId);
                params.put("appointmentRefNo",appointmentRefId);
                params.put("reason", reason);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void CheckValidate(){

         reason = etReason.getText().toString().trim();

         if (reason.isEmpty() || reason.length()<15 ){

            Toast.makeText(CancelAppointmentActivity.this, "Please Enter Minimum 15 Characters", Toast.LENGTH_SHORT).show();
        }else if(!checkBox.isChecked()){

            Toast.makeText(CancelAppointmentActivity.this, "Please Accept the Terms & Conditions.", Toast.LENGTH_SHORT).show();
         }
         else {

            cancelAppointment();
        }
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
