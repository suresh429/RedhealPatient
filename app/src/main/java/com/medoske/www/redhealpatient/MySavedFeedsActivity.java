package com.medoske.www.redhealpatient;

import android.app.ProgressDialog;
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

import com.medoske.www.redhealpatient.Adapters.MySavedFeedsAdapter;
import com.medoske.www.redhealpatient.Adapters.PackageAdapter;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.PackageItem;
import com.medoske.www.redhealpatient.Items.SavedFeedItem;
import com.medoske.www.redhealpatient.Utilities.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MySavedFeedsActivity extends AppCompatActivity {
    ArrayList<SavedFeedItem> savedFeedItems = new ArrayList<SavedFeedItem>();
    MySavedFeedsAdapter mySavedFeedsAdapter;
    List<NameValuePair> param = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    ListView listView;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_saved_feeds);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Saved Feeds");

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        String redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);

        listView =(ListView)findViewById(R.id.savedFeedslist);

        URL = Apis.MY_FAV_TIP_LIST_URL+redHealId;

        new SavedDataList().execute();

    }


    public class SavedDataList extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Create a new progress dialog
            progressDialog = new ProgressDialog(MySavedFeedsActivity.this);
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

            Log.e("ABOUT_ME_URL", URL);

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

                JSONArray jsonArray2 = json.optJSONArray("myfavfeed_list");

                Log.e("array2",""+jsonArray2);

                if (jsonArray2!=null){

                    for(int j=0; j < jsonArray2.length(); j++){

                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);

                        String categoryId = jsonObject2.optString("categoryId").toString();
                        String doctor_redhealId= jsonObject2.optString("doctor_redhealId").toString();
                        String tip_date = jsonObject2.optString("tip_date").toString();
                        String tip_time = jsonObject2.optString("tip_time").toString();
                        String doctorName = jsonObject2.optString("doctorName").toString();
                        String doctorImage = jsonObject2.optString("doctorImage").toString();
                        String tipName = jsonObject2.optString("tipName").toString();
                        String description = jsonObject2.optString("description").toString();
                        String tipImage = jsonObject2.optString("tipImage").toString();
                        String id = jsonObject2.optString("id").toString();
                        String categoryName = jsonObject2.optString("categoryName").toString();
                        String color = jsonObject2.optString("color").toString();
                        String like_status = jsonObject2.optString("like_status").toString();

                        savedFeedItems.add(new SavedFeedItem(categoryId,doctor_redhealId,tip_date,tip_time,doctorName,doctorImage,tipName,description,tipImage,id,categoryName,color,like_status));
                    }


                    if(savedFeedItems.size()>0)
                    {
                        mySavedFeedsAdapter=new MySavedFeedsAdapter(MySavedFeedsActivity.this,R.layout.saved_fee_item,savedFeedItems);
                        listView.setItemsCanFocus(false);
                        listView.setAdapter(mySavedFeedsAdapter);


                    }
                    else
                    {
                        Toast.makeText(MySavedFeedsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(MySavedFeedsActivity.this, "no data found", Toast.LENGTH_SHORT).show();

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
