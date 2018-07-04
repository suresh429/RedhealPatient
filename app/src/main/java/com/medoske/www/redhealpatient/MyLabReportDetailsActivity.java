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
import android.widget.ImageView;
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

public class MyLabReportDetailsActivity extends AppCompatActivity {
    int k = 0;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    String URL;
    TextView txtPackageName,txtStatus,txtActualPrice,txtDiscountPrice,txtTotal,txtPaymentMode,txtBooking,txtDiagnosticCenterName;
    ImageView imageView;

    RelativeLayout bookingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lab_report_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lab Reports Details");

        String redheal_refNo = getIntent().getExtras().getString("redheal_refNo");

        URL = Apis.MY_LAB_REPORTS_Details_URL+redheal_refNo;



        txtPackageName=(TextView)findViewById(R.id.txtPackageName);

        txtStatus=(TextView)findViewById(R.id.txtCurrentStatus);
        txtActualPrice=(TextView)findViewById(R.id.txtACtualPrice);
        txtDiscountPrice=(TextView)findViewById(R.id.txtDiscountPrice);
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        txtPaymentMode=(TextView)findViewById(R.id.textPaymentMode);
        txtDiagnosticCenterName =(TextView)findViewById(R.id.textViewCenterName);
        txtBooking=(TextView)findViewById(R.id.textViewBooking);
        imageView=(ImageView)findViewById(R.id.labreportImage);
        bookingLayout=(RelativeLayout)findViewById(R.id.bookinLayout) ;

        new MyLabRecordsDetails().execute();
    }

    public class MyLabRecordsDetails extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyLabReportDetailsActivity.this);
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

            Log.e("MY_PACKAGE_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("mylabreport_details");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String bookingId = jsonObject2.optString("bookingId").toString();
                        String bookingDate= jsonObject2.optString("bookingDate").toString();
                        String redheal_refNo = jsonObject2.optString("redheal_refNo").toString();
                        String report = jsonObject2.optString("report").toString();
                        String report_date = jsonObject2.optString("report_date").toString();
                        String description = jsonObject2.optString("description").toString();
                        String packageName = jsonObject2.optString("packageName").toString();
                        String actualPrice = jsonObject2.optString("actualPrice").toString();
                        String discountPrice = jsonObject2.optString("discountPrice").toString();
                        String from_date = jsonObject2.optString("from_date").toString();
                        String to_date = jsonObject2.optString("to_date").toString();
                        String diagnosticCenterName = jsonObject2.optString("diagnosticCenterName").toString();
                        String paymentMode = jsonObject2.optString("paymentMode").toString();
                        String status = jsonObject2.optString("status").toString();

                        //labReportsItems.add(new LabReportsItem(bookingId,bookingDate,redheal_refNo,report,report_date,description,packageName,actualPrice,discountPrice,from_date,to_date,diagnosticCenterName));



                        Picasso.with(getBaseContext()).load(report).placeholder(R.drawable.ic_action_placeholder).into(imageView);


                        txtPackageName.setText(packageName);
                        txtPackageName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));

                        txtDiagnosticCenterName.setText(diagnosticCenterName);
                        txtDiagnosticCenterName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf"));

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

                    Toast.makeText(MyLabReportDetailsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
