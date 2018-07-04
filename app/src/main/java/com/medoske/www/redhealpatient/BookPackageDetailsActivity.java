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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.RecomPackageAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.RecomendPackageItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookPackageDetailsActivity extends AppCompatActivity {
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;
    TextView txtPackageName,txtPeriod,txtSittings,txtValidity,txtDiscount,txtActual,txtDoctorName,txtSpeclization,txtDescription;
    Button bookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_package_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Package Details");

        String packageId = getIntent().getExtras().getString("packageId");

        URL = Apis.PACKAGES__DETAILS_URL+packageId;

        txtPackageName=(TextView)findViewById(R.id.txtPackageName);
        txtPeriod=(TextView)findViewById(R.id.txtPeriod);
        txtSittings=(TextView)findViewById(R.id.txtSittings);
        txtValidity=(TextView)findViewById(R.id.txtValidity);
        txtDiscount=(TextView)findViewById(R.id.txtDiscountPrice);
        txtActual=(TextView)findViewById(R.id.txtActualPrice);
        txtDoctorName=(TextView)findViewById(R.id.txtDoctorame);
        txtSpeclization=(TextView)findViewById(R.id.txtSpeclization);
        txtDescription=(TextView)findViewById(R.id.txtDescription);

        bookButton=(Button) findViewById(R.id.bookButton);

        new AllPackagesDetailsData().execute();
    }

    public class AllPackagesDetailsData extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(BookPackageDetailsActivity.this);
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

                JSONArray jsonArray2 = json.optJSONArray("package_profile");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        final String packageId = jsonObject2.optString("packageId").toString();
                        String packageName= jsonObject2.optString("packageName").toString();
                        String actualPrice = jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String fromTime = jsonObject2.optString("fromTime").toString();
                        String toTime = jsonObject2.optString("toTime").toString();
                        String packageImage = jsonObject2.optString("packageImage").toString();
                        String total_sittings = jsonObject2.optString("total_sittings").toString();
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

                        txtPackageName.setText(packageName);
                        txtPeriod.setText("Period : "+period);
                        txtSittings.setText("Sittings : "+total_sittings);
                        txtValidity.setText("Validity : "+fromTime+"  -  "+toTime);
                        txtDiscount.setText("Discount Price : "+discountPrice);
                        txtActual.setText("Actual Price : "+actualPrice);
                        txtActual.setPaintFlags(txtActual.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        txtDoctorName.setText(doctorName);
                        txtSpeclization.setText(specialization+"\n"+clinicName+"\n"+address);
                        txtDescription.setText(description);

                        bookButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i8 =new Intent(BookPackageDetailsActivity.this,PackageTimeSlotsActivity.class);
                                i8.putExtra("clinicId",clinic_redhealId);
                                i8.putExtra("doctorId",doctor_redhealId);
                                i8.putExtra("doctorImage",doctorImage);
                                i8.putExtra("clinicName",clinicName);
                                i8.putExtra("doctorName",doctorName);
                                i8.putExtra("speclization",specialization);
                                i8.putExtra("packageId",packageId);
                                i8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i8);
                            }
                        });


                    }




                }
                else {

                    Toast.makeText(BookPackageDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
