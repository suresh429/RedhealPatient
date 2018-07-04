package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MyPackageDetailsActivity extends AppCompatActivity {
    int i =0;
    int k = 0;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;

    TextView txtDoctorName,txtDoctorExp,txtSpeclization,txtPatientname,txtRhId,
            txtPackageName,txtPeriod,txtStatus,txtActualPrice,txtDiscountPrice,txtTotal,txtPaymentMode,txtRemaing,txtBooking;
    CircleImageView doctorImageView,patientImageVie;
    RelativeLayout bookingLayout;
    LinearLayout remaningLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_package_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Package Details");


        String packageRefNo = getIntent().getExtras().getString("packageRefNo");
        URL = Apis.MY_PACKEGES_DETAILS_URL+packageRefNo;

        txtDoctorName=(TextView)findViewById(R.id.textViewCenterName);
        txtDoctorExp=(TextView)findViewById(R.id.textExp);
        txtSpeclization=(TextView)findViewById(R.id.textSpeclization);
        txtPatientname=(TextView)findViewById(R.id.textPatientNae1);
        txtRhId=(TextView)findViewById(R.id.textRhid1);
        txtPackageName=(TextView)findViewById(R.id.txtPackageName);
        txtPeriod=(TextView)findViewById(R.id.txtValidity);
        txtStatus=(TextView)findViewById(R.id.txtCurrentStatus);
        txtActualPrice=(TextView)findViewById(R.id.txtACtualPrice);
        txtDiscountPrice=(TextView)findViewById(R.id.txtDiscountPrice);
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        txtPaymentMode=(TextView)findViewById(R.id.textPaymentMode);

        // images
        doctorImageView=(CircleImageView)findViewById(R.id.labreportImage) ;
        patientImageVie=(CircleImageView)findViewById(R.id.patientImage1) ;

        txtRemaing=(TextView)findViewById(R.id.textViewRemaing);
        txtBooking=(TextView)findViewById(R.id.textViewBooking);

        // layouts
        bookingLayout=(RelativeLayout)findViewById(R.id.bookinLayout) ;
        remaningLayout=(LinearLayout) findViewById(R.id.remainingLayout) ;

        new MyOrdersDetailsList().execute();

    }


    public class MyOrdersDetailsList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyPackageDetailsActivity.this);
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

            Log.e("MY_PACKAGE_DETAILS_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("package_details");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String packageName = jsonObject2.optString("packageName").toString();
                        String actualPrice= jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String fromTime = jsonObject2.optString("fromTime").toString();
                        String toTime = jsonObject2.optString("toTime").toString();
                        String packageImage = jsonObject2.optString("packageImage").toString();
                        String period = jsonObject2.optString("period").toString();
                        String description = jsonObject2.optString("description").toString();
                        String specialization = jsonObject2.optString("specialization").toString();
                        String packageId = jsonObject2.optString("packageId").toString();
                        String packageleadId = jsonObject2.optString("packageleadId").toString();
                        String doctor_redhealId = jsonObject2.optString("doctor_redhealId").toString();
                        String doctorName = jsonObject2.optString("doctorName").toString();
                        String doctorImage= jsonObject2.optString("doctorImage").toString();
                        String leadDate = jsonObject2.optString("leadDate").toString();
                        String leadTime = jsonObject2.optString("leadTime").toString();
                        String packageRefNo = jsonObject2.optString("packageRefNo").toString();
                        String paymentMode = jsonObject2.optString("paymentMode").toString();
                        String status = jsonObject2.optString("status").toString();
                        String clinicName = jsonObject2.optString("clinicName").toString();
                        String clinic_redhealId = jsonObject2.optString("clinic_redhealId").toString();
                        String address = jsonObject2.optString("address").toString();
                        String clinicImage = jsonObject2.optString("clinicImage").toString();
                        String total_sittings = jsonObject2.optString("total_sittings").toString();
                        String completed_sittings = jsonObject2.optString("completed_sittings").toString();
                        String remaing_sittings = jsonObject2.optString("remaing_sittings").toString();
                        String experience = jsonObject2.optString("experience").toString();
                        String patientName = jsonObject2.optString("patientName").toString();
                        String patientId = jsonObject2.optString("patient_redhealId").toString();
                        String patientImage = jsonObject2.optString("patientImage").toString();

                        txtDoctorName.setText(doctorName);
                        txtDoctorName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                        txtDoctorExp.setText("Exp "+experience+" Years");
                        txtDoctorExp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        txtSpeclization.setText(specialization);
                        txtSpeclization.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        txtPatientname.setText(patientName);
                        txtPatientname.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                        txtRhId.setText(patientId);
                        txtRhId.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        Picasso.with(getBaseContext()).load(doctorImage).placeholder(R.drawable.ic_action_placeholder).into(doctorImageView);
                        Picasso.with(getBaseContext()).load(patientImage).placeholder(R.drawable.ic_action_placeholder).into(patientImageVie);

                        txtPackageName.setText(packageName);
                        txtPackageName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                        txtPeriod.setText("Validity : "+period);
                        txtPeriod.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        txtStatus.setText("Status : "+status);
                        txtStatus.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        txtActualPrice.setText("Rs"+actualPrice);
                        txtActualPrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));
                        txtActualPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                        txtDiscountPrice.setText("Rs"+discountPrice);
                        txtDiscountPrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));


                        txtTotal.setText("Rs"+discountPrice);
                        txtTotal.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

                        txtPaymentMode.setText("Payment Mode : "+paymentMode);
                        txtPaymentMode.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));


                        // expandable textview
                        txtRemaing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub

                                i++;
                                Handler handler = new Handler();
                                Runnable r = new Runnable() {

                                    @Override
                                    public void run() {
                                        i = 0;
                                    }
                                };

                                if (i == 1) {
                                    //Single click
                                    remaningLayout.setVisibility(View.VISIBLE);

                                } else if (i == 2) {
                                    //Double click
                                    i = 0;

                                  remaningLayout.setVisibility(View.GONE);
                                }
                            }
                        });


                        txtBooking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                k++;
                                Handler handler = new Handler();
                                Runnable r = new Runnable() {

                                    @Override
                                    public void run() {
                                        k = 0;
                                    }
                                };

                                if (k == 1) {
                                    //Single click
                                    bookingLayout.setVisibility(View.VISIBLE);

                                } else if (k == 2) {
                                    //Double click
                                    k = 0;

                                    bookingLayout.setVisibility(View.GONE);
                                }
                            }
                        });

                    }


                }
                else {

                    Toast.makeText(MyPackageDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
