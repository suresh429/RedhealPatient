package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
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

public class ClinicProfileActivity1 extends AppCompatActivity {
    JSONArray jsonArray;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;
    TextView textFee,textClinicName,textAddress,textTimings,textArea,textSpeclization,clinicName;
    ImageView clinicImageView;
    Button bookAppointment;
    WebView webViewMap;
    String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_profile1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        String clinicId = getIntent().getExtras().getString("clinicId");
        String clinicTitle = getIntent().getExtras().getString("clinicName");
        latitude = getIntent().getExtras().getString("latitude","defaultKey");
        longitude = getIntent().getExtras().getString("longitude","defaultKey");
        getSupportActionBar().setTitle(clinicTitle);


        URL = Apis.CLINIC_PROFILE_URL+clinicId;
        Log.e("URL",""+URL);

        // listView =(ListView)rootView.findViewById(R.id.mapList);

        //header textviews
        clinicName=(TextView)findViewById(R.id.txtDoctorName);
        textArea=(TextView)findViewById(R.id.txtArea);
        textSpeclization=(TextView)findViewById(R.id.txtSpecilization);
        clinicImageView =(ImageView)findViewById(R.id.clinicImage);


        textAddress=(TextView)findViewById(R.id.textAddress);
        textFee=(TextView)findViewById(R.id.textFee);
        textClinicName=(TextView)findViewById(R.id.textClinicName);
        textTimings=(TextView)findViewById(R.id.textTimings);
        webViewMap=(WebView)findViewById(R.id.webViewMap);
        webViewMap.setWebViewClient(new WebViewClient());
        webViewMap.getSettings().setJavaScriptEnabled(true);
        webViewMap.setFocusable(false);
        webViewMap.setClickable(false);
        bookAppointment=(Button)findViewById(R.id.button_book);



        new ClinicDataList().execute();
    }

    public class ClinicDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(ClinicProfileActivity1.this);
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

            Log.e("CLINIC_PROFILE_URL", URL);

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
                jsonArray = json.optJSONArray("clinic_details");

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    final String doctor_redhealId = jsonObject.optString("doctor_redhealId").toString();
                    final String fullName = jsonObject.optString("fullName").toString();
                    final String doctorImage = jsonObject.optString("doctorImage").toString();
                    String id = jsonObject.optString("id").toString();
                    final String specialization = jsonObject.optString("specialization").toString();
                    String clinic_redhealId = jsonObject.optString("clinic_redhealId").toString();
                    String clinicName1 = jsonObject.optString("clinicName").toString();
                    String consultationFee = jsonObject.optString("consultationFee").toString();
                    String premiumConsultationFee = jsonObject.optString("premiumConsultationFee").toString();
                    String clinicAddress = jsonObject.optString("clinicAddress").toString();
                    String clinicImages = jsonObject.optString("clinicImages").toString();
                    String areaId = jsonObject.optString("areaId").toString();
                    String areaName = jsonObject.optString("areaName").toString();
                    String timing1 = jsonObject.optString("timing1").toString();
                    String timing2 = jsonObject.optString("timing2").toString();
                    String timing3 = jsonObject.optString("timing3").toString();
                    String clinicImage = jsonObject.optString("clinicImage").toString();
                    final String latitude1 = jsonObject.optString("latitude").toString();
                    final String longitude1 = jsonObject.optString("longitude").toString();

                    String webUrl ="http://medoske.com/rhdoctor/welcome/test?lat="+latitude1+"&lan="+longitude1;
                    Log.e("weburl",""+webUrl);

                    textAddress.setText(clinicAddress);
                    textClinicName.setText(clinicName1);
                    textFee.setText(consultationFee +" consultationFee");
                    textTimings.setText(timing1+"\n"+timing2+"\n"+timing3);
                    webViewMap.loadUrl(webUrl);

                    textArea.setText(areaName);
                    textSpeclization.setText(specialization);
                    clinicName.setText(clinicName1);

                    if (clinicImage.isEmpty()) {
                        clinicImageView.setImageResource(R.drawable.ic_action_placeholder);
                    } else{
                        Picasso.with(getApplicationContext()).load(clinicImage).into(clinicImageView);
                    }

                    bookAppointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(ClinicProfileActivity1.this, SelectClinicACtivity.class);
                            intent.putExtra("doctorImage",doctorImage);
                            intent.putExtra("doctorName",fullName);
                            intent.putExtra("speclization",specialization);
                            intent.putExtra("doctorId",doctor_redhealId);
                            intent.putExtra("latitude",latitude);
                            intent.putExtra("longitude",longitude);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });


                }
                // output.setText(data);
            } catch (JSONException e) {e.printStackTrace();}


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
