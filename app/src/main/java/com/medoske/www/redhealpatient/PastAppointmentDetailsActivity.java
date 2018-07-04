package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Utilities.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PastAppointmentDetailsActivity extends AppCompatActivity {
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    /*private RecyclerView mRecyclerViewPrescription,mRecyclerViewDiagnostic;;

    private List<MyReportItem> prescriptionItems =new ArrayList<>();
    private MyReportAdapter mAdapter;

    private List<DiagnosticReportItem> diagnosticReportItems =new ArrayList<>();
    private DiagnosticReportAdapter diagnosticReportAdapter;*/

    String URL;
    TextView textPatientName, textRhId, textMobile, textDoctorName, textSpeclization, textDateTime, textPaymentMode;
    CircleImageView patientImageView, doctorImageView;
    Button addReport;
    String appointmentRefNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_appointment_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Past Appointment Details");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        appointmentRefNo = sp.getString("appointmentRefNo", "1");
        Log.e("appointmentRefNo", "" + appointmentRefNo);


        URL = Apis.PAST_APPOINTMENTS_DETAILS_URL + appointmentRefNo;

        textPatientName = (TextView) findViewById(R.id.textViewCenterName);
        textRhId = (TextView) findViewById(R.id.textSpeclization);
        textMobile = (TextView) findViewById(R.id.textExp);
        textDoctorName = (TextView) findViewById(R.id.textPatientNae1);
        textSpeclization = (TextView) findViewById(R.id.textRhid1);
        textDateTime = (TextView) findViewById(R.id.textDateTime);
        textPaymentMode = (TextView) findViewById(R.id.paymentStatus);
        doctorImageView = (CircleImageView) findViewById(R.id.patientImage1);
        patientImageView = (CircleImageView) findViewById(R.id.labreportImage);
        addReport = (Button) findViewById(R.id.addReport);

        new PastAppointmentDetails().execute();

    }

    public class PastAppointmentDetails extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(PastAppointmentDetailsActivity.this);
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

            Log.e("PAST_DETAILS_URL", URL);

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
               JSONArray jsonArray = json.optJSONArray("past_appointment_details");

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    final String appointmentRefNo = jsonObject.optString("appointmentRefNo").toString();
                    final String bookingId = jsonObject.optString("bookingId").toString();
                    String bookingDate = jsonObject.optString("bookingDate").toString();
                    String bookingTime = jsonObject.optString("bookingTime").toString();
                    final String patient_redhealId = jsonObject.optString("patient_redhealId").toString();
                    String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                    String status = jsonObject.optString("status").toString();
                    final String doctorName = jsonObject.optString("doctorName").toString();
                    String doctorImage = jsonObject.optString("doctorImage").toString();
                    String specialization = jsonObject.optString("specialization").toString();
                    String clinicName = jsonObject.optString("clinicName").toString();
                    String address = jsonObject.optString("address").toString();
                    String latitude = jsonObject.optString("latitude").toString();
                    String longitude = jsonObject.optString("longitude").toString();
                    String patientName = jsonObject.optString("patientName").toString();
                    String patientImage = jsonObject.optString("patientImage").toString();
                    String mobileNumber = jsonObject.optString("mobileNumber").toString();
                    String reschedule_reson = jsonObject.optString("reschedule_reson").toString();
                    String paymentMode = jsonObject.optString("paymentMode").toString();

                   /* try{
                         intColor = Integer.parseInt(color);
                    }catch(NumberFormatException ex){ // handle your exception

                    }*/

                    textPatientName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
                    textPatientName.setText(patientName);


                    textRhId.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                    textRhId.setText(patient_redhealId);


                    textMobile.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                    textMobile.setText(mobileNumber);


                    textDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
                    textDoctorName.setText(doctorName);


                    textSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                    textSpeclization.setText(specialization);


                    textDateTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                    textDateTime.setText("on " + bookingDate + " & " + bookingTime + "\n" + "at " + address);


                    textPaymentMode.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                    textPaymentMode.setText("Payment Mode : " + paymentMode);


                    Picasso.with(getApplicationContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(doctorImageView);
                    Picasso.with(getApplicationContext()).load(patientImage).placeholder(R.drawable.ic_action_placeholder).into(patientImageView);



                    addReport.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PastAppointmentDetailsActivity.this, AddReportActivity.class);
                            intent.putExtra("doctorName", doctorName);
                            intent.putExtra("patientId", patient_redhealId);
                            intent.putExtra("bookingId", bookingId);
                            intent.putExtra("appointmentRefNo", appointmentRefNo);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });




                }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
