package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.MyLabReportsAdpter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.LabReportsItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyLabRecordsActivity extends AppCompatActivity {
    ArrayList<LabReportsItem> labReportsItems = new ArrayList<LabReportsItem>();
    MyLabReportsAdpter myLabReportsAdpter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lab_records);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lab Reports");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL = Apis.MY_LAB_REPORTS_URL+redHealId;

        listView =(ListView)findViewById(R.id.labReportsList);

        new MyLabRecordsList().execute();
    }


    public class MyLabRecordsList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyLabRecordsActivity.this);
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

                JSONArray jsonArray2 = json.optJSONArray("mylabreports_list");

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

                        labReportsItems.add(new LabReportsItem(bookingId,bookingDate,redheal_refNo,report,report_date,description,packageName,actualPrice,discountPrice,from_date,to_date,diagnosticCenterName,paymentMode,status));
                    }


                    if(labReportsItems.size()>0)
                    {
                        myLabReportsAdpter = new MyLabReportsAdpter(MyLabRecordsActivity.this, R.layout.mylabreports_item, labReportsItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(myLabReportsAdpter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                LabReportsItem labReportsItem = (LabReportsItem) listView.getItemAtPosition(position);

                                Intent intent =new Intent(MyLabRecordsActivity.this,MyLabReportDetailsActivity.class);
                                intent.putExtra("redheal_refNo",labReportsItem.getRedheal_refNo());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                Toast.makeText(MyLabRecordsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(MyLabRecordsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(MyLabRecordsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
