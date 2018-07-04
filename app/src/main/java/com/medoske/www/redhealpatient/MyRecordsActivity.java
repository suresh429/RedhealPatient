package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Adapters.MyReportAdapter;
import com.medoske.www.redhealpatient.Adapters.MySavedFeedsAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.MyReportItem;
import com.medoske.www.redhealpatient.Items.SavedFeedItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyRecordsActivity extends AppCompatActivity {
    ArrayList<MyReportItem> myReportItems = new ArrayList<MyReportItem>();
    MyReportAdapter myReportAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    RecyclerView mRecyclerView;
    String URL;
    RecyclerView.LayoutManager mLayoutManager;
    Button AddRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_records);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Records");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        URL = Apis.MY_REPORTS_URL+redHealId;

        mRecyclerView =(RecyclerView)findViewById(R.id.recordsList);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AddRecord =(Button)findViewById(R.id.AddRecord);
        AddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyRecordsActivity.this,AddReportActivity.class));
            }
        });


        new SavedRecordsList().execute();

    }

    public class SavedRecordsList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MyRecordsActivity.this);
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

            Log.e("REPORT_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("myreports_list");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String report_link = jsonObject2.optString("report_link").toString();
                        String timestamp= jsonObject2.optString("timestamp").toString();
                        String doctorName = jsonObject2.optString("doctorName").toString();
                        String patient_redhealId = jsonObject2.optString("patient_redhealId").toString();

                        myReportItems.add(new MyReportItem(report_link,timestamp,doctorName,patient_redhealId));
                    }

                    if(myReportItems.size()>0)
                    {
                        myReportAdapter=new MyReportAdapter(MyRecordsActivity.this,myReportItems);
                        mRecyclerView.setAdapter(myReportAdapter);

                    }
                    else
                    {
                        Toast.makeText(MyRecordsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(MyRecordsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
