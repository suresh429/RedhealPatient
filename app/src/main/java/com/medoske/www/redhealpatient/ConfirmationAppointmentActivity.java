package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfirmationAppointmentActivity extends AppCompatActivity {
TextView textDoctorName,textDate,textClinicName,textClinicAddress,textPatientName,textMobileNo, textReferenceNo,textPaymentMode;
    CircleImageView imageView;
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;
    Button buttonProceed;
    String appointmentRefNo,patient_redhealId,appointmentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_appointment);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appointment details");

        appointmentRefNo = getIntent().getExtras().getString("appointmentRefno","defaultKey");

        URL = Apis.CONFIRM_APPOINTMENT_URL+appointmentRefNo;
        Log.e("ygdfgsdg",""+URL);


        textDoctorName =(TextView)findViewById(R.id.textPatientNae1);
        textDate =(TextView)findViewById(R.id.textDateTime);
        textClinicName =(TextView)findViewById(R.id.textClinicName);
        textClinicAddress =(TextView)findViewById(R.id.textAddress);
        textPatientName =(TextView)findViewById(R.id.textViewCenterName);
        textMobileNo =(TextView)findViewById(R.id.textMobileNo);
        textReferenceNo =(TextView)findViewById(R.id.textRefernceNo);
        textPaymentMode =(TextView)findViewById(R.id.textPaymentMode);

        imageView =(CircleImageView)findViewById(R.id.circleImageView);

        buttonProceed=(Button)findViewById(R.id.buttonProceed);



       new bookingDataList().execute();
    }

    public class bookingDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(ConfirmationAppointmentActivity.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog title to 'Loading...'
            // progressDialog.setTitle("Chargement...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Fetching ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {

            //Getting product details from making HTTP Request
            JSONObject json = jsonParser.makeHttpRequest(URL, "GET", param);

            Log.e("DOCTORS_LIST_URL", URL);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            super.onPostExecute(json);

            progressDialog.dismiss();

            Log.e("data", String.valueOf(json));

            String data = "";
            try {

                //Get the instance of JSONArray that contains JSONObjects
                jsonArray = json.optJSONArray("confirm_appointment");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String appointmentRefNo = jsonObject.optString("appointmentRefNo").toString();
                    String bookingDate = jsonObject.optString("bookingDate").toString();
                    String bookingTime = jsonObject.optString("bookingTime").toString();
                    String patientName = jsonObject.optString("patientName").toString();
                    String patientNumber = jsonObject.optString("patientNumber").toString();
                    String doctorName = jsonObject.optString("doctorName").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();
                    String clinicName = jsonObject.optString("clinicName").toString();
                    String address = jsonObject.optString("address").toString();
                    String paymentMode = jsonObject.optString("paymentMode").toString();
                    String status = jsonObject.optString("status").toString();
                    patient_redhealId = jsonObject.optString("patient_redhealId").toString();
                    appointmentType = jsonObject.optString("appointmentType").toString();

                    textDoctorName.setText(doctorName);
                    textDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textDate.setText(bookingDate+"  &  "+bookingTime);
                    textDate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textClinicName.setText(clinicName);
                    textClinicName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textClinicAddress.setText(address);
                    textClinicAddress.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textPatientName.setText(patientName);
                    textPatientName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textMobileNo.setText(patientNumber);
                    textMobileNo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textReferenceNo.setText("Reference Id : \n"+appointmentRefNo);
                    textReferenceNo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                    textPaymentMode.setText("PaymentMode : "+paymentMode);
                    textPaymentMode.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));


                    Picasso.with(getApplicationContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(imageView);

                   if (paymentMode.equals("Online")){

                       buttonProceed.setVisibility(View.VISIBLE);
                   }else {

                       buttonProceed.setVisibility(View.GONE);
                   }

                    buttonProceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            registerUser();
                        }
                    });

                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}
            }
    }


    private void registerUser(){
        /*final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();*/

        final String paymentMode ="Online";
        final String paymentStatus ="success";
        final String transactionId ="65465464";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.PAY_APPOINTMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        Toast.makeText(ConfirmationAppointmentActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        Toast.makeText(ConfirmationAppointmentActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("paymentmode",paymentMode);
                params.put("payment_status",paymentStatus);
                params.put("transactionId", transactionId);
                params.put("appointmentRefNo",appointmentRefNo);
                params.put("appointmentType",appointmentType);
                params.put("patient_redhealId", patient_redhealId);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                Intent intent =new Intent(ConfirmationAppointmentActivity.this,BottomMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent =new Intent(ConfirmationAppointmentActivity.this,BottomMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
