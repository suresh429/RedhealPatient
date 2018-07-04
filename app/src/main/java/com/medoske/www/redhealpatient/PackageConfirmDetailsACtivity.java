package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
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
import com.google.android.gms.common.api.Api;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.BookAppointmentItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.medoske.www.redhealpatient.Api.Apis.REGISTER_URL;

public class PackageConfirmDetailsACtivity extends AppCompatActivity {
    String URL;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    Button buttonProceed;
    CircleImageView doctorImageView;
    String packageRefNo,appointmentRefNo,patient_redhealId;
    TextView txtDoctorName,txtSpeclization,txtPackageName,txtPeriodsittings,txtValidity,txtActualPrice,txtDiscountPrice,txtTotalPrice,txtpatientName,txtpatientDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_confirm_details_activity);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Confirm Your Details");

         packageRefNo = getIntent().getExtras().getString("packageRefNo","defaultKey");

        URL = Apis.CONFIRM_PACKAGES_URL+packageRefNo;

        txtDoctorName=(TextView)findViewById(R.id.txtDoctorName);
        txtSpeclization=(TextView)findViewById(R.id.txtSpeclization);
        txtPackageName=(TextView)findViewById(R.id.txtPackagename);
        txtPeriodsittings=(TextView)findViewById(R.id.txtPeriodSittings);
        txtValidity=(TextView)findViewById(R.id.txtValidity);
        txtActualPrice=(TextView)findViewById(R.id.txtActualPrice);
        txtDiscountPrice=(TextView)findViewById(R.id.txtDiscountPrice);
        txtTotalPrice=(TextView)findViewById(R.id.txtTotalPrice);
        txtpatientName=(TextView)findViewById(R.id.txtPatientName);
        txtpatientDetails=(TextView)findViewById(R.id.txtPatientDetails);
        buttonProceed=(Button)findViewById(R.id.buttonProceed);
        doctorImageView=(CircleImageView)findViewById(R.id.doctorImage);

        new PackageConfirmDetailsData().execute();


    }
    public class PackageConfirmDetailsData extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(PackageConfirmDetailsACtivity.this);
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

            Log.e("BEST_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("confirm_package");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        final String packageId = jsonObject2.optString("packageId").toString();
                        String packageName= jsonObject2.optString("packageName").toString();
                        String actualPrice = jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String validity = jsonObject2.optString("validity").toString();

                        String packageImage = jsonObject2.optString("packageImage").toString();
                        String total_sittings = jsonObject2.optString("sittings").toString();
                        String period = jsonObject2.optString("period").toString();
                        final String specialization = jsonObject2.optString("specialization").toString();
                        final String doctor_redhealId = jsonObject2.optString("doctor_redhealId").toString();
                        final String doctorName = jsonObject2.optString("doctorName").toString();
                        final String doctorImage = jsonObject2.optString("doctorImage").toString();
                        final String clinicName = jsonObject2.optString("clinicName").toString();
                        final String clinic_redhealId = jsonObject2.optString("clinic_redhealId").toString();
                        String address = jsonObject2.optString("address").toString();
                        String clinicImage = jsonObject2.optString("clinicImage").toString();
                        String experience = jsonObject2.optString("experience").toString();
                        String description = jsonObject2.optString("description").toString();
                        String patientName = jsonObject2.optString("patientName").toString();
                        String patientMobile = jsonObject2.optString("patientNumber").toString();
                        patient_redhealId = jsonObject2.optString("patient_redhealId").toString();
                        appointmentRefNo = jsonObject2.optString("appointmentRefNo").toString();


                        txtPackageName.setText(packageName);
                        txtPeriodsittings.setText("Period : "+period + "\n"+"Sittings : "+total_sittings);
                        txtValidity.setText("Validity : "+validity);
                        txtDiscountPrice.setText(discountPrice);
                        txtActualPrice.setText(actualPrice);
                        txtActualPrice.setPaintFlags(txtActualPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        txtDoctorName.setText(doctorName);
                        txtSpeclization.setText(specialization+"\n"+clinicName+"\n"+address);
                        txtTotalPrice.setText(discountPrice);
                        txtpatientName.setText("Patient Name : "+patientName);
                        txtpatientDetails.setText("Mobile no : "+patientMobile);
                        Picasso.with(getBaseContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(doctorImageView);


                        buttonProceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*Intent i8 =new Intent(PackageConfirmDetailsACtivity.this,PackageTimeSlotsActivity.class);
                                i8.putExtra("clinicId",clinic_redhealId);
                                i8.putExtra("doctorId",doctor_redhealId);
                                i8.putExtra("doctorImage",doctorImage);
                                i8.putExtra("clinicName",clinicName);
                                i8.putExtra("doctorName",doctorName);
                                i8.putExtra("speclization",specialization);
                                i8.putExtra("packageId",packageId);
                                i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i8);*/

                                registerUser();
                            }
                        });


                    }




                }
                else {

                    Toast.makeText(PackageConfirmDetailsACtivity.this, "no data found", Toast.LENGTH_SHORT).show();

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.PACKEGES_PAYMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        Toast.makeText(PackageConfirmDetailsACtivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        Toast.makeText(PackageConfirmDetailsACtivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("paymentmode",paymentMode);
                params.put("payment_status",paymentStatus);
                params.put("transactionId", transactionId);
                params.put("appointmentRefNo",appointmentRefNo);
                params.put("packageRefNo",packageRefNo);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
